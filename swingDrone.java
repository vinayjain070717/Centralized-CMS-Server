import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;
import javax.websocket.Session;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import org.java_websocket.*;
import org.java_websocket.client.*;
import com.google.gson.*;
import org.java_websocket.handshake.*;
import com.thinking.machines.drone.common.*;
import com.thinking.machines.drone.common.response.*;
import com.thinking.machines.drone.common.request.*;
import java.text.*;
public class swingDrone extends JFrame
{
   private JButton statusButton,connectButton,heartBeatButton,statusNotificationButton,rangeAlertNotificationButton,technicalFaultButton;
    private JLabel uriLabel; 
     private JTextField uriField;
    private Container c;
    //private DefaultListModel dlm;
  //  private JList<String> list;
    private JTextArea statusTextArea;
    private WebSocketClient webSocketClient;
    public String request="";
     private JFrame f=new JFrame("Connect"); 
     private Gson gson;
     private HashMap<String,String> idsMap=new HashMap<>();
     Message globalMessage;
    swingDrone()
    {
        gson=new Gson();
         f= new JFrame("Drone List"); 
        c=getContentPane();
//        c.setLayout(new GridLayout(12,3));
        c.setLayout(new FlowLayout());
        uriField=new JTextField(20);
        uriLabel=new JLabel("Enter URI");
        uriLabel.setHorizontalAlignment(JLabel.CENTER);
        uriLabel.setVerticalAlignment(JLabel.CENTER);
        
        connectButton=new JButton("Connect");
        statusButton=new JButton("Status");
        heartBeatButton=new JButton("Heart Beat");
        statusNotificationButton=new JButton("Status Notification");
        rangeAlertNotificationButton=new JButton("Range Alert Notification");
  
        DefaultListModel<String> l1 = new DefaultListModel<>();  
          l1.addElement("Accepted");  
          l1.addElement("Rejected");  
          l1.addElement("Not Implemented");  
        JList<String> list = new JList<>(l1);  
            list.setBounds(100,100, 30,30); 
  

        technicalFaultButton=new JButton("Technical Fault");
        //statusTextArea=new JTextArea(20,10);
        JTextArea statusTextArea = new JTextArea(20,65);  
        JScrollPane scrollableTextArea = new JScrollPane(statusTextArea);  
  
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
  
        //frame.getContentPane().add(scrollableTextArea); 
        statusTextArea.setEditable(false);
        
        

        c.add(uriLabel);
        c.add(uriField);
        c.add(connectButton);
        c.add(scrollableTextArea);
        c.add(heartBeatButton);
        c.add(statusNotificationButton);
        c.add(rangeAlertNotificationButton);
        c.add(technicalFaultButton);
        c.add(list);
        c.add(statusButton);

            connectButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
        {
        if(e.getSource()==connectButton)
        {
            	webSocketClient=new WebSocketClient(URI.create(uriField.getText().trim())){
				public void onOpen(ServerHandshake sh)
				{
					System.out.println("Connection established");
					statusTextArea.append("Connected"+"\n");
				}
				public void onClose(int a,String reason,boolean c)
				{
					// System.out.println(reason);
					System.out.println("Connection closed");
					statusTextArea.setText("Disconnect"+"\n");
				}
				public void onMessage(String message)
				{
					System.out.println("Message arrived");
                    statusTextArea.append(message+"\n");
                    Message m=gson.fromJson(message,Message.class);
					System.out.println("Type Id"+m.getTypeId());
					System.out.println("Message Id"+m.getId());
					System.out.println("Message: "+m.getMessage());
					// System.out.println("Date Time: "+m.getDateTime());
                    System.out.println("PayLoad"+m.getPayload());
                    globalMessage=m;
				
					if(m.getTypeId()==1)
					{
						if(m.getMessage().equalsIgnoreCase("CommandRequest"))
						{
							CommandRequest commandRequest=(CommandRequest)m.getRequest();
							String operations="";
							if(!commandRequest.getMove().equalsIgnoreCase("")) operations+=commandRequest.getMove()+" ";
							if(!commandRequest.getRoll().equalsIgnoreCase("")) operations+=commandRequest.getRoll()+" ";
							if(!commandRequest.getPitch().equalsIgnoreCase("")) operations+=commandRequest.getPitch()+" ";
							if(!commandRequest.getYaw().equalsIgnoreCase("")) operations+=commandRequest.getYaw()+" ";
							if(!commandRequest.getThrottle().equalsIgnoreCase("")) operations+=commandRequest.getThrottle()+" ";

							System.out.println("Commands Recieved "+operations);
							System.out.println("Operations performed");
							// Message responseMessage=new Message();
							// responseMessage.setTypeId(2);
							// responseMessage.setId(m.getId());
							// responseMessage.setDateTime(new java.util.Date());
							// CommandResponse commandResponse=new CommandResponse();
							// commandResponse.setStatus("Accepted");
							// // JsonParser jsonParser=new JsonParser();
							// // JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(commandResponse));
							// // responseMessage.setPayload(jo);
							// // webSocketClient.send(gson.toJson(responseMessage));
							// String response="";
							// response=response+"\"typeId:"+responseMessage.getTypeId()+",id:"+responseMessage.getId()+",dateTime:2020-01-06T20:17:46.384Z,payload:";
							// response=response+"{\"status\":\""+commandResponse.getStatus()+"\",\"reason\":\""+commandResponse.getReason()+"\",\"error\":\""+commandResponse.getError()+"\"}\"";
							// System.out.println(response);
							// //change date and time to current date and time in response
							// webSocketClient.send(response);
						}
					}
   
				}
				public void onError(Exception exception)
				{
					System.out.println("Error occured");
				}
			};
			webSocketClient.connect();

        }
        }
        });

        heartBeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String request = new String();

                if (ae.getSource() == heartBeatButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:pqr1,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:HeartBeatRequest,payload:{}";
                }
                System.out.println('"' + request + '"');
                statusTextArea.append('"' + request + '"' + "\n");
                webSocketClient.send(request);
            }
        });

         statusNotificationButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            String request=new String();
                    
                if(ae.getSource()==statusNotificationButton)
                {
                    request="";
                    request=request+"typeId:1,";
                    request=request+"id:pqr2,";
                    request=request+"dateTime:2020-01-06T20:17:46.384Z,";
                    request=request+"message:StatusNotificationRequest,payload:{";
                    request=request+"\"distanceTravelled\":50,\"latitude\":20.7972,\"longitude\":76.9922,\"altitude\":25,\"speed\":7,\"remainingFlightTime\":12,\"timeElapsed\":10,\"airSpeed\":40}";
                }
                    System.out.println('"'+request+'"');   
					statusTextArea.append('"'+request+'"'+"\n");
                    webSocketClient.send(request);             
            }
        });

             rangeAlertNotificationButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            String request=new String();
                    
                if(ae.getSource()==rangeAlertNotificationButton)
                {
                    request="";
                    request=request+"typeId:1,";
                    request=request+"id:pqr1,";
                    request=request+"dateTime:2020-01-06T20:17:46.384Z,";
                    request=request+"message:RangeAlertNotificationRequest,payload:{";
                    request=request+"\"communicationRangeLeft\":20.3}";
                }
                    System.out.println('"'+request+'"');   
                	statusTextArea.append('"'+request+'"'+"\n");
                    webSocketClient.send(request);             
            }
        });
     technicalFaultButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
            String request=new String();
                    
                if(ae.getSource()==technicalFaultButton)
                {
                    request="";
                    request=request+"typeId:1,";
                    request=request+"id:pqr1,";
                    request=request+"dateTime:2020-01-06T20:17:46.384Z,";
                    request=request+"message:TechnicalProblemRequest,payload:{";
                    request=request+"\"problem\":"+"\"Any\"}";
                }
                    System.out.println('"'+request+'"');   
					statusTextArea.append('"'+request+'"'+"\n");
                    webSocketClient.send(request);             
            }
        });

         statusButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                 String request=new String();
                  int[] selectedIx = list.getSelectedIndices();
                    String selectedCommand="";
                for (int i = 0; i < selectedIx.length; i++) {
                    selectedCommand=list.getModel().getElementAt(selectedIx[i]);
            }
 
                if(ae.getSource()==statusButton && selectedCommand!="")
                {
                    request="";
                    request=request+"typeId:2,";
                    request=request+"id:abc4,";
                    request=request+"dateTime:2020-01-06T20:17:46.384Z,payload:{";
                    request=request+"\"status\":"+'"'+selectedCommand+'"'+"}";
                    System.out.println('"'+request+'"');    
					statusTextArea.append('"'+request+'"'+"\n");
                    Message responseMessage=new Message();
                    responseMessage.setTypeId(2);
                    responseMessage.setId(globalMessage.getId());
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    responseMessage.setDateTime(sdf.format(new java.util.Date()));                    
                    CommandResponse commandResponse=new CommandResponse();
                    commandResponse.setStatus(selectedCommand);
                    // JsonParser jsonParser=new JsonParser();
                    // JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(commandResponse));
                    // responseMessage.setPayload(jo);
                    // webSocketClient.send(gson.toJson(responseMessage));
                    String response="";
                    response=response+"\"typeId:"+responseMessage.getTypeId()+",id:"+responseMessage.getId()+",dateTime:2020-01-06T20:17:46.384Z,payload:";
                    // response=response+"{\"status\":\""+commandResponse.getStatus()+"\",\"reason\":\""+commandResponse.getReason()+"\",\"error\":\""+commandResponse.getError()+"\"}\"";
                    response=response+"{\"status\":\""+commandResponse.getStatus()+"\"}\"";
                    System.out.println(response);
                    webSocketClient.send(response);
                }
                //change date and time to current date and time in response

            }
        });
        

            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(800,600); 
           Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
//            setLocation(d.width/2-400,d.height/2-300);   
        setLocation(d.width/2-300,d.height/2-250);
          setVisible(true);  
    }
    public static void main(String gg[])
    {
        swingDrone s6=new swingDrone();
    }
}
