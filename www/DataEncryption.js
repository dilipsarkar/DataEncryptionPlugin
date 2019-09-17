//Binds the exec() method to cordova plugin
var exec = require('cordova/exec');

function DataEncryption() {}

DataEncryption.prototype.encrypt = function(doneCallback, keyName, plainText, passcode) {
	exec(function(result) {
        //Success callback    
        var successResult = {
            "value": result,
            "status": "Success"
        };
        doneCallback(successResult);
    }, function(result) {
        //Failure callback
        var errorResult = {
            "Error": result,
            "status": "Error"
        };
        doneCallback(errorResult); 
    }, "DataEncryption","encryption",[keyName, plainText, passcode]);
} 


DataEncryption.prototype.decrypt = function(doneCallback, keyName, encText, passcode) {
	exec(function (result) {
        //Success callback
        var successResult = {
            "value": result,
            "status": "Success"
        };
        doneCallback(successResult);
    }, function (result) {
        //Failure callback
        var errorResult = {
            "Error": result,
            "status": "Error"
        };
        doneCallback(errorResult);
    }, "DataEncryption","decryption",[keyName, encText, passcode]);
}

DataEncryption.prototype.setKey = function(doneCallback, keyName, value) {
	exec(function (result) {
        //Success callback
        var successResult = {
            "value" : result,
            "status": "Success"
        };
        doneCallback(successResult);
    },

    // Error function 
    function (result) {
        //Failure callback
        var errorResult = {
            "status": "Error"
        };
        doneCallback(errorResult);
    }, "DataEncryption", "setKey", [keyName, value]);
}


DataEncryption.prototype.getKey = function(doneCallback, keyName) {
	exec(function (result) {
        //Success callback
        var successResult = {
            "status": "Success",
            "value" : result
        };
        doneCallback(successResult);
    },
    // Error function 
    function (result) {
        //Failure callback
        var errorResult = {
            "status": "Error"
        };
        doneCallback(errorResult);
    }, "DataEncryption","getKey",[keyName]);
}

var dataEncryption = new DataEncryption(); 
module.exports = dataEncryption;