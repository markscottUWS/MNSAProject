package uk.ac.uws.mnsaproject;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class WaterActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView targetTextView;
    private TextView soFarTextView;
    private ImageView dropImageView;
    private EditText addEditText;
    private Button addButton;
    private MediaPlayer player;

    private double water;
    private double targetWater;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        targetTextView = findViewById(R.id.target_textview);
        soFarTextView = findViewById(R.id.so_far_textview);
        addEditText = findViewById(R.id.water_edittext);
        dropImageView = findViewById(R.id.drop_imageview);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        retrievePrefs();

        targetTextView.setText(getString(R.string.water_target, targetWater));
        soFarTextView.setText(getString(R.string.water_so_far, water, targetWater));

    }

    private void retrievePrefs()
    {
        SharedPreferences prefs = getSharedPreferences(SharedPrefsKeys.prefsId, MODE_PRIVATE);

        //get the user's ideal weight to work out their water balance
        String idealWeightString = prefs.getString(SharedPrefsKeys.idealWeightKey, "");
        if(!idealWeightString.equals(""))
        {
            double idealWeight = 0;
            try
            {
                idealWeight = Double.parseDouble(idealWeightString);
                targetWater = idealWeight * 0.03;
            }
            catch (NumberFormatException e)
            {
                targetWater = 2;
            }

        }

        //get the amount of water they've added today
        long waterDateInMillis = prefs.getLong(SharedPrefsKeys.waterDateKey, 0);
        if(waterDateInMillis > 0)
        {
            Calendar now = Calendar.getInstance();
            Calendar then = Calendar.getInstance();
            then.setTimeInMillis(waterDateInMillis);
            if(now.get(Calendar.YEAR) != then.get(Calendar.YEAR) &&
                    now.get(Calendar.DAY_OF_YEAR) != then.get(Calendar.DAY_OF_YEAR))
            {
                //it's a different day, so we start again
                water = 0;
            }
            else
            {
                //we retrieve the water from shared prefs
                water = prefs.getFloat(SharedPrefsKeys.waterAmountKey, 0);
            }
        }
        else
        {
            water = 0;
        }
        updateWater();


    }

    private void updateWater()
    {
        double percent = water / targetWater;
        if(percent >= 0 && percent < 0.25)
        {
            dropImageView.setImageResource(R.drawable.water_none);
        }
        if(percent >= 0.25 && percent < 0.75)
        {
            dropImageView.setImageResource(R.drawable.water_one_quarter);
        }
        if(percent >= 0.75 && percent < 1.0)
        {
            dropImageView.setImageResource(R.drawable.water_three_quarters);
        }
        if(percent >= 1.0)
        {
            dropImageView.setImageResource(R.drawable.water_full);
        }
        soFarTextView.setText(getString(R.string.water_so_far, water, targetWater));
    }

    private void saveWater()
    {
        SharedPreferences prefs = getSharedPreferences(SharedPrefsKeys.prefsId, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(SharedPrefsKeys.waterAmountKey, (float)water);
        Calendar now = Calendar.getInstance();
        long nowInMillis = now.getTimeInMillis();
        editor.putLong(SharedPrefsKeys.waterDateKey, nowInMillis);
        editor.commit();
    }

    @Override
    public void onClick(View view)
    {
        //check the user has input a valid value
        if(addEditText.getText().toString() != null && addEditText.getText().toString() != "")
        {
            int amount;
            try
            {
                amount = Integer.parseInt(addEditText.getText().toString());
                water = water + (amount * 0.001);
                updateWater();
                playSound();
                saveWater();
                addEditText.setText("");
            }
            catch(NumberFormatException nfe)
            {
                Toast.makeText(getApplicationContext(), R.string.no_water_value, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.no_water_value, Toast.LENGTH_SHORT).show();
        }


    }

    private void playSound()
    {
        player = MediaPlayer.create(getApplicationContext(), R.raw.drink);
        player.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(player != null)
        {
            player.release();
            player = null;
        }
    }
}
