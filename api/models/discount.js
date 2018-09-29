const mongoose = require('mongoose');

const discountS = mongoose.Schema({
    region: {
        type:String
    },
    name: {
            type:String
        },
    photo: {
            type:String
        },
    price: {
            type:Number
        },
    discount: {
                type:Number
            }


});

module.exports = mongoose.model('discount', discountS);