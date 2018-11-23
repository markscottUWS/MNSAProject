package uk.ac.uws.mnsaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class WelcomeSecond extends AppCompatActivity {

    EditText editText;
    Button button;
    private TextView welcomeBackTextView;
    private TextView weekTextView;

    String password;

    private String name;
    private int weeksJoined;
    private boolean validValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome_second);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password","");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        welcomeBackTextView = findViewById(R.id.welcome_back_textview);
        weekTextView = findViewById(R.id.week_textview);

        retrievePrefs();
        if(validValues)
        {
            welcomeBackTextView.setText(getString(R.string.welcome_name, name));
            weekTextView.setText(getString(R.string.in_week, weeksJoined));
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                if (text.equals(password)) {

                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(WelcomeSecond.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void retrievePrefs()
    {
        validValues = true;
        SharedPreferences prefs = getSharedPreferences(SharedPrefsKeys.prefsId, MODE_PRIVATE);
        name = prefs.getString(SharedPrefsKeys.nameKey, "");
        if(name.equals(""))
        {
            validValues = false;
        }
        long joinTimeInMillis = prefs.getLong(SharedPrefsKeys.joinDateMillisKey, 0);
        if(joinTimeInMillis > 0)
        {
            calculateWeeks(joinTimeInMillis);
        }
        else
        {
            validValues = false;
        }
    }

    private void calculateWeeks(long joinTimeInMillis)
    {
        Calendar now = Calendar.getInstance();
        long nowMillis = now.getTimeInMillis();
        long difference = nowMillis - joinTimeInMillis;
        long daysJoined = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        weeksJoined = (int)((daysJoined / 7) + 1);
    }
}

