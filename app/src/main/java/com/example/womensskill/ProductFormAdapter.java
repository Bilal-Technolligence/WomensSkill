package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ProductFormAdapter extends FragmentStatePagerAdapter {
    int countTab;
    public ProductFormAdapter(@NonNull FragmentManager fm, int countTab) {
        super(fm);
        this.countTab=countTab;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                BasicInformationFragment basicInformationFragment=new BasicInformationFragment();
                return basicInformationFragment;
            case 1:
                DetailedDescriptionFragment detailedDescriptionFragment=new DetailedDescriptionFragment();
                return detailedDescriptionFragment;
            case 2:
                ServiceDeliveryFragment serviceDeliveryFragment =new ServiceDeliveryFragment();
                return serviceDeliveryFragment;
            case 3:
                PriceFragment priceFragment=new PriceFragment();
                return priceFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return countTab;
    }
}
