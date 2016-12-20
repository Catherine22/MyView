package catherine.com.myview.view.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Catherine on 2016/12/19.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class ItemMoveCallback extends ItemTouchHelper.Callback {

    private final OnItemMoveListener mAdapter;

    public ItemMoveCallback(OnItemMoveListener adapter) {
        mAdapter = adapter;
    }

    /**
     * 支持长按RecyclerView item进入拖动操作
     *
     * @return true
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 要在view任意位置触摸事件发生时启用滑动操作，则直接在sItemViewSwipeEnabled()中返回true就可以了
     *
     * @return true
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * 指定可以支持的拖放和滑动的方向
     * 上下为拖动（drag），左右为滑动（swipe）
     *
     * @param recyclerView RecyclerView
     * @param viewHolder   RecyclerView.ViewHolder
     * @return makeMovementFlags
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖移，通知adapter
     *
     * @param recyclerView RecyclerView
     * @param viewHolder   RecyclerView.ViewHolder
     * @param target       RecyclerView.ViewHolder
     * @return true
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemDragAndDrop(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 左右滑动，通知adapter
     *
     * @param viewHolder RecyclerView.ViewHolder
     * @param direction  int
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemSwipe(viewHolder.getAdapterPosition());
    }
}