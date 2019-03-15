package com.itc.suppaperless;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;
import java.util.Stack;

/**
 * Created by cong on 19-1-14.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MainPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
