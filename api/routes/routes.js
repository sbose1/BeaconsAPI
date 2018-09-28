const express = require("express");
const router = express.Router();

const controller = require('../controllers/controller');


router.post("/productlist", controller.getProducts);
router.post("/addproduct", controller.saveProduct);
//router.post("/image", controller.getImage);

module.exports = router;
