BEACONS API Group3 
 
### ABSTRACT
 •	This project provides fetching the list of products based on the region Id from the Beacon in the mobile app
 •	An error reporting mechanism.

### Git link:
https://github.com/sbose1/BeaconsAPI.git

### Architecture overview:
A RESTful API accepting JSON data format client requests. To enable all the capability services of the system and subsystem(s) the architecture for the project includes roughly MVC (Model View Controller) layered components working cohesively to provide expected functionalities and enable navigation within the website. Each View page of the website is backed by one or more functions of the logical tier of the Web application server. The modules consist of the Controller(s) implement handler functions for orchestration and implementation of intended functionalities and business logic. The object state is managed using Model classes and MongoDb schema.

### Technology stack used: RESTful API
UI/Frontend: Mobile App (Android) Middle tier: Node and Express Backend Database: supported using MongoDB Data transition format: JSON Web Authentication: JWT

### Platform Scenarios:
User authentication mobile application using a REST API built with Node, Express and MongoDB. Implementation scenarios handled:

### Oscillation Algorithm
We have used the concept of thresholding. there are two variables badRSSI which is tuned to 60 and a good rssi which is tuned to 75. As the beacon list is scanned the algorithm check if the discovered beacons are among the desired beacons and if so what is there RSSI value which then checks if this RSS is better than good RSSI or lessesr than bad RSSI. If the RSSI of current beacon drops below bad RSSI and one of the scanned beacon has RSSI better than good RSSI then it is considered to be the current RSSI. Thus till if the beacon is selected and RSSI is between 60-75 it stays connected and if none of the desired beacons are available in ranging list then the default value for the list is shown.

Read the presentation.ppt in root folder for demo screenshots
