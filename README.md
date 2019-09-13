# cordova-plugin-dataencryption
Its based on below encryption libraries
- Android - [Conceal](http://facebook.github.io/conceal/)

# Data Encryption Cordova Plugin
Use this plugin to encrypt and decrypt data sensitive data. Its useful when we want to persist some sensitive data e.g. access token, on application. 

# Installation
```
cordova plugin add cordova-plugin-dataencryption --save
```
# Supported Platforms
- Android

# How does it work?
 - **Set key** - We decide a key(strong enough) using which we will encrypt our data. We would need the same key to decrypt encrypted data. Key can be persisted on the app side, its stored on native storage. If we want to make it more secure then we can also use a passcode which will be used in combination with the key to encrypt the data.
 - **Get Key** - If we persisted key using setKey API, we have to call getKey API to get the key to be able to decrypt encrypted data.
 - **Encryption** - We call encrypt API, along with raw data, key and passcode(optional) to encrypt raw data.
 - **Decryption** - We call decrypt API, along with encrypted data, key and passcode(optional) to decrypt encrypted data.

# Usage
On ```deviceReady``` event(or there after) we have to perform all our actions 
**Data Encryption**
```
let data = 'your_data';
let encKey = 'your_key';
let passcode = 'your_passcode';

DataEncryption.encrypt(function (encryptedData) {
    if (encryptedData.status === 'Success') {
        alert('Encrypted data: '+encryptedData.value);
        //Lets set the key in case we want to persist in the app
    	DataEncryption.setKey(function (r) {
		   	if (r.status === 'Success') {
		    	alert('Key set successfully');
		   	}
		}, 'key', encKey);
    }
}, encKey, data, passcode);
```

**Data Decryption**
```
let encData = 'your_encrypted_data';
let encKey = 'your_key';//same as encryption key, we have to read it using getKey in case its persisted in the app
let passcode = 'your_passcode';//same as encryption passcode

DataEncryption.getKey(function (r) {
   	if (r.status === 'Success') {
    	encKey = r.value;
    	DataEncryption.decrypt(function (decryptedData) {
            if (decryptedData.status === 'Success') {
                alert('Decrypted data'+decryptedData.value);
            }
        }, encKey, encData, passcode);
   	}
}, 'key');

```

# License
BSD