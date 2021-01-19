import javax.websocket.*;
import javax.websocket.server.*;
import java.sql.*;
import java.util.*;
import com.thinking.machines.drone.common.request.*;
import com.thinking.machines.drone.common.response.*;
import com.thinking.machines.drone.common.*;
import com.thinking.machines.user.*;
import com.thinking.machines.connection.*;
import com.google.gson.*;
import java.lang.*;
import javax.servlet.*;
import com.thinking.machines.drone.pojo.*;
import com.thinking.machines.user.pojo.*;
import java.io.*;
import java.net.*;
import java.text.*;

@ServerEndpoint(value = "/server2/{typeOfDevice}/{id}", configurator = com.thinking.machines.user.Authenticator.class)
public class MainServer2 {
	private long lastHeartBeatRequestTime = 0;
	private int totalFlightTime = 100;// in mins
	private static HashMap<String, String> messageIdMap = new HashMap<>();
	private Gson gson = new Gson();
	private Timer timer = null;
	private EndpointConfig config;
	private List<Drone> drones = null;
	private List<User> users = null;
	private ServletContext context=null;
	// private Client client=null;

	@OnOpen
	public void openHandler(@PathParam("typeOfDevice") String nameOfDevice, @PathParam("id") String id, Session session,
			EndpointConfig config) {
		// check if already exists drone request more than one time so close connection
		// with reason already connected
		// System.out.println(nameOfDevice);
		// System.out.println(id);
		try {
			System.out.println("Connected with session id " + session.getId());
			this.config = config;
			if (nameOfDevice.equalsIgnoreCase("drone")) {
				context = (ServletContext) this.config.getUserProperties().get("servletContext");
				drones = (List<Drone>) context.getAttribute("webSocketDrones");
				for (Drone d : drones) {
					if (d.getId().equalsIgnoreCase(id)) {
						d.setSession(session);
						System.out.println(d.getId() + " " + d.getName() + " " + d.getSession().getId() + " "
								+ d.getDroneStatus() + " " + d.getIsConnected());
						// System.out.println("Bhaiya drone ka session mil gaya he:
						// "+d.getSession().getId());
						break;
					}
				}
			}
		} catch (Exception exception) {
			System.out.println("on open exception " + exception);
			exception.printStackTrace();
		}

	}
	@OnClose
	public void closeHandler(Session session) {
		try {
			context = (ServletContext) this.config.getUserProperties().get("servletContext");
			System.out.println("Connection closed with session " + session.getId());
			User tempUser = null;
			users=(List<User>)context.getAttribute("webSocketUsers");
			drones=(List<Drone>)context.getAttribute("webSocketDrones");
			// System.out.println("1");
			for (User u : users) {
				if (u.getSession()!= null && session.getId().equalsIgnoreCase( u.getSession().getId())){
					tempUser = u;
					// System.out.println("2 session id :"+tempUser.getSession().getId());
					break;
				}
			}
			Drone tempDrone = null;
			for (Drone d : drones) {
				if (d.getSession()!=null && session.getId().equalsIgnoreCase(d.getSession().getId())){
					tempDrone = d;
					// System.out.println("3 session id:"+tempDrone.getSession().getId());
					break;
				}
			}
			if(tempUser!=null)
			{
				tempDrone = null;
				for (Drone d : drones) {
					if (d.getId().equalsIgnoreCase(tempUser.getDroneId())){
						tempDrone = d;
						// System.out.println("4 session id:"+tempDrone.getSession().getId());
						break;
					}
				}
				tempUser.setSession(null);
				tempUser.setDroneId(null);
				if(tempDrone!=null) tempDrone.setIsConnected(false);
			}
			else if(tempDrone!=null)
			{
				tempUser = null;
				for (User u : users) {
					if (u.getSession()!=null && u.getDroneId()!=null && u.getDroneId().equalsIgnoreCase(tempDrone.getId())){
						tempUser = u;
						// System.out.println("5 session id:"+tempUser.getSession().getId());
						break;
					}
				}
				DroneStatusRequest droneStatusRequest = new DroneStatusRequest();
				droneStatusRequest.setStatus("disconnected");
				JsonParser jsonParser=new JsonParser();
				JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(droneStatusRequest));
				Message responseMessage2 = new Message();
				responseMessage2.setTypeId(1);
				responseMessage2.setId("h1234");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				responseMessage2.setDateTime(sdf.format(new java.util.Date()));
				responseMessage2.setMessage("DroneStatusRequest");
				responseMessage2.setPayload(jo);
				if(tempUser!=null) tempUser.getSession().getBasicRemote().sendText(gson.toJson(responseMessage2));
				forwardRequestToLoggingServer(gson.toJson(responseMessage2));

				tempDrone.setSession(null);
				tempDrone.setDroneStatus(null);
				tempDrone.setIsConnected(false);
				if(tempUser!=null) tempUser.setDroneId(null);
			}
		} catch (Exception exception) {
			System.out.println("on close exception " + exception);
		}
	}
	@OnMessage
	public void messageHandler(String jsonString, Session session) {
		System.out.println("On Message chali " + session.getId());
		// System.out.println(jsonString);
		Parser2 p = new Parser2(jsonString);
		Message m = p.parse(jsonString);
		String message = m.getMessage();
		int typeId = m.getTypeId();
		// System.out.println(
		// m.getTypeId() + " " + m.getId() + " " + m.getMessage() + " " +
		// m.getDateTime() + " " + m.getPayload());
		Message responseMessage = new Message();
		responseMessage.setTypeId(2);
		responseMessage.setId(m.getId());
		// changes
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String dateTime=sdf.format(new java.util.Date());
		responseMessage.setDateTime(dateTime);
		JsonParser jsonParser = new JsonParser();
		context = (ServletContext) this.config.getUserProperties().get("servletContext");
		forwardRequestToLoggingServer(gson.toJson(m));
		if (typeId == 1) {
			if (message.equalsIgnoreCase("AuthenticateRequest")) {
				try {
					AuthenticateRequest aRequest = (AuthenticateRequest) m.getRequest();
					String requestUsername = aRequest.getUsername();
					String requestPassword = aRequest.getPassword();
					// ServletContext
					// context=(ServletContext)this.config.getUserProperties().get("servletContext");
					users = (List<User>) context.getAttribute("webSocketUsers");
					boolean usernameFlag = false;
					User tempUser = null;
					for (User user : users) {
						if (user.getUsername().equals(requestUsername)) {
							usernameFlag = true;
							tempUser = user;
							break;
						}
					}
					AuthenticateResponse aResponse = new AuthenticateResponse();
					if (usernameFlag) {
						Connection connection = new DatabaseConnection().getConnection();
						PreparedStatement s = connection.prepareStatement("select * from member where username=?");
						s.setString(1, requestUsername);
						ResultSet rs = s.executeQuery();
						rs.next();
						String decryptedPassword = rs.getString("pwd");
						if (decryptedPassword.equals(requestPassword)) {
							System.out.println("User authenticated");
							aResponse.setStatus("Accepted");
							for (User u : users) {
								if (u.getUsername().equalsIgnoreCase(tempUser.getUsername())) {
									u.setSession(session);
									break;
								}
							}
							System.out.println(tempUser.getId() + "\n " + tempUser.getUsername() + "\n "
									+ tempUser.getSession() + "\n " + tempUser.getDroneId());
						} 
					else if(tempUser.getSession()!=null){
							aResponse.setStatus("Rejected");
							aResponse.setReason("Concurrent Login!!");
					}
					 else {
							System.out.println("User credential invalid");
							aResponse.setStatus("Rejected");
							aResponse.setReason("Invalid password");
						}
					} else {
						System.out.println("User credential invalid");
						aResponse.setStatus("Rejected");
						aResponse.setReason("Invalid username");
					}
					String aResponseString = gson.toJson(aResponse);
					// System.out.println("Authentication response string " + aResponseString);
					JsonObject jo = (JsonObject) jsonParser.parse(aResponseString);
					responseMessage.setPayload(jo);
					String messageString = gson.toJson(responseMessage);
					// System.out.println(messageString);
					session.getBasicRemote().sendText(messageString);
					forwardRequestToLoggingServer(messageString);

				} catch (Exception exception) {
					System.out.println("Authenticate request exception " + exception);
				}
			} // authentication request ends
			if (message.equalsIgnoreCase("SendListOfDevicesRequest")) {
				try {
					HashMap<String, String> deviceMap = new HashMap<>();
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession() != null && !(d.getIsConnected())) {
							System.out.println("hashmap iterator invoked");
							deviceMap.put(d.getId(), d.getName());
						}
					}
					System.out.println(deviceMap);
					// entry of log
					SendListOfDevicesResponse sendListOfDevicesResponse = new SendListOfDevicesResponse();
					sendListOfDevicesResponse.setDevices(deviceMap);
					String sendListOfDevicesResponseString = gson.toJson(sendListOfDevicesResponse);
					JsonObject jo = (JsonObject) jsonParser.parse(sendListOfDevicesResponseString);
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));

					forwardRequestToLoggingServer(gson.toJson(responseMessage));
				} catch (Exception exception) {
					System.out.println("send list of devices exception " + exception);
				}
			} // send list of devices ends
			if (message.equalsIgnoreCase("AssignDeviceRequest")) {
				AssignDeviceRequest a = (AssignDeviceRequest) m.getRequest();
				System.out.println("Device Id" + a.getDeviceId());
				AssignDeviceResponse assignDeviceResponse = new AssignDeviceResponse();
				users = (List<User>) context.getAttribute("webSocketUsers");
				drones = (List<Drone>) context.getAttribute("webSocketDrones");
				JsonObject jo;
				User tempUser = null;
				for (User u : users) {
					System.out.println(u.getUsername() + " " + u.getSession());
				}
				for (User u : users) {
					System.out.println("-------------------------------------------" + session.getId());
					if (u.getSession() != null && u.getSession().getId().equalsIgnoreCase(session.getId())) {
						tempUser = u;
						break;
					}
				}
				try 
				{
				Drone tempDrone = null;
				drones = (List<Drone>) context.getAttribute("webSocketDrones");
				for (Drone d : drones) {
					if (d.getSession()!=null && d.getId().equalsIgnoreCase(a.getDeviceId()))
						tempDrone = d;
				}
					if(tempUser!=null && tempUser.getDroneId()!=null)
					{
						assignDeviceResponse.setStatus("Connection refused");
						assignDeviceResponse.setReason("User already connected with drone : "+tempUser.getDroneId());
					}
					else if (tempDrone == null) {
						assignDeviceResponse.setStatus("Connection refused");
						assignDeviceResponse.setReason("Invalid id");
					}
					else if(tempDrone.getIsConnected())
					{
						assignDeviceResponse.setStatus("Connection refused");
						assignDeviceResponse.setReason("Drone already connected");
					}
					else {
						tempUser.setDroneId(tempDrone.getId());
						tempDrone.setIsConnected(true);
						assignDeviceResponse.setStatus("Connection Established");
					}
					jo = (JsonObject) jsonParser.parse(gson.toJson(assignDeviceResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					// System.out.println("-------------------------------------------");
					// drones = (List<Drone>) context.getAttribute("webSocketDrones");
					// users = (List<User>) context.getAttribute("webSocketUsers");
					// System.out.println(users.get(0).getDroneId());
					// System.out.println(drones.get(0).getIsConnected());
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
				} catch (Exception exception) {
					System.out.println("Assign device request exception " + exception);
				}
			}
			if (message.equalsIgnoreCase("commandRequest")) {
				System.out.println("command request method invoked");
				// this could only be done after assingment of drone
				try {
					CommandRequest commandRequest = (CommandRequest) m.getRequest();
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && u.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempUser = u;
							break;
						}
					}
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && tempUser.getDroneId().equalsIgnoreCase(d.getId())) {
							tempDrone = d;
							break;
						}
					}

					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(commandRequest));
					// messageIdMap.put(m.getId(),message);
					// for this time we are not sure about the situation so we are using session
					// but we can use queue instead of session if needed
					session.getUserProperties().put("messageId", m.getId());
					responseMessage.setId("av10");
					responseMessage.setTypeId(1);
					responseMessage.setMessage("CommandRequest");
					responseMessage.setPayload(jo);
					messageIdMap.put(responseMessage.getId(), responseMessage.getMessage());
					tempDrone.getSession().getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
				} catch (Exception exception) {
					System.out.println("Command request exception " + exception);
				}
			} // command request ends
			if (message.equalsIgnoreCase("ReleaseDroneRequest")) {
				try {
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && u.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempUser = u;
							break;
						}
					}
					System.out.println("cool");
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && tempUser!=null && tempUser.getDroneId().equalsIgnoreCase(d.getId())) {
							tempDrone = d;
							break;
						}
					}System.out.println("cool2");
					ReleaseDroneResponse releaseDroneResponse = new ReleaseDroneResponse();
					if(tempDrone==null)
					{
						releaseDroneResponse.setDisconnect("Rejected");
						releaseDroneResponse.setReason("Drone already released");
						JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(releaseDroneResponse));
						responseMessage.setPayload(jo);
						session.getBasicRemote().sendText(gson.toJson(responseMessage));
						forwardRequestToLoggingServer(gson.toJson(responseMessage));
					}
					// tempDrone.setSession(null);
					// tempDrone.setDroneStatus(null);
					tempDrone.setIsConnected(false);
					if(tempUser!=null) tempUser.setDroneId(null);

					if (tempDrone.getDroneStatus()!=null && tempDrone.getDroneStatus().getAltitude() > 0) {
						CommandRequest commandRequest = new CommandRequest();
						commandRequest.setLand(true);
						System.out.println("cool3");
						JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(commandRequest));
						session.getUserProperties().put("messageId", m.getId());
						Message requestMessage = new Message();
						requestMessage.setId("avp12");
						requestMessage.setTypeId(1);
						requestMessage.setDateTime(dateTime);
						requestMessage.setMessage("CommandRequest");
						requestMessage.setPayload(jo);
						System.out.println("cool4");
						messageIdMap.put(requestMessage.getId(), requestMessage.getMessage());
						System.out.println("cool");
						tempDrone.setDroneStatus(null);
						tempDrone.getSession().getBasicRemote().sendText(gson.toJson(requestMessage));
						tempDrone.getSession().getUserProperties().put("UserObject", tempUser);
						forwardRequestToLoggingServer(gson.toJson(requestMessage));

						System.out.println("-----------------------------------------------------");
					}

					releaseDroneResponse.setDisconnect("Accepted");
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(releaseDroneResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
				} catch (Exception exception) {
					System.out.println("Release drone exception " + exception);
				}
			}
			if (message.equalsIgnoreCase("StatusRequest")) {
				try {
					System.out.println("Status request invoked");
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && u.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempUser = u;
							break;
						}
					}
					String droneId = tempUser.getDroneId();
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && d.getId().equalsIgnoreCase(droneId)) {
							tempDrone = d;
							break;
						}
					}
					if(tempDrone==null || tempDrone.getDroneStatus()==null)
					{
					StatusResponse statusResponse = new StatusResponse();
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(statusResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));	
					}
					else
					{
					StatusNotificationRequest droneStatus = tempDrone.getDroneStatus();
					StatusResponse statusResponse = new StatusResponse();
					statusResponse.setDistanceTravelled(droneStatus.getDistanceTravelled());
					statusResponse.setLatitude(droneStatus.getLatitude());
					statusResponse.setLongitude(droneStatus.getLongitude());
					statusResponse.setAltitude(droneStatus.getAltitude());
					statusResponse.setSpeed(droneStatus.getSpeed());
					statusResponse.setRemainingFlightTime(droneStatus.getRemainingFlightTime());
					statusResponse.setTimeElapsed(droneStatus.getTimeElapsed());
					// statusResponse.setAirSpeed(droneStatus.getAirSpeed());
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(statusResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
					}
				} catch (Exception exception) {
					System.out.println("Mobile Status request exception " + exception);
				}
			}
			if (message.equalsIgnoreCase("StatusNotificationRequest")) {
				try {
					System.out.println("status notification request invoked");
					StatusNotificationRequest statusNotificationRequest = (StatusNotificationRequest) m.getRequest();
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && d.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempDrone = d;
							break;
						}
					}
					System.out.println(statusNotificationRequest);
					tempDrone.setDroneStatus(statusNotificationRequest);

					StatusNotificationResponse statusNotificationResponse = new StatusNotificationResponse();
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(statusNotificationResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
					System.out.println(statusNotificationRequest.getRemainingFlightTime());
					System.out.println((int) statusNotificationRequest.getRemainingFlightTime() / totalFlightTime);
					int batteryPercentage = (int) (((((int) statusNotificationRequest.getRemainingFlightTime()) * 100)
							/ totalFlightTime));
					System.out.println("Battery Percentage " + batteryPercentage);
					if (batteryPercentage < 10) {
						System.out.println("Battery low request invoked");
						BatteryLowRequest blr = new BatteryLowRequest();
						blr.setBatteryLeft(batteryPercentage);
						Message requestMessage = new Message();
						requestMessage.setTypeId(1);
						requestMessage.setId("battery1");
						requestMessage.setDateTime(dateTime);
						requestMessage.setMessage("BatteryLowRequest");
						jo = (JsonObject) jsonParser.parse(gson.toJson(blr));
						requestMessage.setPayload(jo);
						User tempUser = null;
						users = (List<User>) context.getAttribute("webSocketUsers");
						for (User u : users) {
							if (u.getSession() != null && tempDrone.getId().equalsIgnoreCase(u.getDroneId())) {
								tempUser = u;
								break;
							}
						}
						if (tempUser == null)
							System.out.println("User not assigned");
						else
							tempUser.getSession().getBasicRemote().sendText(gson.toJson(requestMessage));
						forwardRequestToLoggingServer(gson.toJson(requestMessage));
					}
					// System.out.println("drone status " + tempDrone.getDroneStatus().getDistanceTravelled());
					// entry of log
				} catch (Exception exception) {
					System.out.println("Status notification exception " + exception);
				}
			}
			if (message.equalsIgnoreCase("RangeAlertNotificationRequest")) {
				try {
					System.out.println("Range alert notification invoked");
					RangeAlertNotificationRequest rangeAlertNotificationRequest = (RangeAlertNotificationRequest) m.getRequest();
					// System.out.println("1
					// "+rangeAlertNotificationRequest.getCommunicationRangeLeft());
					// entry of log

					RangeAlertNotificationResponse rangeAlertNotificationResponse = new RangeAlertNotificationResponse();
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(rangeAlertNotificationResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && d.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempDrone = d;
							break;
						}
					}
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && u.getDroneId().equalsIgnoreCase(tempDrone.getId())) {
							tempUser = u;
							break;
						}
					}
					Message requestMessage = new Message();
					requestMessage.setTypeId(1);
					requestMessage.setId("mvp1");
					requestMessage.setDateTime(dateTime);
					requestMessage.setMessage("RangeAlertNotificationRequest");
					jo = (JsonObject) jsonParser.parse(gson.toJson(rangeAlertNotificationRequest));
					requestMessage.setPayload(jo);
					tempUser.getSession().getBasicRemote().sendText(gson.toJson(requestMessage));
					forwardRequestToLoggingServer(gson.toJson(requestMessage));
				} catch (Exception exception) {
					System.out.println("range alert notification exception " + exception);
				}
			} // rangeAlertnotification ends
			if (message.equalsIgnoreCase("HeartBeatRequest")) {
				try {
					System.out.println("Heart beat request invoked");
					// if(timer!=null) timer.cancel();
					int thresold = 3000;
					lastHeartBeatRequestTime = new java.util.Date().getTime();
					HeartBeatRequest heartBeatRequest = (HeartBeatRequest) m.getRequest();
					// entry of log

					HeartBeatResponse heartBeatResponse = new HeartBeatResponse();
					heartBeatResponse.setStatus("Accepted");
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(heartBeatResponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));

					timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							try {
								long currentTime = (new java.util.Date().getTime());
								long difference = currentTime - MainServer2.this.lastHeartBeatRequestTime;
								if (difference >= (thresold + heartBeatResponse.getInterval())) {
									System.out.println("Drone inactive and difference " + difference);
									Drone tempDrone = null;
									drones = (List<Drone>) context.getAttribute("webSocketDrones");
									for (Drone d : drones) {
										if (d.getSession()!=null && d.getSession().getId().equalsIgnoreCase(session.getId())) {
											tempDrone = d;
											break;
										}
									}
									// System.out.println("1 "+tempDrone);
									User tempUser = null;
									users = (List<User>) context.getAttribute("webSocketUsers");
									for (User u : users) {
										if (u.getSession() != null
												&& u.getDroneId().equalsIgnoreCase(tempDrone.getId())) {
											tempUser = u;
											break;
										}
									}
									if (tempUser != null)
										tempUser.setDroneId(null);
									DroneStatusRequest droneStatusRequest = new DroneStatusRequest();
									droneStatusRequest.setStatus("Not Connected");
									JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(droneStatusRequest));
									Message responseMessage2 = new Message();
									responseMessage2.setTypeId(1);
									responseMessage2.setId("h123");
									responseMessage2.setDateTime(dateTime);
									responseMessage2.setMessage("DroneStatusRequest");
									responseMessage2.setPayload(jo);
									if(tempUser!=null) tempUser.getSession().getBasicRemote().sendText(gson.toJson(responseMessage2));
									forwardRequestToLoggingServer(gson.toJson(responseMessage2));
									tempDrone.setIsConnected(false);
									// tempDrone.setSession(null);
									tempDrone.setDroneStatus(null);
									tempDrone.getSession().close();
									// drones.remove(tempDrone);
									// System.out.println("Drones list size "+drones.size());
								} else {
									System.out.println("Drone is active and difference " + difference);
								}
							} catch (Exception exception) {
								System.out.println("Timer class exception " + exception);
							}
							// System.out.println("Difference In Time : "+difference);
							// make a variable lastHeartbeatrequesttime
							// set its value in miliseconds for lastHeartbeat request
							// check if time is change
							// one posiible method to check difference of current time and last heartbeat
							// request time
							// if difference is greater then make drone as not alive otherwise timer will be
							// rescheduled

						}
					}, thresold + heartBeatResponse.getInterval());
				} catch (Exception exception) {
					System.out.println("Heart beat exception " + exception);
				}
			} // heart beat request ends

			if (message.equalsIgnoreCase("TechnicalProblemRequest")) {
				try {
					TechnicalProblemRequest tpr = (TechnicalProblemRequest) m.getRequest();
					System.out.println("Problem in drone " + tpr.getProblem());
					// entry of log

					TechnicalProblemResponse tpresponse = new TechnicalProblemResponse();
					JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(tpresponse));
					responseMessage.setPayload(jo);
					session.getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));

					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && d.getSession().getId().equalsIgnoreCase(session.getId())) {
							tempDrone = d;
							break;
						}
					}
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && tempDrone.getId().equalsIgnoreCase(u.getDroneId())) {
							tempUser = u;
							break;
						}
					}
					Message requestMessage = new Message();
					requestMessage.setTypeId(1);
					requestMessage.setId("mvp2");
					requestMessage.setDateTime(dateTime);
					requestMessage.setMessage("TechnicalProblemRequest");
					jo = (JsonObject) jsonParser.parse(gson.toJson(tpr));
					requestMessage.setPayload(jo);
					tempUser.getSession().getBasicRemote().sendText(gson.toJson(requestMessage));
					forwardRequestToLoggingServer(gson.toJson(requestMessage));
				} catch (Exception exception) {
					System.out.println("Technical problem request exception " + exception);
				}
			} // technical problem request ends
		} // type id = 1 if condition ends
		if (typeId == 2) {
			// response ki id kya he
			// match karna he with request id
			// find ki request kis type ki thi like command ,authenticate, status
			// if else
			if (messageIdMap.containsKey(m.getId())) {
				try {
					System.out.println("command response arrived in server ");

					String value = messageIdMap.get(m.getId());
					// System.out.println("1");
					// System.out.println("session value
					// "+session.getUserProperties().get("messageId"));
					Drone tempDrone = null;
					drones = (List<Drone>) context.getAttribute("webSocketDrones");
					for (Drone d : drones) {
						if (d.getSession()!=null && session.getId() == d.getSession().getId()) {
							tempDrone = d;
							break;
						}
					}
					// System.out.println("2");
					User tempUser = null;
					users = (List<User>) context.getAttribute("webSocketUsers");
					for (User u : users) {
						if (u.getSession() != null && u.getDroneId().equalsIgnoreCase(tempDrone.getId())) {
							tempUser = u;
							break;
						}
					}
					// System.out.println("3");
					if (tempUser == null)
						tempUser = (User) session.getUserProperties().get("UserObject");

					// System.out.println("session value
					// "+tempUser.getSession().getUserProperties().get("messageId"));
					// System.out.println("4");

					String messageId = (String) tempUser.getSession().getUserProperties().get("messageId");
					responseMessage.setId(messageId);
					// response message id check karlena
					// System.out.println("6");

					responseMessage.setPayload(m.getPayload());
					tempUser.getSession().getBasicRemote().sendText(gson.toJson(responseMessage));
					forwardRequestToLoggingServer(gson.toJson(responseMessage));
				} catch (Exception exception) {
					System.out.println("type id 2 command response " + exception);
				}

			}

		}
	}//message handler ends
	public void forwardRequestToLoggingServer(String payload)
	{
		try
		{
			System.out.println("Forwarding request to logging server");
			OutputStream outputStream;
			OutputStreamWriter outputStreamWriter;
			InputStream inputStream;
			InputStreamReader inputStreamReader;
			int portNumber=1000;
			String serverName="localhost";
			Timestamp currentTime;
			currentTime=new Timestamp(new java.util.Date().getTime());
			String request="Drone Application,"+currentTime.toString()+","+payload+"#";
			System.out.println(request);
			Socket socket=new Socket(serverName,portNumber);
			outputStream=socket.getOutputStream();
			outputStreamWriter=new OutputStreamWriter(outputStream);
			outputStreamWriter.write(request);
			outputStreamWriter.flush();
			socket.close();
		}catch(Exception exception)
		{
			System.out.println("Forward request to server exception "+exception);
		}
	}


}
