package com.pshkrh.bakingtime.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pshkrh.bakingtime.Adapter.CustomPagerAdapter;
import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.Model.Step;
import com.pshkrh.bakingtime.R;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CustomPagerAdapter mCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Bundle bundle = getIntent().getExtras();
        int scrollPosition = bundle.getInt("Position");

        mViewPager = findViewById(R.id.pager);
        mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(),bundle);

        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(scrollPosition,true);
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
}
