package uk.ac.uws.mnsaproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class WelcomeSecond extends AppCompatActivity implements View.OnClickListener{

    EditText editText;
    Button button;
    TextView textView;
    private TextView welcomeBackTextView;
    private TextView weekTextView;
    private ImageButton waterButton;
    private ImageButton planButton;
    private ImageButton detailsButtons;
    private ConstraintLayout navLayout;

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

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        welcomeBackTextView = findViewById(R.id.welcome_back_textview);
        weekTextView = findViewById(R.id.week_textview);
        waterButton = findViewById(R.id.water_button);
        detailsButtons = findViewById(R.id.details_button);
        planButton = findViewById(R.id.plan_button);
        navLayout = findViewById(R.id.nav_layout);

        navLayout.setVisibility(View.INVISIBLE);

        button.setOnClickListener(this);
        waterButton.setOnClickListener(this);
        planButton.setOnClickListener(this);
        detailsButtons.setOnClickListener(this);

        retrievePrefs();
        if(validValues)
        {
            welcomeBackTextView.setText(getString(R.string.welcome_name, name));
            weekTextView.setText(getString(R.string.in_week, weeksJoined));
        }

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


    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.button:
            {
                String text = editText.getText().toString();

                if (text.equals(password))
                {
                    navLayout.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(WelcomeSecond.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.water_button:
            {
                Intent waterIntent = new Intent(getApplicationContext(), WaterActivity.class);
                startActivity(waterIntent);
                break;
            }
            case R.id.details_button:
            {
                Intent detailsIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(detailsIntent);
                break;
            }
            case R.id.plan_button:
            {
                Intent planIntent = new Intent(getApplicationContext(), PlanActivity.class);
                startActivity(planIntent);
                break;
            }
            default:
            {
                break;
            }
        }
    }
}

