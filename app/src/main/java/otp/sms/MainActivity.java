/**
 * MainActivity - Java file used to operate the MainActivity for the app.  
 * It is currently acting as a testing activity; it is the activity that launches when the program is run on a device.
 * The code is currently exploring how to integrate Intents into a program.
 * Status tracker:
 * [X]  Show user phone's native contact picker after a button push
 * []   Allow user to select which phone number to use if a contact has multiple registered
 * []   Print the selected phone number in the top line of the interface.
 **/

package otp.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView indicator;
    EditText phoneNumber;
    EditText smsContents;
    BroadcastReceiver smsReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indicator = (TextView) findViewById(R.id.indicator);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        smsContents = (EditText) findViewById(R.id.smsContents);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v("Broadcast Receiver", "Message Received");
                //Gets the bundle associated with the incoming Intent
                Bundle incomingBundle = intent.getExtras();

                //Names the array for the incoming messages
                SmsMessage[] receivedMessageParts;

                String parsedMessage = null;

                //Checks to make sure there is a meaningful bundle to parse
                if (incomingBundle != null) {
                    //Gets the relevant part of the intent
                    Object[] pdus = (Object[]) incomingBundle.get("pdus");
                    //Sets the parsedMessages array to the correct length
                    receivedMessageParts = new SmsMessage[pdus.length];

                    //Cycles through the received messages in the bundle
                    for (int i = 0; i < receivedMessageParts.length; i++) {
                        //Converts the PDU to an SmsMessage object
                        receivedMessageParts[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        parsedMessage += "From " + receivedMessageParts[i].getOriginatingAddress() + " : ";
                        parsedMessage += receivedMessageParts[i].getMessageBody()+ "\n";
                    }
                }
                indicator.setText("Message Received");
            }
        }, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    /**
     * Function that handles when the contact button is pushed
     *
     * @param v - the View that the button is in (needs review later)
     */
    public void onContactButton(View v) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, 1001);
        //TODO: Extract relevant data from the contact that the user selects in the contact picker.
    }

    /**
     * Function that sends the message in the smsContents field to the phone number in the phoneNumber field.
     * Also checks whether the send was successful, and indicates send-failure.
     * @param v - the View that the button is in (needs review)
     */
    public void onSendButton(View v){
        //Pulls data from the form in the UI
        String _destinationNumber = phoneNumber.getText().toString();
        String messageText = smsContents.getText().toString();

        //Creates a PendingIntent object to listen for when the SMS has arrived
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
        //Creates a SmsManager object to handle sending the message; it uses the default settings.
        SmsManager smsManager = SmsManager.getDefault();

        //Listens for SMS_SENT intents to check if the send occurred successfully.
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (getResultCode() == Activity.RESULT_OK) {
                    indicator.setText("Message Sent");
                } else {
                    indicator.setText("Message Failed");
                }
            }
        }, new IntentFilter("SMS_SENT"));

        smsManager.sendTextMessage(_destinationNumber, null, messageText, sentPendingIntent, null);
        indicator.setText("Message Sending");

    }

    /**
     * Function that bounces to another activity in the app; used to test transitioning between different activities in the
     * @param v the view that the button is in.
     */
    public void onBounceButton(View v){
        //Creates an explicit intent in preparation for launching the BounceActivity.
        Intent bounceIntent = new Intent(this, BounceActivity.class);
        startActivity(bounceIntent);
    }


}
