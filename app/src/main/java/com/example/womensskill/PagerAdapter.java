package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public PagerAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AboutFragment aboutFragment=new AboutFragment();
                return aboutFragment;
            case 1:
                SkillFragment skillFragment=new SkillFragment();
                return skillFragment;
            case 2:
                ProductFragment productFragment=new ProductFragment();
                return productFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
