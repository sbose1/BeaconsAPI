const express = require("express");

const morgan = require("morgan");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");


const routes = require('./api/routes/routes');
const app = express();

/**
app.post('/image',function(req,res){

  const image=request.body.image;
  console.log(image);

  res.sendFile(__dirname+'/images/image');
  //__dirname : It will resolve to your project folder.
}); **/



mongoose.connect('mongodb://localhost:27017/test');
mongoose.Promise = global.Promise;

app.use(morgan("dev"));
app.use('/uploads', express.static('uploads'));
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  );
  if (req.method === "OPTIONS") {
    res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
    return res.status(200).json({});
  }
  next();
});

// Routes which should handle requests

app.use("/get", routes);
app.use(express.static('public'))

app.use((req, res, next) => {
  const error = new Error("Not found");
  error.status = 404;
  next(error);
});

app.use((error, req, res, next) => {
  res.status(error.status || 500);
  res.json({
    error: {
      message: error.message
    }
  });
});

module.exports = app;
