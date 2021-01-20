# Centralized-CMS-Server
This is one of my best self-learning project, this project is done by me during internship at thinking machines. Actually, this project made with use of many cross-platform technologies, that's why it a little bit comples.
Basic idea, for this project is, to made a centralized CMS server, from which mobile app and drone communicate. Also, to make a admin panel, which is very secure and from which admin can see whole system.
Currently, commited git repository has only CMS server code, but did not have mobile device and IOT devices code.
Actually, In this project we have made various protocols to communicate across device, you can see our protocols file in documentation folder.

## Features
This project has different parts, and every parts has different features
1) Admin Panel
* Through this panel, admin can see, monitor and restrict all things happering in system.
* Admin can see total number of registered mobile user and drones
* Admin can add, update and delete drone
* Admin can see active drone-mobile pair.
* Admin can see current status of mobile user app and drone. (Means they are on/off)
* Admin can see all logs, according to date, actually, this system is integerated with my logsManagementSystem, where logging server and retrieval server runnning as a separate server to maintain logs.
* Admin can also do installation/signup one time.
* Admin can log-in in only one device at a time, and can log in only when did not logged-in in any other device.
* Admin will be automatically log-out after specified time, we made this time as 30 min.
* Once admin installation completed, no one can go to installation page again.
* Also, whatever admin is doing in this admin panel, all logs are stored in loggin server, and admin can see it.

2) Central Server
* It is the server, where all communication between mobile application and drone happern.
* This is made with websocket protocol, for handling real time application request/response.
* Mobile application and drone, send request to server, according to our protocol and our server will send response json to them, according to our protocol.
* Also, all the request/response handled in this server, will be stored as logs in logging server, which can be seen from admin panel.
* This server is made in tomcat and our admin side services are in spring boot, so we integerated spring boot with tomcat application, by using spring boot as web service framework, by taking their jar files.
* We have also made some request parser, to parse all request and response string and save them in appropriate pojo.
* We tested this server by making a swing application, in this we made two different application for drone and mobile respectively.

3) Mobile application (Currently not uploaded in git)
* In this user can register in a system, at this time user mobile number is verified.
* Then user can log-in in system.
* Then user can see list of drones available.
* Then user can connect to control, where drone controller appear in user screen, and user can control drone, with this application.

