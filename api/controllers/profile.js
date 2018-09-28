var mongoose = require('mongoose');
const User = require("../models/user");
const jwt = require("jsonwebtoken");

//get User profile
module.exports.getProfile = function(req, res){
  const id=req.userData.userId;
  if (!id) {
    res.status(401).json({
      "message" : "UnauthorizedError: private profile"
    });
  } else {
    User
      .findById(id)
      .exec(function(err, user) {
        res.status(200).json(
        user
       // {message:"Request successful",
        //user:user,
        //status:200}
        )
      });
  }
};

//submit and save response to questions for each user
module.exports.saveResponse = function(req,res){
  const id=req.userData.userId;
  User.findByIdAndUpdate(
    id,
    {
      $set:{
        survey:req.body.survey
      }
    },
    {new: true},
    function(err,result){
    if(err){
      console.log(err);
      res.status(500).json({
        error:err,
        status:500
      });
    }else{
      console.log(result);
      res.status(200).json(
    //  message:"Request successful",
      result
      //status:200
      );
     // console.log(result);
    }
    });

};

//get User result
module.exports.getResult = function(req, res){
  const id=req.userData.userId;
  if (!id) {
    res.status(401).json({
      "message" : "UnauthorizedError: private profile"
    });
  } else {
    User
      .findById(id)
      .exec(function(err, user) {

      //calculation
       //get response and for each ques & check range
              user



        res.status(200).json(

       // {message:"Request successful",
        //user:user,
        //status:200}

        {
        score:score,
        status:200
        }

        )
      });
  }
};


module.exports.editProfile = function(req,res){
  const id=req.userData.userId;
  User.findByIdAndUpdate(
    id,
    {
      $set:{
        name:req.body.name,
        age:req.body.age,
        weight:req.body.weight,
        address:req.body.address
      }
    },
    {new: true},
    function(err,result){
    if(err){
      console.log(err);
      res.status(500).json({
        error:err,
        status:500
      });
    }else{
      console.log(result);
      res.status(200).json(
    //  message:"Request successful",
      result
      //status:200
      );
     // console.log(result);
    }
    });

};

//

exports.getProducts = (req, res, next) => {
  User.findAll({ email: req.body.email }) //check if email id exists before in DB
    .exec()
    .then(user => {
      if (user.length >= 1) {
        return res.status(409).json({
          message: "Email address exists",
          status: 409
        });
      } else {
        bcrypt.hash(req.body.password, 10, (err, hash) => { //hashing and salting password
          if (err) {
            return res.status(500).json({
              error: err,
              status: 500
            });
          } else {
            const user = new User({
              _id: new mongoose.Types.ObjectId(),
              email: req.body.email,
              password: hash,
              name: req.body.name,
              age: req.body.age,
              weight: req.body.weight,
              address:req.body.address
            });
            user
              .save()
              .then(result => {
                console.log(result);
                res.status(201).json({
                  message: "User created",
                  status: 200
                });
              })
              .catch(err => {
                console.log(err);
                res.status(500).json({
                  error: err,
                  status: 500
                });
              });
          }
        });
      }
    });
};

exports.user_login = (req, res, next) => {
  User.find({ email: req.body.email })
    .exec()
    .then(user => {
      if (user.length < 1) {
        return res.status(401).json({
          message: "Auth failed",
          status: 401
        });
      }
      bcrypt.compare(req.body.password, user[0].password, (err, result) => {
        if (err) {
          return res.status(401).json({
            message: "Auth failed",
            status: 401
          });
        }
        if (result) {
          const token = jwt.sign(
            { //payload
              //email: user[0].email,
              userId: user[0]._id
            },
            process.env.JWT_KEY, //private key
            {
              expiresIn: "1h"
            }
          );
          return res.status(200).json({
            message: "Auth successful",
            token: token,
           // userId:user[0]._id,
            status: 200
          });
        }
        res.status(401).json({
          message: "Auth failed",
          status: 401
        });
      });
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err,
        status: 500
      });
    });
};

exports.user_delete = (req, res, next) => {
  User.remove({ _id: req.params.userId })
    .exec()
    .then(result => {
      res.status(200).json({
        message: "User deleted",
        status: 200
      });
    })
    .catch(err => {
      console.log(err);
      res.status(500).json({
        error: err,
        status: 500
      });
    });
};

