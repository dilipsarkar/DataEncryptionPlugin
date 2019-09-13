//package cordova-plugin-dataencryption;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.crypto.*;
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;
import android.util.Base64;
import android.content.Context;
import android.content.SharedPreferences;

public class DataEncryption extends CordovaPlugin {
	public static final String TAG = "DataEncryption Plugin";
	public static final String MY_PREFS_NAME = "MyPrefsFile";
	Crypto crypto = null;
	Context context = null;
	boolean DEBUG = false;

	public DataEncryption() {}

	/**
	* Sets the context of the Command. This can then be used to do things like
	* get file paths associated with the Activity.
	*
	* @param cordova The context of the main Activity.
	* @param webView The CordovaWebView Cordova is running in.
	*/

	// Initializing the Crypto plugin
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		this.context = cordova.getActivity().getApplicationContext();
		// Defines the Crypto class
		crypto = new Crypto(new SharedPrefsBackedKeyChain(cordova.getActivity().getApplicationContext()), new SystemNativeCryptoLibrary());
	}

	public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		//checks for the input is whether Encryption or Decryption and calls the method accordingly
		if (action.equalsIgnoreCase("encryption")) {
			// Execute the encryptData() Method and returns the encrypted data back to CryptoPlugin.js
			callbackContext.success(this.encryptData(args.getString(0), args.getString(1), args.getString(2)));
			return true;
		} else if (action.equalsIgnoreCase("decryption")) {
			// Execute the decryptData() Method and returns the decrypted data back to CryptoPlugin.js
			callbackContext.success(this.decryptData(args.getString(0), args.getString(1), args.getString(2)));
			return true;
		} else if (action.equalsIgnoreCase("setKey")) {
			// Execute the setKey() Method and stores the value in sharedPrefference
			callbackContext.success(this.setKey(args.getString(0), args.getString(1))); 
			return true;
		} else if (action.equalsIgnoreCase("getKey")) {
			// Execute the getKey() Method and returns the value stored by the setKey()
			callbackContext.success(this.getKey(args.getString(0))); 
			return true;
		}
		return false;
	}

	// Encryption process starts here
	public String encryptData(String keyName, String plainText, String passcode) {
		byte[] encText = null;
		String encTextStr = "";

		if (DEBUG) {
			Log.v(TAG,"encryptData keyName : "+ keyName);
		}

		try {
			String encKey = getKey(keyName) + passcode;
			byte[] data = plainText.getBytes("UTF-8");
			encText = crypto.encrypt(data, new Entity(encKey));		// Encrypting the plain text
			encTextStr = Base64.encodeToString(encText, Base64.DEFAULT);	// Converting the encrypted data to string
			if (DEBUG) {
				Log.v(TAG," Encypted data : "+ encTextStr);
			}
		} catch (Exception e) {
			if (DEBUG) {
				Log.v(TAG," Exception encypted data : "+ e.toString());
			}
		}
		return encTextStr; 
	}

	// Decryption process starts here
	public String decryptData(String keyName, String encText, String passcode) { 
		byte[] plainText = null;
		String plainTxtStr = "";

		if (DEBUG) {
			Log.v(TAG," decryptData keyName : "+ keyName);
		}

		try {
			//TODO: Check for error from getKey()
			String encKey = getKey(keyName) + passcode;
			byte[] data = Base64.decode(encText, Base64.DEFAULT);
			plainText = crypto.decrypt(data, new Entity(encKey));	// Decrypting the plain text
			plainTxtStr = new String(plainText, "UTF-8");	// Converting the Decrypted data to string

			if (DEBUG) {
				Log.v(TAG," Decypted data :: "+ plainTxtStr);
			}
		} catch (Exception e) {
			if (DEBUG) {
				Log.v(TAG,e.toString()+"decryptData Exception");
			}
		}
		return plainTxtStr;
	}

	public String setKey(String keyName, String value) {
		SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit(); // Creates SharedPreferences
		editor.putString(keyName, value);	// Storing data as KEY/VALUE pair

		if (DEBUG) {
			Log.v(TAG,"================ setKey "+ keyName +": "+ value);
		}

		editor.commit();	// Save the changes in SharedPreferences	

        if (editor.commit()) {
			return "true";
		}
		return "false";	
	}

	public String getKey(String keyName) {
		SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
		//Getting SharedPreferences data 
		String restoredText = prefs.getString(keyName, null);	// If value for key not exist then return second param value

		if (restoredText != null) {
			if (DEBUG) {
				Log.v(TAG,"================= getKey "+ keyName +": "+ restoredText);
			}
			return restoredText;
		} else if (DEBUG) {
			Log.v(TAG,"SharedPreference is NULL, may not have been set yet !!");
		}
		return "false";
	}
}