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
import android.widget.Toast;

public class WelcomeSecond extends AppCompatActivity {

    EditText editText;
    Button button;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome_second);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password","");

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);


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
}

