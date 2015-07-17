package otp.sms;

import android.support.annotation.Nullable;

/**
 * Class to store information regarding contacts internally.
 * Created by aharris on 7/16/15.
 */
public class Contact {
    private static final String TAG = "Contact";

    private String name;
    private String phoneNumber;
    private String code;

    public Contact(String name, String phoneNumber, String code){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.code = code;
    }

    public String getName(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public String getCode(){
        return code;
    }

    /**
     * Combined setter method
     * @param name the new name; null if no change
     * @param phoneNumber the new phone number; null if no change
     * @param code the new code; null if no change
     */
    public void updateContact(@Nullable String name, @Nullable String phoneNumber, @Nullable String code){
        if (name!= null){
            this.name = name;
        }
        if (phoneNumber != null){
            this.phoneNumber = phoneNumber;
        }
        if (code != null){
            this.code = code;
        }
    }

    /**
     * Clears information specified by the arguments (true to clear)
     * @param name boolean representing whether the name field is cleared
     * @param phoneNumber boolean representing whether the phoneNumber field is cleared
     * @param code boolean representing whether the code field is cleared
     */
    public void deleteInformation(boolean name, boolean phoneNumber, boolean code){
        if(name){
            this.name = null;
        }
        if(phoneNumber){
            this.phoneNumber = null;
        }
        if (code){
            this.code = null;
        }
    }
}
