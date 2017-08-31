package com.example.ss89068.registration;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static android.R.layout.simple_dropdown_item_1line;
import static android.R.layout.simple_list_item_1;


public class MainActivity extends Activity {
    Button button;
    Button buttonGet;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DatabaseHandler(this);
        button=(Button)findViewById(R.id.add);

        AutoCompleteTextView actv;
        actv = (AutoCompleteTextView) findViewById(R.id.racfId);
        String[] albums = getResources().getStringArray(R.array.Racf_ID_array);

        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, albums);
        //Getting the instance of AutoCompleteTextView
        actv.setThreshold(3);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                AutoCompleteTextView actv;
                actv = (AutoCompleteTextView) findViewById(R.id.racfId);
                String racfId=actv.getText().toString();

                if(racfId.isEmpty()) {
                    Toast.makeText(
                            getApplicationContext(),"Please enter valid RACF Id",
                            Toast.LENGTH_SHORT).show();
                            return;
                }
                if(!IsUserAdded(racfId,db)) {
                    db.add(racfId);
                     Toast.makeText(
                            getApplicationContext(),"RACF Id registered successfully",
                            Toast.LENGTH_SHORT).show();
  }
                else
                {
                    Toast.makeText(
                            getApplicationContext(),"RACF Id is already registered",
                            Toast.LENGTH_SHORT).show();
                }
                actv.setText("");
                // get all data

                List<String> ls= db.get();
                System.out.println(ls);
            }
        });


     /*   buttonGet=(Button)findViewById(R.id.get);
        buttonGet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                TextView textView=(TextView)findViewById(R.id.allRacfIds);
                List<String> racfIds=db.Get();
                String log="";
                for(String racfid:racfIds)
                {
                   log+=racfid;
                }
                textView.setText(log);
            }
        });*/
    }
    private Boolean IsUserAdded(String racfId,DatabaseHandler db)
    {
        List<String> allUsers=db.get();
        for(String user:allUsers) {
        if(user.equalsIgnoreCase(racfId))
        {
            return  true;
        }
        }
        return false;
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
            sendMail();
        }

        return super.onOptionsItemSelected(item);
    }

    void sendMail() {
        List<String> racfIds = db.get();
        StringBuilder allRacfIds = new StringBuilder("Registered RACF Ids:");
        for (String x : racfIds) {
            allRacfIds.append(System.getProperty("line.separator"));
            allRacfIds.append(x);
        }
        allRacfIds.append(System.getProperty("line.separator"));

        allRacfIds.append("  Total Number:");
        allRacfIds.append(racfIds.size());


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
}
