package uk.ac.uws.mnsaproject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Details_page {

    public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        private EditText nameEdittext,heightEdittext,weightEdittext,idealEdittext,ageEdittext;
        private RadioGroup genderRadioGroup, expectRadioGroup;
        private Button registerButton;
        private DatePickerDialog datePickerDialog;
        private Spinner spinner;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //Bind views with their ids
            bindViews();

            //Set listeners of views
            setViewActions();

            //Create DatePickerDialog to show a calendar to user to select birthdate
            prepareDatePickerDialog();

            Spinner spinner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lifestyle, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

        }

        private void bindViews() {
            nameEdittext=(EditText)findViewById(R.id.name_edittext);
            heightEdittext=(EditText)findViewById(R.id.height_edittext);
            weightEdittext=(EditText)findViewById(R.id.weight_edittext);
            idealEdittext=(EditText)findViewById(R.id.ideal_edittext);
            ageEdittext=(EditText)findViewById(R.id.age_edittext);
            genderRadioGroup=(RadioGroup)findViewById(R.id.gender_radiogroup);
            expectRadioGroup=(RadioGroup)findViewById(R.id.expect_radiogroup);
            registerButton=(Button)findViewById(R.id.register_button);
            spinner=(Spinner)findViewById(R.id.spinner);
        }

        private void setViewActions() {
            ageEdittext.setOnClickListener(this);
            registerButton.setOnClickListener(this);
        }

        private void prepareDatePickerDialog() {
            //Get current date
            Calendar calendar=Calendar.getInstance();

            //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    //When a date is selected, it comes here.
                    //Change ageEdittext's text and dismiss dialog.
                    ageEdittext.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                    datePickerDialog.dismiss();
                }
            },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        }

        private void showToastWithFormValues() {

            SharedPreferences prefs = getSharedPreferences(SharedPrefsKeys.prefsId, Context.MODE_PRIVATE);

            //Get edittexts values
            String name=nameEdittext.getText().toString();
            String height=heightEdittext.getText().toString();
            String weight=weightEdittext.getText().toString();
            String ideal=idealEdittext.getText().toString();
            String age=ageEdittext.getText().toString();

            //Get gender
            RadioButton selectedRadioButton=(RadioButton)findViewById(genderRadioGroup.getCheckedRadioButtonId());
            String gender=selectedRadioButton==null ? "":selectedRadioButton.getText().toString();

            //Get expectations
            RadioButton expectationsRadioButton=(RadioButton)findViewById(expectRadioGroup.getCheckedRadioButtonId());
            String expect=expectationsRadioButton==null ? "":selectedRadioButton.getText().toString();

            //Check all fields
            if(!name.equals("")&&!height.equals("")&&!weight.equals("")&&!ideal.equals("")&&!age.equals("")&&!gender.equals("")){


                //Everything allright
                Toast.makeText(this,getResources().getString(R.string.here_is_values,("\nname:"+name+"\nheight:"+height+"\nEmail:"+weight+"\nage:"+age+"\nGender:"+gender)),Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(SharedPrefsKeys.nameKey, name);

                editor.commit();

            }
            else{
                Toast.makeText(this,getResources().getString(R.string.no_field_can_be_empty),Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.age_edittext:
                    datePickerDialog.show();
                    break;
                case R.id.register_button:
                    showToastWithFormValues();
                    break;
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
