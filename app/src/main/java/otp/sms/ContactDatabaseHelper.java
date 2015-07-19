package otp.sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**CURRENTLY IN DEVELOPMENT
 * Created by aharris on 7/16/15.
 */
public class ContactDatabaseHelper extends SQLiteOpenHelper {
//TODO: Make this class a singleton to avoid slowing down the UI thread.

    //Table Names
    public static final String TABLE_CONTACTS = "contacts";
    //Column names
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONENUMBER = "phonenumber";
    public static final String KEY_CODE = "code";
    //TAG for Logcat.
    private final static String TAG = "ContactDatabaseHelper";
    //DB Version
    private static final int DATABASE_VERSION = 2; //incremented during testing
    //DB Name
    private static final String DATABASE_NAME = "contactsDatabase";
    private static final String CREATE_TABLE_CONTACTS = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)", TABLE_CONTACTS, KEY_ID, KEY_NAME, KEY_PHONENUMBER, KEY_CODE);

    public ContactDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
        Log.d(TAG, "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTE: Temporary; change as soon as a new database version is implemented and develop method for migrating data between them.
        //Drops old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        //Generates new tables
        onCreate(db);
    }

    public void insert(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase(); //creates a reference to the writeable version of the database

        //Creates a new ContentValues object to be stored in the database
        ContentValues values = new ContentValues();
        //Associates the sender with the sender tag
        values.put(KEY_NAME, contact.getName());
        //Associates the message with the message tag
        values.put(KEY_PHONENUMBER, contact.getPhoneNumber());
        //Associates the timestamp with the timestamp tag
        values.put(KEY_CODE, contact.getCode());

        db.insert(TABLE_CONTACTS, null, values);
        db.close(); //closes the connection to the database
    }

    public Cursor getContactsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID, KEY_NAME, KEY_PHONENUMBER, KEY_CODE};
        return db.query(TABLE_CONTACTS, columns, null, null, null, null, null, null);
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }
}
