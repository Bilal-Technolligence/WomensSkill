package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SkillPagerAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public SkillPagerAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                OrderCompletedFragment orderCompletedFragment=new OrderCompletedFragment();
                return orderCompletedFragment;
            case 1:
                OrderCancelledFragment orderCancelledFragment=new OrderCancelledFragment();
                return orderCancelledFragment;
            case 2:
                ActiveOrderFragment activeOrderFragment=new ActiveOrderFragment();
                return activeOrderFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
