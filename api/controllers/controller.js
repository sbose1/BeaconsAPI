const mongoose = require("mongoose");
const Product = require("../models/product");
const Discount = require("../models/discount");

//get product
module.exports.getProducts = function(req, res){
  const id=req.body.region;

  if (!id) {
    res.status(401).json({
      "message" : "Error: no valid device found"
    });
  } else {
    Product
      .find({region: req.body.region})
      .exec(function(err, product) {
        res.status(200).json(
        //result
        {message:"Request successful",
        product: product,
        status:200}
        )
      });
  }
};
//get discount
module.exports.getDiscounts = function(req, res){
  const id=req.body.region;

  if (!id) {
   Discount
      .find({})
      .exec(function(err, product) {
		  if(err){
		  res.status(401).json({
				"message" : "Error: no valid device found"
			});
			return;
			}
        res.status(200).json(
        //result
        {message:"Request successful",
        product: product,
        status:200}
        )
      });
  } else {
    Discount
      .find({region: req.body.region})
      .exec(function(err, discounts) {
        res.status(200).json(
        //result
        {message:"Request successful",
        product: discounts,
        status:200}
        )
      });
  }
};

//get image
/**module.exports.getImage = function(req, res){
  const image=req.body.image;
  var path= "/images/"+ image;

  if (true) {
    res.status(401).json({
      "message" : "Error: no valid device found"
    });
  } else {
    res.sendFile(__dirname+'path');
      }

}; **/


//add product

module.exports.saveProduct = function(req, res){
const product = new Product({
              _id: new mongoose.Types.ObjectId(),
              region: req.body.region,
              path: req.body.path,
              pname: req.body.pname,
              discount: req.body.discount,
              price: req.body.price

            });
product.save()
              .then(result => {
                console.log(result);
                res.status(201).json({
                  message: "product added",
                  status: 200
                });
              })
              .catch(err => {
                console.log(err);
                res.status(500).json({
                  error: err,
                  status: 500
                });
              }); }

