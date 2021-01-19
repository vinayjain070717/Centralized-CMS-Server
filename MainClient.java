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
import com.thinking.machines.drone.common.request.*;
import com.thinking.machines.drone.common.response.*;
import com.thinking.machines.drone.common.*;
import java.text.*;
class psp
{
	public static void main(String gg[])
	{
		MainClient mainClient=new MainClient();
	}
}
class MainClient extends JFrame implements ActionListener
{
	private JTextField urlTextField,jsonTf;
	private JButton connect,send,disconnect;
	private Container container;
	private WebSocketClient webSocketClient;
	private JTextArea ta;
	private Gson gson;
	public MainClient()
	{
		container=getContentPane();
		container.setLayout(new FlowLayout());
		urlTextField=new JTextField(40);
		container.add(urlTextField);
		connect=new JButton("Connect");
		connect.addActionListener(this);
		container.add(connect);
		jsonTf=new JTextField(40);
		send=new JButton("Send Json");
		send.addActionListener(this);
		container.add(jsonTf);
		container.add(send);
		disconnect=new JButton("Disconnect");
		disconnect.addActionListener(this);
		container.add(disconnect);
		JScrollPane scroll=new JScrollPane();
		ta=new JTextArea(15,70);
		ta.setEditable(false);
		scroll.setViewportView(ta);
		container.add(scroll);
		setVisible(true);
		setSize(800,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		gson=new Gson();
	}
	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getSource()==connect)
		{
			System.out.println("cool");											
			webSocketClient=new WebSocketClient(URI.create(urlTextField.getText().trim())){
				public void onOpen(ServerHandshake sh)
				{
					System.out.println("Connection established");
					// statusLabel.setText("Connected");
					ta.append("Connected\n");
				}
				public void onClose(int a,String reason,boolean c)
				{
					// System.out.println(reason);
					System.out.println("Connection closed");
					ta.setText("");
					// statusLabel.setText("Disconnect");
					ta.append("Disconnected\n");
				}
				public void onMessage(String message)
				{
					System.out.println("Message arrived");
					// statusLabel.setText(message);
					// Parser2 p=new Parser2(message);
					ta.append(message+"\n");
					Message m=gson.fromJson(message,Message.class);
					System.out.println("Type Id"+m.getTypeId());
					System.out.println("Message Id"+m.getId());
					System.out.println("Message: "+m.getMessage());
					System.out.println("Date Time: "+m.getDateTime());
					System.out.println("PayLoad"+m.getPayload());

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
							Message responseMessage=new Message();
							responseMessage.setTypeId(2);
							responseMessage.setId(m.getId());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
							responseMessage.setDateTime(sdf.format(new java.util.Date()));	
							CommandResponse commandResponse=new CommandResponse();
							commandResponse.setStatus("Accepted");
							// JsonParser jsonParser=new JsonParser();
							// JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(commandResponse));
							// responseMessage.setPayload(jo);
							// webSocketClient.send(gson.toJson(responseMessage));
							String response="";
							response=response+"\"typeId:"+responseMessage.getTypeId()+",id:"+responseMessage.getId()+",dateTime:2020-01-06T20:17:46.384Z,payload:";
							response=response+"{\"status\":\""+commandResponse.getStatus()+"\",\"reason\":\""+commandResponse.getReason()+"\",\"error\":\""+commandResponse.getError()+"\"}\"";
							System.out.println(response);
							//change date and time to current date and time in response
							webSocketClient.send(response);
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
		if(ev.getSource()==send)
		{
			System.out.println("send button ka action perform hua");
			webSocketClient.send(jsonTf.getText().trim());
		}
		if(ev.getSource()==disconnect)
		{
			System.out.println("disconnect button invoked");
			webSocketClient.close();
		}
	}
}