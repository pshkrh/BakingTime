package com.pshkrh.bakingtime.Activity;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    private static final String FRAGMENT_POSITION = "FragmentPosition";
    private static final String STEPS = "Steps";
    private static final String CURRENT_STEP = "currentStep";

    public ArrayList<Step> mSteps = new ArrayList<>();

    public int currentStep = 0;
    private DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mSteps = getIntent().getParcelableArrayListExtra(STEPS);
        currentStep = getIntent().getIntExtra(FRAGMENT_POSITION, 0);
        if (savedInstanceState != null) {
            currentStep = savedInstanceState.getInt(CURRENT_STEP);
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            inflateFragment();

        ImageButton prev = findViewById(R.id.button_prev);
        ImageButton next = findViewById(R.id.button_next);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep == 0) {
                    return;
                }
                currentStep--;
                inflateFragment();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStep == mSteps.size() - 1) {
                    return;
                }
                currentStep++;
                inflateFragment();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void inflateFragment() {

        getSupportFragmentManager().popBackStackImmediate();

        detailsFragment = DetailsFragment.newInstance(mSteps.get(currentStep).getVideoUrl()
                , mSteps.get(currentStep).getThumbnailUrl()
                , mSteps.get(currentStep).getDesc()
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details_frame, detailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_STEP, currentStep);
    }
}