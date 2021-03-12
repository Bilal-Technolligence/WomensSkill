package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SearchAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public SearchAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CategoryFragment categoryFragment=new CategoryFragment();
                return categoryFragment;
            case 1:
                InterestFragment interestFragment=new InterestFragment();
                return interestFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
