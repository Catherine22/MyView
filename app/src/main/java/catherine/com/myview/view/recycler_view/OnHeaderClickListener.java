package catherine.com.myview.view.recycler_view;

import android.view.View;

/**
 * Created by Catherine on 2016/12/19.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public interface OnHeaderClickListener {
    void onHeaderClick(View view, int position);

    void onHeaderLongClick(View view, int position);
}
