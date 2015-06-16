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
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends Activity {
    CheckBox sentBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sentBox = (CheckBox) findViewById(R.id.sentBox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
     * Dummy function that checks the "sent" box after the send button is pushed.
     * Could later be adapted to actually check whether the message has been sent and potentially display an error message if it isn't.
     * @param v - the View that the button is in (needs review)
     */
    public void onSendButton(View v){
        sentBox.setChecked(true);
    }
}
