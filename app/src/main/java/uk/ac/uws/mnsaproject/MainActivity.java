package uk.ac.uws.mnsaproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends FragmentController {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/

    @Override
    protected Fragment createFragmentView() {
        return new Fragment_startPage();
    }
}
