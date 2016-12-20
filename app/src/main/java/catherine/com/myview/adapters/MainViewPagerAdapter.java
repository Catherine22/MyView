package catherine.com.myview.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import catherine.com.myview.RefreshableListFragment;
import catherine.com.myview.RefreshableGridFragment;
import catherine.com.myview.Fragment2;
import catherine.com.myview.Fragment3;
import catherine.com.myview.Fragment4;
import catherine.com.myview.view.PagerSlidingTabStrip;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    private List<Integer> viewTabsResource = new ArrayList<Integer>();
    private Fragment fragment0, fragment1, fragment2, fragment3, fragment4;
    private final int PAGES = 5;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragment0 == null)
                    fragment0 = new RefreshableListFragment();
                return fragment0;
            case 1:
                if (fragment1 == null)
                    fragment1 = new RefreshableGridFragment();
                return fragment1;

            case 2:
                if (fragment2 == null)
                    fragment2 = new Fragment2();
                return fragment2;

            case 3:
                if (fragment3 == null)
                    fragment3 = new Fragment3();
                return fragment3;
            case 4:
                if (fragment4 == null)
                    fragment4 = new Fragment4();
                return fragment4;
            default:
                throw new IllegalArgumentException("The item position should be less or equal to:" + PAGES);
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    public void addViewTabResource(int resId) {
        viewTabsResource.add(resId);
    }

    @Override
    public int getPageIconResId(int position) {
        return viewTabsResource.get(position);
    }
}
