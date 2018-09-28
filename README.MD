USER AUTH SERVICE Group3 ABSTRACT •	This project provides sign in, login, and logout features. •	A mechanism to allow the user to access protected resources after a successful login. •	An error reporting mechanism.

### Git link:
https://github.com/sbose1/UserAuthenticationAPI.git

### Architecture overview:
A RESTful API accepting JSON data format client requests. To enable all the capability services of the system and subsystem(s) the architecture for the project includes roughly MVC (Model View Controller) layered components working cohesively to provide expected functionalities and enable navigation within the website. Each View page of the website is backed by one or more functions of the logical tier of the Web application server. The modules consist of the Controller(s) implement handler functions for orchestration and implementation of intended functionalities and business logic. The object state is managed using Model classes and MongoDb schema.

### Technology stack used: RESTful API
UI/Frontend: Mobile App (Android) Middle tier: Node and Express Backend Database: supported using MongoDB Data transition format: JSON Web Authentication: JWT

### Platform Scenarios:
User authentication mobile application using a REST API built with Node, Express and MongoDB. Implementation scenarios handled:

1.let a user signup
2.save user data
3.let a returning user log in
4.keep a logged in user’s session alive between page visits


API Routes to facilitate the above functionalities:

1.User signup: POST request on server_IP:5000/user/signup
2.User login: POST request on server_IP:5000/user/login
3.User get Profile: GET request on server_IP:5000/user/profile/<{user_id}>
4.User edit Profile: PUT request on server_IP:5000/user/profile/<{user_id}>


### The Authentication Flow
Authentication assumptions:

A User is registered or re-visiting in valid token life time
Authentication overall flow: •	user data is stored in MongoDB •	CRUD functions are done using Express API routing — Create (register), Read (login) •	an Mobile application calls the API and curates the responses •	the API generates a JSON Web Token upon registration or login, and passes this to the Mobile application •	the mobile application stores the JWT in order to maintain the user’s session •	it checks the validity of the JWT to display protected routes/views •	the mobile application passes the JWT back to API when calling API secured routes. JWTs are preferred over cookies for maintaining the session state in the browser. Cookies are better for maintaining state when using a server-side application.

### The REST API
Creating the MongoDB Data Schema with Mongoose: user schema defined in /api/models/users.js. User DB schema is defined as trailing:

userSchema = mongoose.Schema({ id: mongoose.Schema.Types.ObjectId, email: { type: String, required: true, unique: true, match: /[a-z0-9!#$%&'*+/=?^{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_{|}~-]+)*@(?:a-z0-9?.)+a-z0-9?/ }, password: { type: String, required: true }, name: { type: String, required: true}, age: { type: Number }, weight: { type: Number }, address:{ type:String } });

Generating a JSON Web Token (JWT): model needs to generate a JWT To create the JWT run this on the command line: npm install jsonwebtoken --save Then require this in the users.js model file: var jwt = require('jsonwebtoken');

Configure API Endpoints: Code in the Controllers respectively

The Profile API Controller- To handle user get and edit profile functionalities
The User API Controller - to handle user registration, login and delete
Securing an API Route- only authenticated users can access the /api/profile route. The way to validate a request is to ensure that the JWT sent with it is genuine and the ID is mapped to the correct user.