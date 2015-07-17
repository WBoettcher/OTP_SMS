package otp.sms;

import android.telephony.SmsMessage;

/**
 * NOTE: NOT CURRENTLY IN USE.
 * SMS class for internal use in the app.  It acts as a wrapper and provides the ability to attach more data to an SmsMessage object.
 * Could later be used to store details such as whether the message is decrypted, or whether the plaintext should be stored in the app.
 * Created by aharris on 7/15/15.
 */
public class InternalSmsMessage {
    private String sender;
    private String messageText;
    private long receivedAt;

    public InternalSmsMessage(SmsMessage message){
        this.sender = message.getDisplayOriginatingAddress();
        this.messageText = message.getDisplayMessageBody();
        this.receivedAt = message.getTimestampMillis();
    }

    //Getter for sender
    public String getSender(){
        return sender;
    }
    //Setter for sender
    public void setSender(String sender){
        this.sender = sender;
    }
    //Getter for message
    public String getMessage() {
        return messageText;
    }
    public void setMessage(String messageText){
        this.messageText = messageText;
    }

    public long getTimestamp(){
        return receivedAt;
    }
}
