package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterBuyerHome extends FragmentStatePagerAdapter {
    int countTab;
    public PagerAdapterBuyerHome(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ProductFragmentBuyer productFragment=new ProductFragmentBuyer();
                return productFragment;
            case 1:
                SkillFragmentBuyer serviceFragment=new SkillFragmentBuyer();
                return serviceFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
