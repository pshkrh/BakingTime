package com.pshkrh.bakingtime.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pshkrh.bakingtime.Fragment.DetailsFragment;
import com.pshkrh.bakingtime.Model.Step;

import java.util.ArrayList;


public class CustomPagerAdapter extends FragmentPagerAdapter {

    private Bundle stepsBundle;

    public CustomPagerAdapter(FragmentManager fm, Bundle bundle) {
        super(fm);
        stepsBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsFragment.newInstance(stepsBundle.<Step>getParcelableArrayList("Steps"),position);
    }

    @Override
    public int getCount() {
        ArrayList<Step> mSteps = stepsBundle.getParcelableArrayList("Steps");
        if(mSteps!=null)
            return mSteps.size();
        else
            return 0;
    }
}
