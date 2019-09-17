#import "DataEncryption.h"
#import "Cipher.h"

@implementation DataEncryption

@synthesize callbackId;

- (void)pluginInitialize
{
}


- (void)encryption:(CDVInvokedUrlCommand*)command
{
    self.callbackId = command.callbackId;
    
	NSString *keyName = [command argumentAtIndex:0];
	NSString *plainText = [command argumentAtIndex:1];
	NSString *passcode = [command argumentAtIndex:2];
    
    /*NSLog(@" keyName : %@",keyName);
    NSLog(@" plainText : %@",plainText);
    NSLog(@" passcode : %@",passcode);*/
    
    NSString *aValue = [[NSUserDefaults standardUserDefaults] objectForKey:keyName];
    NSString *encKey = [NSString stringWithFormat:@"%@%@", aValue, passcode];
    NSString *getEncVal = [CryptoFun encrypt:plainText password:encKey];
    
    //NSLog(@" getEncVal : %@",getEncVal);
    
    if(getEncVal == nil || getEncVal == (id)[NSNull null]){
        NSLog(@"May be value not returned from encrypt Method or null Parameter");
    }
    else{
        [self successWithMessage:[NSString stringWithFormat:@"%@",getEncVal]];
    }
}


- (void)decryption:(CDVInvokedUrlCommand*)command
{
    self.callbackId = command.callbackId;
    
    NSString *keyName = [command argumentAtIndex:0];
    NSString *encText = [command argumentAtIndex:1];
    NSString *passcode = [command argumentAtIndex:2];
    
    NSString *aValue = [[NSUserDefaults standardUserDefaults] objectForKey:keyName];
    NSString *decKey = [NSString stringWithFormat:@"%@%@", aValue, passcode];
    NSString *getDecVal = [CryptoFun decrypt:encText password:decKey];
    
    //NSLog(@" getDecVal : %@",getDecVal);
    
    if(getDecVal == nil || getDecVal == (id)[NSNull null]){
        NSLog(@"May be value not returned from decrypt Method or null Parameter");
    }
    else{
        [self successWithMessage:[NSString stringWithFormat:@"%@",getDecVal]];
    }
    
}


- (void)setKey:(CDVInvokedUrlCommand*)command
{
    self.callbackId = command.callbackId;
    
    NSString *keyName = [command argumentAtIndex:0];
    NSString *value = [command argumentAtIndex:1];
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setValue:value forKey:keyName];
    
    if(defaults == nil || defaults == (id)[NSNull null]){
        NSLog(@" Key,value may not stored in NSUserDefaults");
    }
    else{
        [self successWithMessage:[NSString stringWithFormat:@"true"]];
    }
}


- (void)getKey:(CDVInvokedUrlCommand*)command
{
    self.callbackId = command.callbackId;
    
    NSString *keyName = [command argumentAtIndex:0];
    NSString *aValue = [[NSUserDefaults standardUserDefaults] objectForKey:keyName];
    
    if(aValue == nil || aValue == (id)[NSNull null]){
        NSLog(@" getKey parameter may be empty!!");
    }
    else{
        [self successWithMessage:[NSString stringWithFormat:@"%@", aValue]];
    }
}


- (void)successWithMessage:(NSString *)message
{
   CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}


@end
