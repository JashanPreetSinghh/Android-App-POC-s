package com.example.ss89068.feedback;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends Activity implements OnItemSelectedListener {

String spinnerValue ;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
     db=new DatabaseHandler(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("ISG");
        categories.add("CAP");
        categories.add("SAP");
        categories.add("JDF");
        categories.add("CPS");
        categories.add("DAITI");
        categories.add("PDP");
        categories.add("SPE");
        categories.add("Parts");
        categories.add("Manufacturing & Supply Chain");
        categories.add("Information Management");
        categories.add("Architecture");
        categories.add("GCS Database Service");
        categories.add("BITS");
        categories.add("HR");
        categories.add("DWIS");
        categories.add("IKC");
        categories.add("Process");
        categories.add("Enabling");
        categories.add("Business Analytics");
        categories.add("Order Management");
        categories.add("Process");
        categories.add("MDM");
        categories.add("Finance");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }


    void display()
    {
        Context context = getApplicationContext();
        CharSequence text = "Thank you";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,70);

        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(40);
// Changes the height and width to the specified *pixels*
        // get all data
        List<String> ls= db.get();
        System.out.println(ls);
        toast.show();
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
        if (id == R.id.download) {
            //return true;
            sendMail();
        }

        return super.onOptionsItemSelected(item);
    }
void sendMail()
{
    List<String> feedback = db.get();
    StringBuilder allRacfIds = new StringBuilder("Feedbacks are:");

    for (String x : feedback) {
        allRacfIds.append(System.getProperty("line.separator"));
        allRacfIds.append(x);
    }
    allRacfIds.append(System.getProperty("line.separator"));

    allRacfIds.append("  Total Number of Feedbacks:");
    allRacfIds.append(feedback.size());


    String[] TO = {"sapresnehal@johndeere.com"};
    String[] CC = {""};
    Intent emailIntent = new Intent(Intent.ACTION_SEND);
    emailIntent.setData(Uri.parse("mailto:"));
    emailIntent.setType("text/plain");
    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
    emailIntent.putExtra(Intent.EXTRA_CC, CC);
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
    emailIntent.putExtra(Intent.EXTRA_TEXT, allRacfIds.toString());

    try {
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        finish();

    } catch (android.content.ActivityNotFoundException ex) {

    }
}

    public void feedbackFunction(View view) {
        final RatingBar mBar = (RatingBar) findViewById(R.id.ratingBar);
        EditText feedText=(EditText) findViewById(R.id.racfId);

       /* Toast.makeText(
                getApplicationContext(), String.valueOf(mBar.getRating()) ,
                Toast.LENGTH_SHORT).show();*/
        db.add((int)mBar.getRating(), feedText.getText(),spinnerValue);
        display();
        mBar.setRating((float)1.5);
        EditText et=(EditText) findViewById(R.id.racfId);
        et.setText("");

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        spinnerValue = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
