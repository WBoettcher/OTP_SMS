/**
 * MainActivity - Java file used to operate the MainActivity for the app.  
 * It is currently acting as a testing activity; it is the activity that launches when the program is run on a device.
 * The code is currently exploring the use of BroadcastReceivers to interface with SMS messages.
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
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView indicator;
    EditText phoneNumber;
    EditText smsContents;
    ListView receivedMessagesList;
    SmsDatabaseHelper smsDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creates a local reference to the different elements of the UI.
        indicator = (TextView) findViewById(R.id.indicator);
        phoneNumber = (EditText) findViewById(R.id.c_phoneNumber);
        smsContents = (EditText) findViewById(R.id.smsContents);
        receivedMessagesList = (ListView) findViewById(R.id.receivedMessagesList);

        //Creates a reference to the database
        smsDb = new SmsDatabaseHelper(this);

        //Sets up a SimpleCursorAdapter

        //Registers a new receiver "new BroadcastReceiver(){...}" for the SMS_RECEIVED intent
        //Pre-Kitkat Receiver
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Changes the UI upon receipt of a message.
                indicator.setText("Message Received");
                Bundle messageBundle = intent.getExtras();
                //Only saves the message to the database if the bundle is non-null
                if(messageBundle != null) {
                    saveMessage(messageBundle);
                }
            }
        }, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        //Registers a new receiver "new BroadcastReceiver(){...}" for the SMS_RECEIVED_ACTION
    }

    /**
     * Subroutine to parse an incoming SMS message.
     * @param messageBundle - the bundle containing the message data; passed from the BroadcastReceiver
     */
    private void saveMessage(Bundle messageBundle){

        final Object[] pduObjects = (Object[]) messageBundle.get("pdus");
        //Goes through each pdu in the bundle and creates an SmsMessage from them
        SmsMessage currentMessage;
        InternalSmsMessage messageWrapped;
        //TODO Replace following loop with for-each loop to simplify code.
        for(int i = 0; i < pduObjects.length; i++){
            currentMessage = SmsMessage.createFromPdu((byte[]) pduObjects[i]);
            messageWrapped = new InternalSmsMessage(currentMessage); //Wraps the SmsMessage in an InternalSmsMessage layer
            smsDb.addMessage(messageWrapped); //Saves the message in the database
        }
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
     * @param v - the View that the button is in.
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
        Intent bounceIntent = new Intent(this, ContactInterface.class);
        //Executes that explicit intent
        startActivity(bounceIntent);
    }


}
