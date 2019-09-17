# cordova-plugin-dataencryption
Its based on below encryption libraries
- Android - [Conceal](http://facebook.github.io/conceal/)
- iOS - [AESCrypt](https://github.com/Gurpartap/AESCrypt-ObjC)

# Data Encryption Cordova Plugin
Use this plugin to encrypt and decrypt data sensitive data. Its useful when we want to persist some sensitive data e.g. access token, on application. 

# Installation
```
cordova plugin add cordova-plugin-dataencryption --save
```
# Supported Platforms
- Android
- iOS

# How does it work?
 - **Set key** - Define encryption key(strong enough) which will be used to encrypt and decrypt your data. setKey API is called to persist a key on native storage in a format of (key, value) pair. It enables us to be able to use different keys for different encryption. During encryption/decryption we need to pass the key name(not the value) to be used for encryption/decryption. Setting keys should be the very first things we need to do. If you want to make it more secure then you can also use a passcode which will be used in combination with the key to encrypt your data.
 - **Get Key** - If your key was persisted using setKey API, getKey API will return your key value.
 - **Encryption** - Call encrypt API, along with raw data, key name and passcode(optional) to encrypt your raw data.
 - **Decryption** - Call decrypt API, along with encrypted data, key name and passcode(optional) to decrypt your encrypted data.

# Usage
On ```deviceReady``` event(or there after) we have to perform all our actions 

**Data Encryption**
```
let data = 'Dilip Sarkar';
let encKey = 'RWZFSjazKOmUgu02';
let passcode = '12345';

DataEncryption.setKey(function (r) {
	if (r.status === 'Success') {
		DataEncryption.encrypt(function (encryptedData) {
			if (encryptedData.status === 'Success') {
				alert('Encrypted data: '+encryptedData.value);
				console.log('Encrypted data: '+encryptedData.value);
				localStorage.setItem('data', encryptedData.value);
			}
		}, 'encKey', data, passcode);
	}
}, 'encKey', encKey);
```

**Data Decryption**
```
let encData = 'your_encrypted_data';
let passcode = 'your_passcode';//same as encryption passcode

DataEncryption.decrypt(function (decryptedData) {
	if (decryptedData.status === 'Success') {
		alert('Decrypted data: '+decryptedData.value);
	}
}, 'encKey', encData, passcode);
```

# License
BSD