package com.pshkrh.bakingtime.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.R;

public class DetailsActivity extends AppCompatActivity {

    private DetailsFragment detailsFragment = new DetailsFragment();
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
            Bundle saveState = savedInstanceState.getBundle("SavedState");
            detailsFragment.setArguments(saveState);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_frame, detailsFragment)
                    .commit();
        }
        else{
            Bundle bundle = getIntent().getExtras();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_frame, detailsFragment)
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
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState called in Activity");
        outState.putBundle("SavedState",detailsFragment.getState());
    }
}