package com.example.laura.cyclingtracker.helper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.laura.cyclingtracker.activity.HomeFragment;
import com.example.laura.cyclingtracker.activity.NewWorkoutActivity;
import com.example.laura.cyclingtracker.activity.ProfileFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                ProfileFragment tab2 = new ProfileFragment();
                return tab2;
            case 2:
                NewWorkoutActivity tab3 = new NewWorkoutActivity();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}