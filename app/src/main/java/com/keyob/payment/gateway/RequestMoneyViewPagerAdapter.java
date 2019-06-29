package com.keyob.payment.gateway;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class RequestMoneyViewPagerAdapter extends FragmentStatePagerAdapter {

     private final List<Fragment> fragmentList = new ArrayList<>();
     private final List<String> titleList = new ArrayList<>();

    public RequestMoneyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void  addFragment(Fragment fragment ,String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}