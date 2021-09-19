package com.cagriyalcin.moviedbtest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.cagriyalcin.moviedbtest.Fragments.HomeFragment;
import com.cagriyalcin.moviedbtest.R;

public class MainActivity extends AppCompatActivity {

    public static String appLanguage = "";
    public static String apiKey = "fd16bd660686cda911b16e49f59485b9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appLanguage = getResources().getConfiguration().locale.getLanguage();

        callHomeScreen();
    }

    private void callHomeScreen() {
        Fragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
    }
}