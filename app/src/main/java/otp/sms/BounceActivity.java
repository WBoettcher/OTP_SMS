package otp.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class BounceActivity extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bounce);
    }

    public void onReturnButtonPush(View v){
        //Creates an explicit intent to return to the MainActivity.
        Intent bounceIntent = new Intent(this, MainActivity.class);
        //Executes the explicit intent
        startActivity(bounceIntent);
    }

}
