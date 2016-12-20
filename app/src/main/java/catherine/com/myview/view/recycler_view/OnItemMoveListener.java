package catherine.com.myview.view.recycler_view;

/**
 * Created by Catherine on 2016/12/19.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */


/**
 * 用于通知底层数据的更新
 */
public interface OnItemMoveListener {

    void onItemDragAndDrop(int fromPosition, int toPosition);

    void onItemSwipe(int position);
}
