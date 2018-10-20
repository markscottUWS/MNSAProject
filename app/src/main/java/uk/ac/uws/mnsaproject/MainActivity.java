package uk.ac.uws.mnsaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.security.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Widget references
    //The button the user will click on to get their location
    private Button locationButton;
    //The textview to show the longitude
    private TextView longitudeTextView;
    //The textview to show the latitude
    private TextView latitudeTextView;

    //This is the private variable to reference the location provider
    private FusedLocationProviderClient mFusedLocationClient;
    //This is the last known (usually current) location of the device
    private Location mCurrentLocation;

    //Represents the request code for requesting the fine grained location permission
    private static final int MY_PERMISSIONS_FINE_LOCATION = 58;
    //The tag for displaying debug messages
    private static final String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this call retrieves the location provider
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //TODO: Assign longitudeTextView from xml id
        //TODO: Assign latitudeTextView from xml id
        //TODO: Assign locationButton from xml id
        locationButton.setOnClickListener(this);
    }

    /**
     * This method is called when the find location button is pressed.
     * It checks whether we have permission to access location.  If so,
     * it begins the process of retrieving the location.  If not, it asks
     * the user for the relevant permissions.
     */
    private void findLocation()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //If we don't have the correct permissions, then ask for them.
            requestLocationPermission();
        }
        else
        {
            getCurrentLocation();
        }
    }

    /**
     * Called as a result of asking for a permission from the user.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSIONS_FINE_LOCATION:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getCurrentLocation();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.permission_denied_location, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
            {
                break;
            }
        }
    }

    /**
     * Method asks the user for permission to use the fine grained location service
     */
    private void requestLocationPermission()
    {
        //The result of this call is returned to onRequestPermissionsResult
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_FINE_LOCATION);
    }

    /**
     * This method gets the last known (usually current) location
     * of the device from the location provider.
     * If the location is not known, displays a toast to inform the user.
     * This method should only be called when we're sure we have the correct permissions;
     * a security exception will create an error.
     */
    private void getCurrentLocation()
    {
        try
        {
            mFusedLocationClient.getLastLocation().
                    addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null)
                            {
                                setLocation(location);
                            }
                            else
                            {
                                setLocation(null);
                                Toast.makeText(getApplicationContext(), R.string.error_location, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        catch(SecurityException se)
        {
            Log.e(TAG, "There was a security exception while attempting to access location: " + se.getMessage());
            requestLocationPermission();
        }

    }

    /**
     * Method assigns a location to the private location variable, and updates the relevant UI.
     * @param location A Location object representing the last known (usually current) location.  May be null.
     */
    private void setLocation(Location location)
    {
        if (location != null)
        {
            mCurrentLocation = location;
            //TODO: Set longitude and latitude text views to display location
        }
        else
        {
            mCurrentLocation = null;
            //TODO: Set longitude and latitude text views to empty strings
        }
    }

    @Override
    public void onClick(View view) {
        findLocation();
    }
}
