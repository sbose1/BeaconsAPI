const mongoose = require('mongoose');

const prodSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,

    region: {
        type:String
    },
    path: {
            type:String
        },
    pname: {
            type:String
        },
    discount: {
            type:Number
        },
    price: {
                type:Number
            }


});

module.exports = mongoose.model('Product', prodSchema);