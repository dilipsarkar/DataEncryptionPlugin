#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface DataEncryption : CDVPlugin <UIAlertViewDelegate> {}

@property (nonatomic, copy) NSString* callbackId;
	- (void)encryption:(CDVInvokedUrlCommand*)command;
    - (void)decryption:(CDVInvokedUrlCommand*)command;
    - (void)setKey:(CDVInvokedUrlCommand*)command;
    - (void)getKey:(CDVInvokedUrlCommand*)command;
@end
