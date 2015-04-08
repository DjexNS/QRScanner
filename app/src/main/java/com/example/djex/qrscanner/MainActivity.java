package com.example.djex.qrscanner;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{

    private Button scanBtn;
    private TextView contentTxt, welcomeMessage;
    private CalendarEntity calendarEntity;
    private String scanContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        welcomeMessage = (TextView)findViewById(R.id.welcome_message);

        welcomeMessage.setText("Welcome to Calendar Event Scanner!");
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
        if (id == R.id.exit_application) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.scan_button)
        {
            //scan
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null && scanningResult.getContents() != null)
        {
            View b = findViewById(R.id.scan_button);
            b.setVisibility(View.INVISIBLE);
            //continue if result exists
            scanContent = scanningResult.getContents();

            calendarEntity = CalendarParser.calendarParse(scanContent);
            String message = "";

            if (calendarEntity!=null)
            {
                message+="Do you wish to create an event with this content:\n\n" + calendarEntity.toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(message).setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
            }
            else
            {
                contentTxt.setText(scanContent);
                b.setVisibility(View.VISIBLE);
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case DialogInterface.BUTTON_POSITIVE:
                {
                    CalendarHelper calHelper = new CalendarHelper();
                    if(calendarEntity != null)
                    {
                        Intent event = calHelper.createEntryObject(calendarEntity.getTitle(), calendarEntity.getContent(), calendarEntity.getStartDate(), calendarEntity.getEndDate(), calendarEntity.getReccurenceString(), 30);
                        startActivity(event);
                        contentTxt.setText("You have successfully entered Google Calendar, in order to create an event with the title: " + calendarEntity.getTitle() + "\nStarting time: " + calendarEntity.getStartDateString());
                    }
                    else
                    {
                        contentTxt.setText(scanContent);
                        View b = findViewById(R.id.scan_button);
                        b.setVisibility(View.VISIBLE);
                    }
                    break;
                }
                case DialogInterface.BUTTON_NEGATIVE:
                {
                    contentTxt.setText("You have no new events.");
                    View b = findViewById(R.id.scan_button);
                    b.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
    };

}
