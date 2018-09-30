package com.animalprotect.hoyage.animalprotect;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout mTab = (TabLayout) findViewById(R.id.tabs);
        mTab.setupWithViewPager(viewPager);
    }

}
