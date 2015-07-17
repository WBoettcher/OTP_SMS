package otp.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BounceActivity extends Activity{

    private ListView listView;
    private final String[] testDataset = {"Correct", "Horse", "Battery", "Staple"};
    private ArrayAdapter<String> testAdapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bounce);
        listView = (ListView) findViewById(R.id.testList);
        testAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testDataset);
        listView.setAdapter(testAdapter);
    }

    public void onReturnButtonPush(View v){
        //Creates an explicit intent to return to the MainActivity.
        Intent bounceIntent = new Intent(this, MainActivity.class);
        //Executes the explicit intent
        startActivity(bounceIntent);
    }



}
