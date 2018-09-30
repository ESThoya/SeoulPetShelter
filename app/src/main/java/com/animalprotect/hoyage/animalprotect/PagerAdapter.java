package com.animalprotect.hoyage.animalprotect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private static int PAGE_NUMBER = 2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PageOneFragment.newInstance();
            case 1:
                return PageTwoFragment.newInstance();
            /*case 2:
                return PageThreeFragment.newInstance();*/
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "유기견 조회";
            case 1:
                return "병원 조회";
            /*case 2:
                return "세번째 탭";*/
            default:
                return null;
        }
    }
}
