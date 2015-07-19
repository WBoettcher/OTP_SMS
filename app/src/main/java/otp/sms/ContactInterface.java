package otp.sms;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * CURRENTLY IN DEVELOPMENT
 * Class designed to test the use of databases in the app.
 * Created by aharris on 7/16/15
 */

public class ContactInterface extends Activity {

    private static final String TAG = "ContactInterface";

    private ContactDatabaseHelper contact_db;
    private EditText name;
    private EditText phoneNumber;
    private EditText code;
    private ListView contactsList;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_interface);

        contact_db = new ContactDatabaseHelper(getApplicationContext());
        Log.d(TAG, "Contact Database Helper constructed");

        name = (EditText) findViewById(R.id.c_name);
        phoneNumber = (EditText) findViewById(R.id.c_phoneNumber);
        code = (EditText) findViewById(R.id.c_code);

        contactsList = (ListView) findViewById(R.id.contactsListView);
        Cursor contactsCursor;
        contactsCursor = contact_db.getContactsCursor();
        String[] from = {ContactDatabaseHelper.KEY_NAME, ContactDatabaseHelper.KEY_PHONENUMBER, ContactDatabaseHelper.KEY_CODE};
        int[] to = {R.id.contactRowName, R.id.contactRowPhoneNumber, R.id.contactRowCode};
        adapter = new SimpleCursorAdapter(this, R.layout.contact_row_layout, contactsCursor, from, to, 0);
        contactsList.setAdapter(adapter);

    }

    /**
     * Function to take the values from the fields and submit it to the database.
     *
     * @param v the submit button
     */
    public void onSubmitButton(View v){
        //Inserts a new contact into the database
        contact_db.insert(new Contact(name.getText().toString(), phoneNumber.getText().toString(), code.getText().toString()));
        //TODO Check for a pre-existing contact and update the information in the row instead of adding a new row
    }

    /**
     * Function to update the listView upon the user's request.
     *
     * @param v the refresh button
     */
    public void onRefreshButton(View v) {
        Cursor newCursor = contact_db.getContactsCursor();
        adapter.swapCursor(newCursor);

    }

    /**
     * Function to reset the table.
     *
     * @param v the deleteDB button
     */
    public void onDeleteDBButton(View v) {
        contact_db.deleteTable();
    }

}
