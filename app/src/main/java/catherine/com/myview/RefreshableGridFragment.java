package catherine.com.myview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import catherine.com.myview.adapters.RecyclerViewAdapter;
import catherine.com.myview.common.CLog;
import catherine.com.myview.common.Resources;
import catherine.com.myview.entities.MyData;
import catherine.com.myview.view.recycler_view.DividerGridItemDecoration;
import catherine.com.myview.view.ViewUtils;
import catherine.com.myview.view.recycler_view.DividerItemDecoration;
import catherine.com.myview.view.recycler_view.OnFooterClickListener;
import catherine.com.myview.view.recycler_view.OnHeaderClickListener;
import catherine.com.myview.view.recycler_view.OnItemClickListener;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class RefreshableGridFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private RecyclerViewAdapter adapter;
    private ItemTouchHelper itemTouchHelper;
    private MyScrollListener scrollListener;
    private int dividerHeight;
    private List<MyData> myDataList;
    private Handler timerHandler;
    private ProgressBar progressBar;
    private boolean finishedLoadingAll;

    /**
     * Load how many items at a time.
     * Set this number several times the size of columns
     */
    private final int LOADING_ITEMS = 12;
    //Calculate MAX_DATA_LENGTH referring to APIs response.
    private final int MAX_DATA_LENGTH = Resources.IMAGES.length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        myDataList = new ArrayList<>();
        timerHandler = new Handler();
        scrollListener = new MyScrollListener();
        fillInData(LOADING_ITEMS);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CLog.d(CLog.getTag(), "onRefresh()");
                finishedLoadingAll = false;
                //Call APIs and stop refreshing while getting response from server
                //In this case, stopping refreshing in 3 seconds.
                timerHandler.postDelayed(runnable, 3000);
            }
        });
        rv = (RecyclerView) view.findViewById(R.id.rv);
        //Set a header on RecyclerView
        TextView title = new TextView(getActivity());
        title.setTextSize(18);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setBackgroundColor(getResources().getColor(R.color.action_bar_background, null));
        } else
            title.setBackgroundColor(getResources().getColor(R.color.action_bar_background));
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ViewUtils.convertDpToPixel(getActivity(), 25)));
        title.setText("Refreshable RecyclerView");

        progressBar = new ProgressBar(getActivity());
        progressBar.setVisibility(View.VISIBLE);

//-------------------------Make RecyclerView seem like a ListView-------------------------
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //Add dividers
//        DividerItemDecoration listItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
//        rv.addItemDecoration(listItemDecoration);
//        dividerHeight = listItemDecoration.getDividerHeight();
//-------------------------Make RecyclerView seem like a ListView-------------------------


//-------------------------Make RecyclerView seem like a GridView-------------------------
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3, StaggeredGridLayoutManager.VERTICAL, false));
        //Add dividers
        DividerGridItemDecoration gridItemDecoration = new DividerGridItemDecoration(getActivity());
        rv.addItemDecoration(gridItemDecoration);
        dividerHeight = gridItemDecoration.getDividerHeight();
//-------------------------Make RecyclerView seem like a GridView-------------------------


        //Click items to call back
        adapter = new RecyclerViewAdapter(getActivity(), myDataList, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CLog.d(CLog.getTag(), "onItemClick " + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                CLog.d(CLog.getTag(), "onItemLongClick " + position);
            }

            //It works after you set up ItemTouchHelper
            @Override
            public void onItemSwap(int fromPosition, int toPosition) {
                CLog.d(CLog.getTag(), "onItemSwap " + fromPosition + " to " + toPosition);
            }

            //It works after you set up ItemTouchHelper
            @Override
            public void onItemDismiss(int position, MyData item) {
                CLog.d(CLog.getTag(), "onItemDismiss " + position);
            }
        });

        //Click headers to call back
        adapter.setOnHeaderClickListener(new OnHeaderClickListener() {
            @Override
            public void onHeaderClick(View view, int position) {
                CLog.d(CLog.getTag(), "onHeaderClick " + position);

            }

            @Override
            public void onHeaderLongClick(View view, int position) {
                CLog.d(CLog.getTag(), "onHeaderLongClick " + position);

            }
        });

        //Click footers to call back
        adapter.setOnFooterClickListener(new OnFooterClickListener() {
            @Override
            public void onFooterClick(View view, int position) {
                CLog.d(CLog.getTag(), "onFooterClick " + position);

            }

            @Override
            public void onFooterLongClick(View view, int position) {
                CLog.d(CLog.getTag(), "onFooterLongClick " + position);

            }
        });

        //Drag n drop(switch 2 items), swipe to remove an item
        //Dragging n dropping doesn't work in grids.
//        itemTouchHelper = new ItemTouchHelper(new ItemMoveCallback(adapter));
//        itemTouchHelper.attachToRecyclerView(rv);

        adapter.addHeader(title);
        adapter.addFooter(progressBar);
        notifyDataSetChangedHandler.post(r);
        rv.setAdapter(adapter);

        //Add layoutChangeListener to get the event of finished notifyDataSetChanged() so that RecyclerView won't catch scrolling event during redrawing view.
        rv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                if (!finishedLoadingAll) {
                    rv.clearOnScrollListeners();
                    //Scroll to load more
                    rv.addOnScrollListener(scrollListener);
                }
            }
        });
        return view;
    }

    private class MyScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (adapter.getFooterSize() == 0) {
                adapter.addFooter(progressBar);
                notifyDataSetChangedHandler.post(r);
            }
            progressBar.setVisibility(View.VISIBLE);

            //There is always a divider in the bottom.
            if (recyclerView.getHeight() + dividerHeight >= recyclerView.getChildAt(recyclerView.getChildCount() - 1).getBottom()) {
                //It is scrolled all the way down here

                // 滑不动了
                // You scrolled to the end of the RecyclerView included the header
                // Reach to the end of RecyclerView
                // Each downloaded data which is supposed to be filled in the RecyclerView has been loaded.
                if (adapter.getRealItemCount() == MAX_DATA_LENGTH) {
                    adapter.removeFooter(0);
                    notifyDataSetChangedHandler.post(r);
                    finishedLoadingAll = true;
                } else
                    loadMore();
            }
        }
    }


    private int lpPointer = 0;

    /**
     * Loading loadingProgress items at a time
     *
     * @param loadingProgress how many items are supposed to be load at a time
     */
    private void fillInData(int loadingProgress) {
        try {
            //It seems like download data
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadingProgress += lpPointer;
        String title = "TITLE ";
        String desc = "DESC. ";
        if (newData) {
            title = "new TITLE ";
            desc = "new DESC. ";
        }
        for (int i = lpPointer; i < loadingProgress; i++) {
            lpPointer++;
            MyData mData = new MyData();
            mData.setTitle(title + i);
            mData.setDescription(desc + i);
            mData.setPicUrl(Resources.IMAGES[i]);
            myDataList.add(mData);
        }
    }


    //This is just for testing.
    private boolean newData;
    private Runnable runnable = new Runnable() {
        public void run() {
            //refill data
            myDataList.clear();
            lpPointer = 0;
            newData = true;
            fillInData(LOADING_ITEMS);
            //update and redraw recyclerView
            adapter.updateDataSet(myDataList);
            notifyDataSetChangedHandler.post(r);
            //stop refreshing
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    private void loadMore() {
        CLog.i(CLog.getTag(), "loadMore()");
        int rest = MAX_DATA_LENGTH - myDataList.size();
        //If the rest of items < LOADING_ITEMS, just load the rest.
        int loadingSize = rest > LOADING_ITEMS ? LOADING_ITEMS : rest;
        if (loadingSize > 0) {
            fillInData(loadingSize);

            //update and redraw recyclerView
            adapter.updateDataSet(myDataList);
            notifyDataSetChangedHandler.post(r);
        }
    }

    /**
     * Use handler to do notifyDataSetChanged() or you'll get IllegalStateException
     */
    Handler notifyDataSetChangedHandler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {
            rv.clearOnScrollListeners();
            adapter.notifyDataSetChanged();
        }
    };
}
