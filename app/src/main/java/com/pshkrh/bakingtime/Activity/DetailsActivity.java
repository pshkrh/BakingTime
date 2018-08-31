package com.pshkrh.bakingtime.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.R;

public class DetailsActivity extends AppCompatActivity {

    private DetailsFragment detailsFragment;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if(savedInstanceState!=null){
            Log.d(TAG,"SavedInstanceState found in onCreate of DetailsActivity");
            detailsFragment = (DetailsFragment)getSupportFragmentManager().findFragmentByTag(DetailsFragment.FRAGMENT_TAG);
            if(detailsFragment==null){
                detailsFragment = new DetailsFragment();
                Log.d(TAG,"New Details Fragment Created");
            }
            Bundle saveState = savedInstanceState.getBundle("SavedState");
            detailsFragment.setArguments(saveState);
            detailsFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_frame, detailsFragment,DetailsFragment.FRAGMENT_TAG)
                    .commit();
        }
        else{
            Bundle bundle = getIntent().getExtras();
            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            detailsFragment.setRetainInstance(true);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_frame, detailsFragment,DetailsFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        //getSupportFragmentManager().beginTransaction().remove(detailsFragment).commit();
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG,"onSaveInstanceState called in Activity");
        //Bundle logBundle = detailsFragment.getState();
        Bundle logBundle = new Bundle();
        logBundle.putParcelableArrayList("Steps",DetailsFragment.mSteps);
        logBundle.putLong("PlayerPosition",DetailsFragment.mPlayerPosition);
        logBundle.putInt("FragmentPosition",DetailsFragment.mFragmentPosition);
        logBundle.putBoolean("Moving",DetailsFragment.mMoving);
        Log.d(TAG,"LOG BUNDLE\n" + "Player Position = " + logBundle.getLong("PlayerPosition") +
                    "\nFragment Position = " + logBundle.getInt("FragmentPosition"));
        //outState.putBundle("SavedState",detailsFragment.getState());
        outState.putBundle("SavedState",logBundle);
        super.onSaveInstanceState(outState);
    }
}