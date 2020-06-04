package com.example.delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.delivery.interfaces.ActiveMarksCount;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements ActiveMarksCount {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem stopsTabItem;
    private TabItem mapTabItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.viewPager_id);
        tabLayout = findViewById(R.id.layoutTab_id);
        stopsTabItem = findViewById(R.id.stopsTab_id);
        mapTabItem = findViewById(R.id.mapTab_id);

        pagerAdapter = new com.example.delivery.adapters.PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        pager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onItemsCount(int count) {
        tabLayout.getTabAt(0).setText("STOPS (" + count + ")");
    }
}
