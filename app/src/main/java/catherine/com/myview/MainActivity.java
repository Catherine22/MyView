package catherine.com.myview;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import catherine.com.myview.adapters.MainViewPagerAdapter;
import catherine.com.myview.common.CLog;
import catherine.com.myview.view.CustomViewPager;
import catherine.com.myview.view.PagerSlidingTabStrip;

public class MainActivity extends FragmentActivity {
    private MainViewPagerAdapter adapter;
    private CustomViewPager viewPager;
    private PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//----------------------------ViewPager----------------------------
        adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addViewTabResource(R.drawable.selector01);
        adapter.addViewTabResource(R.drawable.selector02);
        adapter.addViewTabResource(R.drawable.selector03);
        adapter.addViewTabResource(R.drawable.selector04);
        adapter.addViewTabResource(R.drawable.selector05);

        viewPager = (CustomViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

        tabs = (PagerSlidingTabStrip) findViewById(R.id.pagertabs);
        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CLog.d(CLog.getTag(), "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                CLog.d(CLog.getTag(), "onPageScrollStateChanged:" + state);

            }
        });
//----------------------------ViewPager----------------------------
    }
}
