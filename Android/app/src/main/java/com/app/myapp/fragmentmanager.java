package com.app.myapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class fragmentmanager extends FragmentPagerAdapter
{
    private int tabno;

    public fragmentmanager(@NonNull FragmentManager fm, int tabno) {
        super(fm);
        this.tabno = tabno;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new Home();
            case 1:return new PhysiologicalMeasure();
            case 2:return new FavPharmacy();
            case 3:return new ltc();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
