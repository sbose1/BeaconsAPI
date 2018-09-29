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


},{collection: 'discount'});

module.exports = mongoose.model('discount', discountS);