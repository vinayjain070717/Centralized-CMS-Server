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
import java.text.*;
public class swingMobile extends JFrame {
    private JButton sendListOfDeviceButton, connectButton1, loginButton, upButton, downButton, leftButton, rightButton,
            forwardButton, backwardButton, fasterButton, slowerButton, clockwiseButton, anticlockwiseButton, landButton,
            releaseDroneButton, connectButton, statusButton;
    private JLabel listLabel, uriLabel, moveLabel, rollLabel, pitchLabel, yawLabel, landLabel, throttleLabel,
            releaseDroneLabel, userNameLabel, passwordLabel;
    private JTextField userNameTextField, uriField;
    private JPasswordField passwordTextField;
    private Container c;
    private DefaultListModel dlm;
    private JList<String> list;
    private JTextArea statusTextArea;
    private WebSocketClient webSocketClient;
    public String request = "";
    private JFrame f = new JFrame("Connect");
    private Gson gson;
    private HashMap<String, String> idsMap = new HashMap<>();

    swingMobile() {
        gson = new Gson();
        f = new JFrame("Drone List");
        c = getContentPane();
        // c.setLayout(new GridLayout(12,3));
        c.setLayout(new FlowLayout());
        uriField = new JTextField(20);
        uriLabel = new JLabel("Enter URI");
        uriLabel.setHorizontalAlignment(JLabel.CENTER);
        uriLabel.setVerticalAlignment(JLabel.CENTER);

        connectButton = new JButton("Connect");
        statusButton = new JButton("Status");
        // statusTextArea=new JTextArea(20,10);
        JTextArea statusTextArea = new JTextArea(20, 65);
        JScrollPane scrollableTextArea = new JScrollPane(statusTextArea);

        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // frame.getContentPane().add(scrollableTextArea);
        statusTextArea.setEditable(false);
        sendListOfDeviceButton = new JButton("Send List of Devices");
        listLabel = new JLabel("List of Drones");
        listLabel.setHorizontalAlignment(JLabel.CENTER);
        listLabel.setVerticalAlignment(JLabel.CENTER);
        // statusTextArea.setHorizontalAlignment(JLabel.CENTER);
        // statusTextArea.setVerticalAlignment(JLabel.CENTER);
        userNameLabel = new JLabel("UserName");
        userNameLabel.setHorizontalAlignment(JLabel.CENTER);
        userNameLabel.setVerticalAlignment(JLabel.CENTER);

        passwordLabel = new JLabel("Password");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setVerticalAlignment(JLabel.CENTER);

        userNameTextField = new JTextField(10);
        passwordTextField = new JPasswordField(10);
        loginButton = new JButton("Login");

        connectButton1 = new JButton("Connect");

        // DefaultListModel<String> l1 = new DefaultListModel<>();
        // l1.addElement("1");
        // l1.addElement("2");
        // l1.addElement("3");
        // l1.addElement("4");
        // l1.addElement("5");
        list = new JList<>();
        list.setBounds(100, 100, 30, 30);

        upButton = new JButton("Up");
        downButton = new JButton("Down");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        forwardButton = new JButton("Forward");
        backwardButton = new JButton("Backward");
        clockwiseButton = new JButton("Clockwise");
        anticlockwiseButton = new JButton("Anticlockwise");
        fasterButton = new JButton("Faster");
        slowerButton = new JButton("Slower");
        landButton = new JButton("Land");
        moveLabel = new JLabel("Move");
        moveLabel.setHorizontalAlignment(JLabel.CENTER);
        moveLabel.setVerticalAlignment(JLabel.CENTER);

        rollLabel = new JLabel("Roll");
        rollLabel.setHorizontalAlignment(JLabel.CENTER);
        rollLabel.setVerticalAlignment(JLabel.CENTER);

        pitchLabel = new JLabel("Pitch");
        pitchLabel.setHorizontalAlignment(JLabel.CENTER);
        pitchLabel.setVerticalAlignment(JLabel.CENTER);

        yawLabel = new JLabel("Yaw");
        yawLabel.setHorizontalAlignment(JLabel.CENTER);
        yawLabel.setVerticalAlignment(JLabel.CENTER);

        throttleLabel = new JLabel("Throttle");
        throttleLabel.setHorizontalAlignment(JLabel.CENTER);
        throttleLabel.setVerticalAlignment(JLabel.CENTER);

        landLabel = new JLabel("Land");
        landLabel.setHorizontalAlignment(JLabel.CENTER);
        landLabel.setVerticalAlignment(JLabel.CENTER);

        releaseDroneButton = new JButton("Release Drone");
        releaseDroneLabel = new JLabel("Release");
        releaseDroneLabel.setHorizontalAlignment(JLabel.CENTER);
        releaseDroneLabel.setVerticalAlignment(JLabel.CENTER);

        c.add(uriLabel);
        c.add(uriField);
        c.add(connectButton);
        c.add(scrollableTextArea);
        c.add(userNameLabel);
        c.add(userNameTextField);
        c.add(new JLabel(""));
        c.add(passwordLabel);
        c.add(passwordTextField);
        c.add(loginButton);
        c.add(sendListOfDeviceButton);

        c.add(listLabel);
        c.add(list);
        c.add(connectButton1);

        c.add(moveLabel);
        c.add(upButton);
        c.add(downButton);
        c.add(rollLabel);
        c.add(leftButton);
        c.add(rightButton);
        c.add(pitchLabel);
        c.add(forwardButton);
        c.add(backwardButton);
        c.add(yawLabel);
        c.add(clockwiseButton);
        c.add(anticlockwiseButton);
        c.add(throttleLabel);
        c.add(fasterButton);
        c.add(slowerButton);
        c.add(landLabel);
        c.add(landButton);
        c.add(new JLabel(""));

        c.add(releaseDroneLabel);
        c.add(releaseDroneButton);
        c.add(new JLabel(""));
        c.add(statusButton);

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == connectButton) {
                    webSocketClient = new WebSocketClient(URI.create(uriField.getText().trim())) {
                        public void onOpen(ServerHandshake sh) {
                            System.out.println("Connection established");
                            statusTextArea.append("Connected" + "\n");
                        }

                        public void onClose(int a, String reason, boolean c) {
                            // System.out.println(reason);
                            System.out.println("Connection closed");
                            statusTextArea.setText("Disconnect" + "\n");
                        }

                        public void onMessage(String message) {
                            System.out.println("Message arrived");
                            statusTextArea.append(message + "\n");
                            Message message2 = gson.fromJson(message, Message.class);
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            String currentDate=sdf.format(new java.util.Date());

                            System.out.println("2"+message2.getTypeId()+" "+message2.getMessage());
                            if (message2.getTypeId() == 1) {
                                if (message2.getMessage().equalsIgnoreCase("RangeAlertNotificationRequest")) {
                                    RangeAlertNotificationResponse rafr = new RangeAlertNotificationResponse();
                                    Message responseMessage = new Message();
                                    responseMessage.setTypeId(2);
                                    responseMessage.setId(message2.getId());
                                    // responseMessage.setDateTime(currentDate);
                                    JsonParser jsonParser = new JsonParser();
                                    JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(rafr));
                                    responseMessage.setPayload(jo);
String jsonToSend="typeId:2,id:"+message2.getId()+",dateTime:"+currentDate+",message:RangeAlertNotificationResponse,payload:{}";
                                    statusTextArea.append(jsonToSend);
                                    webSocketClient.send(jsonToSend);
                                }
                                if (message2.getMessage().equalsIgnoreCase("DroneStatusRequest")) {
                                    DroneStatusResponse dsr = new DroneStatusResponse();
                                    Message responseMessage = new Message();
                                    responseMessage.setTypeId(2);
                                    responseMessage.setId(message2.getId());
                                    // responseMessage.setDateTime(currentDate);
                                    JsonParser jsonParser = new JsonParser();
                                    JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(dsr));
                                    responseMessage.setPayload(jo);
                                    String jsonToSend="typeId:2,id:"+message2.getId()+",dateTime:"+currentDate+",message:DroneStatusResponse,payload:{}";
                                    statusTextArea.append(jsonToSend);
                                    webSocketClient.send(jsonToSend);
                                }
                                if (message2.getMessage().equalsIgnoreCase("BatteryLowRequest")) {
                                    BatteryLowResponse blr = new BatteryLowResponse();
                                    Message responseMessage = new Message();
                                    responseMessage.setTypeId(2);
                                    responseMessage.setId(message2.getId());
                                    // responseMessage.setDateTime(currentDate);
                                    JsonParser jsonParser = new JsonParser();
                                    JsonObject jo = (JsonObject) jsonParser.parse(gson.toJson(blr));
                                    responseMessage.setPayload(jo);
                                    String jsonToSend="typeId:2,id:"+message2.getId()+",dateTime:"+currentDate+",message:BatteryLowResponse,payload:{}";
                                    statusTextArea.append(jsonToSend);
                                    webSocketClient.send(jsonToSend);
                                }
                            }
                            if (message2.getTypeId() == 2) {
                                String value = idsMap.get(message2.getId());
                                if (value != null && value.equalsIgnoreCase("SendListOfDevicesRequest")) {
                                    dlm = new DefaultListModel<>();
                                    SendListOfDevicesResponse s = gson.fromJson(message2.getPayload(),
                                            SendListOfDevicesResponse.class);
                                    // java.util.List<HashMap<String, String>> listMap = s.getDevices();
                                    HashMap<String, String> listMap = s.getDevices();
                                    listMap.forEach((k, v) -> {
                                        dlm.addElement(k);
                                    });
                                    list.setModel(dlm);
                                    System.out.println(4);
                                }
                            }

                        }

                        public void onError(Exception exception) {
                            System.out.println(1);
                            System.out.println("Error occured" + exception);
                        }
                    };
                    webSocketClient.connect();

                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String request = new String();

                if (ae.getSource() == loginButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc2,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:AuthenticateRequest,payload:{";
                    request = request + "\"username\":" + '"' + userNameTextField.getText() + '"' + ",";
                    request = request + "\"password\":" + '"' + passwordTextField.getText() + '"' + "}";
                }
                System.out.println(request);
                statusTextArea.append(request+"\n");
                webSocketClient.send(request);
            }
        });

        sendListOfDeviceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String request = new String();

                if (ae.getSource() == sendListOfDeviceButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc1,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:SendListOfDevicesRequest,payload:{}";
                }
                System.out.println('"' + request + '"');
                statusTextArea.append('"' + request + '"' + "\n");
                idsMap.put("abc1", "SendListOfDevicesRequest");
                webSocketClient.send(request);
            }
        });
        connectButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String request = new String();
                int[] selectedIx = list.getSelectedIndices();
                String selectedDrone = "";
                for (int i = 0; i < selectedIx.length; i++) {
                    selectedDrone = list.getModel().getElementAt(selectedIx[i]);
                }

                if (ae.getSource() == connectButton1 && selectedDrone != "") {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc4,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:AssignDeviceRequest,payload:{";
                    request = request + "\"deviceId\":" + '"' + selectedDrone + '"' + "}";
                    statusTextArea.append('"' + request + '"' + "\n");
                    System.out.println('"' + request + '"');
                }
                webSocketClient.send(request);

            }
        });

        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == upButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc5,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"move\":" + "\"up\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);

            }
        });
        downButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == downButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc6,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"move\":" + "\"down\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");

                }
                webSocketClient.send(request);
            }
        });
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == leftButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc7,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"roll\":" + "\"left\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == rightButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc8,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"roll\":" + "\"right\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == forwardButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc9,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"pitch\":" + "\"forward\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });
        backwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == backwardButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc10,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"pitch\":" + "\"backward\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });
        clockwiseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == clockwiseButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc11,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"yaw\":" + "\"clockwise\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");

                }
                webSocketClient.send(request);
            }
        });
        anticlockwiseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == anticlockwiseButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc12,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"yaw\":" + "\"antiClockwise\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");

                }
                webSocketClient.send(request);
            }
        });
        fasterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == fasterButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc13,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"throttle\":" + "\"Faster\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });
        slowerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == slowerButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc14,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"throttle\":" + "\"slower\"}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);

            }
        });
        landButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == landButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc15,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:CommandRequest,payload:{";
                    request = request + "\"land\":" + "true}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);

            }
        });
        releaseDroneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == releaseDroneButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc16,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:ReleaseDroneRequest,payload:{}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);

            }
        });
        statusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == statusButton) {
                    request = "";
                    request = request + "typeId:1,";
                    request = request + "id:abc61,";
                    request = request + "dateTime:2020-01-06T20:17:46.384Z,";
                    request = request + "message:StatusRequest,payload:{}";
                    System.out.println('"' + request + '"');
                    statusTextArea.append('"' + request + '"' + "\n");
                }
                webSocketClient.send(request);
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // setLocation(d.width/2-400,d.height/2-300);
        setLocation(d.width / 2 - 300, d.height / 2 - 250);
        setVisible(true);
    }

    public static void main(String gg[]) {
        swingMobile s5 = new swingMobile();
    }
}
