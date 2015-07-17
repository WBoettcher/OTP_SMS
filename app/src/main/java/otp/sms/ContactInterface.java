package otp.sms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

/**
 * CURRENTLY IN DEVELOPMENT
 * Class designed to test the use of databases in the app.
 * Created by aharris on 7/16/15
 */

public class ContactInterface extends Activity {

    private ContactDatabaseHelper contact_db;
    private EditText name;
    private EditText phoneNumber;
    private EditText code;
    private ListView contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_interface);
        contact_db = new ContactDatabaseHelper(this);
        name = (EditText) findViewById(R.id.c_name);
        phoneNumber = (EditText) findViewById(R.id.c_phoneNumber);
        code = (EditText) findViewById(R.id.c_code);
    }

    public void onSubmitButton(View v){
        //Inserts a new contact into the database
        contact_db.insert(new Contact(name.getText().toString(), phoneNumber.getText().toString(), code.getText().toString()));
        //TODO Check for a pre-existing contact and update the information in the row instead of adding a new row
    }

    //TODO Create an adapter to link data from the database to the listView (contactsList).


}
