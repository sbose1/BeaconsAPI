USER AUTH SERVICE Group3 ABSTRACT •	This project provides sign in, login, and logout features. •	A mechanism to allow the user to access protected resources after a successful login. •	An error reporting mechanism.

### Git link:
https://github.com/sbose1/BeaconsAPI.git

### Architecture overview:
A RESTful API accepting JSON data format client requests. To enable all the capability services of the system and subsystem(s) the architecture for the project includes roughly MVC (Model View Controller) layered components working cohesively to provide expected functionalities and enable navigation within the website. Each View page of the website is backed by one or more functions of the logical tier of the Web application server. The modules consist of the Controller(s) implement handler functions for orchestration and implementation of intended functionalities and business logic. The object state is managed using Model classes and MongoDb schema.

### Technology stack used: RESTful API
UI/Frontend: Mobile App (Android) Middle tier: Node and Express Backend Database: supported using MongoDB Data transition format: JSON Web Authentication: JWT

### Platform Scenarios:
User authentication mobile application using a REST API built with Node, Express and MongoDB. Implementation scenarios handled:




