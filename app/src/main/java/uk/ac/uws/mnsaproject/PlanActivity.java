package uk.ac.uws.mnsaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class PlanActivity extends AppCompatActivity
{
    //implementation variables
    private String name;
    private float height;
    private float weight;
    private boolean gender;
    private boolean expectation;
    private int age;
    private String activity;
    private boolean invalid;
    private PlanCalculator planCalculator;
    int[] exerciseArray;
    int[] lightExerciseArray = {R.drawable.walk, R.drawable.yoga, R.drawable.swim};
    int[] moderateExerciseArray = {R.drawable.cycle, R.drawable.hillwalk, R.drawable.swim};
    int[] intenseExerciseArray = {R.drawable.climbing, R.drawable.jump_rope, R.drawable.run};


    //Activity UI variables
    private TextView targetCaloriesTextView;
    private TextView percentCarbsTextView;
    private TextView percentFatsTextView;
    private TextView percentProteinTextView;
    private PieDrawable caloriePieDrawable;
    private ScaleDrawable bmiScaleDrawable;
    private ImageView pieImageView;
    private ImageView scaleImageView;
    private ImageView exercise1ImageView;
    private ImageView exercise2ImageView;
    private ImageView exercise3ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        targetCaloriesTextView = findViewById(R.id.target_calories_textview);
        percentCarbsTextView = findViewById(R.id.carbs_percent);
        percentFatsTextView = findViewById(R.id.fats_percent);
        percentProteinTextView = findViewById(R.id.protein_percent);
        pieImageView = findViewById(R.id.macro_imageview);
        scaleImageView = findViewById(R.id.bmi_scale_imageview);
        exercise1ImageView = findViewById(R.id.activity_image_1);
        exercise2ImageView = findViewById(R.id.activity_image_2);
        exercise3ImageView = findViewById(R.id.activity_image_3);
        exerciseArray = new int[3];
        retrieveSharedPrefs();

        if(!invalid)
        {
            planCalculator = new PlanCalculator(age, gender, weight, height, expectation, activity);

            int bmi = planCalculator.getBMI();
            bmiScaleDrawable = new ScaleDrawable(bmi, getApplicationContext());
            scaleImageView.setImageDrawable(bmiScaleDrawable);

            HashMap<String, Integer> macros = planCalculator.getMacroNutrients();
            int carbs = macros.get(PlanCalculator.CARBS);
            int fats = macros.get(PlanCalculator.FATS);
            int protein = macros.get(PlanCalculator.PROTEIN);
            caloriePieDrawable = new PieDrawable(carbs, fats, protein, getApplicationContext());
            pieImageView.setImageDrawable(caloriePieDrawable);

            percentCarbsTextView.setText(getString(R.string.percent_carbs, carbs));
            percentFatsTextView.setText(getString(R.string.percent_fats, fats));
            percentProteinTextView.setText(getString(R.string.percent_protein, protein));

            int calories = planCalculator.getTargetCalories();
            targetCaloriesTextView.setText(getString(R.string.number_target_calories, calories));

            switch(activity)
            {
                case PlanCalculator.SEDENTARY:
                {
                    exerciseArray = lightExerciseArray;
                    break;
                }
                case PlanCalculator.MODERATELY_ACTIVE:
                {
                    exerciseArray = moderateExerciseArray;
                    break;
                }
                case PlanCalculator.VERY_ACTIVE:
                {
                    exerciseArray = intenseExerciseArray;
                    break;
                }
                case PlanCalculator.EXTREMELY_ACTIVE:
                {
                    exerciseArray = intenseExerciseArray;
                    break;
                }
                default:
                {
                    exerciseArray = lightExerciseArray;
                    break;
                }
            }
            exercise1ImageView.setImageResource(exerciseArray[0]);
            exercise2ImageView.setImageResource(exerciseArray[1]);
            exercise3ImageView.setImageResource(exerciseArray[2]);
        }
    }

    private void retrieveSharedPrefs()
    {
        SharedPreferences prefs = getSharedPreferences(SharedPrefsKeys.prefsId, Context.MODE_PRIVATE);
        invalid = false;
        name = prefs.getString(SharedPrefsKeys.nameKey, "");
        if(name.equals(""))
        {
            invalid = true;
        }

        String heightString = prefs.getString(SharedPrefsKeys.heightKey, "");
        if(heightString.equals(""))
        {
            invalid = true;
        }
        else
        {
            try
            {
                height = Float.parseFloat(heightString);
            }
            catch (NumberFormatException e)
            {
                invalid = true;
            }
        }

        String weightString = prefs.getString(SharedPrefsKeys.weightKey, "");
        if(weightString.equals(""))
        {
            invalid = true;
        }
        else
        {
            try
            {
                weight = Float.parseFloat(weightString);
            }
            catch (NumberFormatException e)
            {
                invalid = true;
            }
        }

        String genderString = prefs.getString(SharedPrefsKeys.genderKey, "");
        if(genderString.equals(""))
        {
            invalid = true;
        }
        else
        {
            if(genderString.equals(getString(R.string.female)))
            {
                gender = true;
            }
            else
            {
                gender = false;
            }
        }

        String expectString = prefs.getString(SharedPrefsKeys.expectationKey, "");
        if(expectString.equals(""))
        {
            invalid = true;
        }
        else
        {
            if(expectString.equals(R.string.lose))
            {
                expectation = true;
            }
            else
            {
                expectation = false;
            }
        }

        activity = prefs.getString(SharedPrefsKeys.activityKey, "");
        if(activity.equals(""))
        {
            invalid = true;
        }

        int day = 0;
        int month = 0;
        int year = 0;
        boolean validValues = true;

        long birthDateInMillis = prefs.getLong(SharedPrefsKeys.birthDateMillisKey, 0);
        if (birthDateInMillis > 0) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(birthDateInMillis);
            day = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        }

        if(validValues)
        {
            Calendar now = Calendar.getInstance();
            Calendar birth = Calendar.getInstance();
            birth.set(year - 1900, month, day);

            int nowYear = now.get(Calendar.YEAR);
            int birthYear = birth.get(Calendar.YEAR);

            age = nowYear - birthYear;

            int nowMonth = now.get(Calendar.MONTH);
            int birthMonth = birth.get(Calendar.MONTH);
            if(birthMonth > nowMonth)
            {
                age--;
            }
            else if(birthMonth == nowMonth)
            {
                int nowDay = now.get(Calendar.DAY_OF_MONTH);
                int birthDay = birth.get(Calendar.DAY_OF_MONTH);
                if (birthDay > nowDay)
                {
                    age--;
                }
            }
        }

    }

}
