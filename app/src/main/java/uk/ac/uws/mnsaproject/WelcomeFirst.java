package uk.ac.uws.mnsaproject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeFirst extends AppCompatActivity {

    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome_first);

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.get_started_button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = editText1.getText().toString();
                String text2 = editText1.getText().toString();

                if (text1.equals("") || text2.equals("")) {
                    Toast.makeText(WelcomeFirst.this, "No password entered!", Toast.LENGTH_SHORT).show();
                } else {

                    if (text1.equals(text2)) {
                        SharedPreferences settings = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("password", text1);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(WelcomeFirst.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
