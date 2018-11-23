package uk.ac.uws.mnsaproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashActivity extends AppCompatActivity {
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        password = settings.getString("password","");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (password.equals("")) {
                    Intent intent = new Intent(getApplicationContext(), WelcomeFirst.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), WelcomeSecond.class);
                    startActivity(intent);
                    finish();


                }
            }
        }, 2000);
    }
}
