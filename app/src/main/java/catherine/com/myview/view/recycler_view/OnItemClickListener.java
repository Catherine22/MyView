package catherine.com.myview.view.recycler_view;

import android.view.View;

import catherine.com.myview.entities.MyData;

/**
 * Created by Catherine on 2016/12/19.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public interface OnItemClickListener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);

    void onItemSwap(int fromPosition, int toPosition);

    void onItemDismiss(int position, MyData item);
}
