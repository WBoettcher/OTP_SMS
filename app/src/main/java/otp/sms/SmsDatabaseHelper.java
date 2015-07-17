package otp.sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * NOTE: NOT PLANNED FOR CURRENT IMPLEMENTATION.  DEMO SWITCHED TO ContactDatabaseHelper.
 * I realised that this database is not particularly useful because messages are already stored in the ContentProvider for SMS Messages.
 * To store plaintexts in the future we could just link an encrypted message (stored in the contentProvider) to its respective key bits and simply store those keybits.
 * Created by aharris on 7/15/15.
 */
public class SmsDatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = this.getClass().getSimpleName();
    //DB Version
    private static final int DATABASE_VERSION = 1;

    //DB Name
    private static final String DATABASE_NAME = "smsDatabase";

    //Table Names
    private static final String TABLE_SMS = "SMS";

    //Column names
    public static final String KEY_ID = "id";
    public static final String KEY_SENDER = "sender";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TIMESTAMP = "timestamp";

    //Table Create Statements
    //CREATE TABLE SMS(id INTEGER PRIMARY KEY, sender TEXT, message TEXT)
    private static final String CREATE_TABLE_SMS = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s LONG", TABLE_SMS, KEY_ID, KEY_SENDER, KEY_MESSAGE, KEY_TIMESTAMP);
            //"CREATE TABLE " + TABLE_SMS + "(" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_SENDER + " TEXT, " + KEY_MESSAGE + " TEXT" + ")";


    public SmsDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SMS); //Once the database has been created, it adds the SMS table
        Log.d(TAG, CREATE_TABLE_SMS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTE: Temporary; change as soon as a new database version is implemented and develop method for migrating data between them.
        //Drops old tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
        //Generates new tables
        onCreate(db);
    }

    /**
     * Adds a message to the database. C in CRUD
     * @param internalSMS the SMS message as represented by the internal SMS class
     */
    public void addMessage(InternalSmsMessage internalSMS){
        SQLiteDatabase db = this.getWritableDatabase(); //creates a reference to the writeable version of the database

        //Creates a new ContentValues object to be stored in the database
        ContentValues values = new ContentValues();
        //Associates the sender with the sender tag
        values.put(KEY_SENDER, internalSMS.getSender());
        //Associates the message with the message tag
        values.put(KEY_MESSAGE, internalSMS.getMessage());
        //Associates the timestamp with the timestamp tag
        values.put(KEY_TIMESTAMP, internalSMS.getTimestamp());

        db.insert(TABLE_SMS, null, values);
        db.close(); //closes the connection to the database
    }

    /**
     * Gives a cursor to allow access to the data in the database
     * @return Cursor containing the messages in the database
     */
    public Cursor getMessages(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SMS, new String[] {KEY_SENDER, KEY_MESSAGE, KEY_TIMESTAMP}, null, null, null, null, KEY_TIMESTAMP + "DESC", null);
    }


}