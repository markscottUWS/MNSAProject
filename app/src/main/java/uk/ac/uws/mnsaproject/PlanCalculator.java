package uk.ac.uws.mnsaproject;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class PlanCalculator
{
    private int mAge;
    private boolean mGender;
    private float mWeight;
    private float mHeight;
    private boolean mExpect;
    private double mActivity;

    public static final String CARBS = "carbs";
    public static final String FATS = "fats";
    public static final String PROTEIN = "protein";

    public static final String SEDENTARY = "sedentary";
    public static final String MODERATELY_ACTIVE = "moderately active";
    public static final String VERY_ACTIVE = "very active";
    public static final String EXTREMELY_ACTIVE = "extremely active";

    /**
     * Standard constructor for the PlanCalculator class.  Once an
     * instance of the class has been initialised, provides access to
     * weight management and fitness metrics specific to the parameters
     * provided.
     *
     * @param age The person's age, in years.
     * @param gender The person's gender (female = true, male = false)
     * @param weight The person's weight, in kg
     * @param height The person's height, in cm
     * @param expect The person's weight management expectations (loss = true, maintain = false)
     * @param activity A string representing the person's activity level
     */
    public PlanCalculator(int age, boolean gender, float weight, float height, boolean expect, String activity)
    {
        mAge = age;
        mGender = gender;
        mWeight = weight;
        mHeight = height;
        mExpect = expect;
        switch(activity)
        {
            case SEDENTARY:
            {
                mActivity = 1.2;
                break;
            }
            case MODERATELY_ACTIVE:
            {
                mActivity = 1.55;
                break;
            }
            case VERY_ACTIVE:
            {
                mActivity = 1.725;
                break;
            }
            case EXTREMELY_ACTIVE:
            {
                mActivity = 1.9;
                break;
            }
            default:
            {
                //presume sedentary
                mActivity = 1.2;
                break;
            }
        }
    }

    /**
     * Method calculates a BMI (body mass index) from
     * the parameters held in the PlanCalculator.
     * Assumes weight is in kg and height is in cm.
     * @return The person's Body Mass Index (BMI)
     */
    public int getBMI()
    {
        int result = 0;
        if(mHeight != 0)
        {
            result = (int)(mWeight / Math.pow((mHeight / 100), 2));
        }
        return result;
    }

    /**
     * Method calculates macronutrient ratios according
     * to the parameters held in the PlanCalculator,
     * and depending on whether the person wishes to lose or
     * to maintain weight.
     * @return A HashMap containing percentages of calories for each macronutrient.
     */
    public HashMap<String, Integer> getMacroNutrients()
    {
        HashMap<String, Integer> results = new HashMap<>();
        if(mExpect)
        {
            results.put(CARBS, 20);
            results.put(FATS, 35);
            results.put(PROTEIN, 45);
        }
        else
        {
            results.put(CARBS, 40);
            results.put(FATS, 30);
            results.put(PROTEIN, 30);
        }
        return results;
    }

    public int getTargetCalories()
    {
        int calories;
        calories = (int)((10 * mWeight) + (6.25 * mHeight) - (5 * mAge));
        if(mGender)
        {
            calories = calories - 161;
        }
        calories = (int)(calories * mActivity);
        if(mExpect)
        {
            calories = calories - 500;
        }
        return calories;
    }

}
