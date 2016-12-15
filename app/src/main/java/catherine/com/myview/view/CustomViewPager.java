package catherine.com.myview.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class CustomViewPager extends ViewPager {

    public CustomViewPager(Context context) {
        super(context);
    }


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        try {
            //Handle the issue only in lower versions of android
            if (v != this && v instanceof ViewPager ) {
                ViewPager viewPager = (ViewPager) v;
                int currentPage = viewPager.getCurrentItem();
                int size = viewPager.getAdapter().getCount();
                //if ViewPager has reached its end and if user tries to swipe left allow the parent to scroll
                if (currentPage == (size - 1) && dx < 0) {
                    return false;
                }
                //if ViewPager has reached its start and if user tries to swipe right allow the parent to scroll
                else if (currentPage == 0 && dx > 0) {
                    return false;
                }
                //Allow the child to scroll hence blocking parent scroll
                else {
                    return true;
                }
            }
            return super.canScroll(v, checkV, dx, x, y);
        } catch (Exception e) {
            return false;
        }
    }
}

