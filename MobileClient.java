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
class MobileClient
{
	public static void main(String gg[])
	{
		MobileMainClient mainClient=new MobileMainClient();
	}
}
class MobileMainClient extends JFrame implements ActionListener
{
	private JTextField urlTextField,jsonTf;
	private JButton connect,send,disconnect;
	private Container container;
	private WebSocketClient webSocketClient;
	private JTextArea ta;
	private Gson gson;
	public MobileMainClient()
	{
		super("Mobile");
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
					System.out.println("Message arrived At Mobile");
					Message message2=gson.fromJson(message,Message.class);
					if(message2.getTypeId()==1)
					{
						if(message2.getMessage().equalsIgnoreCase("RangeAlertNotificationRequest"))
						{
							RangeAlertNotificationResponse rafr=new RangeAlertNotificationResponse();
							Message responseMessage=new Message();
							responseMessage.setTypeId(2);
							responseMessage.setId(message2.getId());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
							responseMessage.setDateTime(sdf.format(new java.util.Date()));
							JsonParser jsonParser=new JsonParser();
							JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(rafr));
							responseMessage.setPayload(jo);
							webSocketClient.send(gson.toJson(responseMessage));
						}
						if(message2.getMessage().equalsIgnoreCase("DroneStatusRequest"))
						{
							DroneStatusResponse dsr=new DroneStatusResponse();
							Message responseMessage=new Message();
							responseMessage.setTypeId(2);
							responseMessage.setId(message2.getId());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
							responseMessage.setDateTime(sdf.format(new java.util.Date()));
							JsonParser jsonParser=new JsonParser();
							JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(dsr));
							responseMessage.setPayload(jo);
							webSocketClient.send(gson.toJson(responseMessage));	
						}
						if(message2.getMessage().equalsIgnoreCase("BatteryLowRequest"))
						{
							BatteryLowResponse blr=new BatteryLowResponse();
							Message responseMessage=new Message();
							responseMessage.setTypeId(2);
							responseMessage.setId(message2.getId());
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
							responseMessage.setDateTime(sdf.format(new java.util.Date()));
							JsonParser jsonParser=new JsonParser();
							JsonObject jo=(JsonObject)jsonParser.parse(gson.toJson(blr));
							responseMessage.setPayload(jo);
							webSocketClient.send(gson.toJson(responseMessage));
						}
					}
					ta.append(message+"\n");

	
				}
				public void onError(Exception exception)
				{
					System.out.println("Error occured"+exception);
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