package catherine.com.myview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import catherine.com.myview.adapters.ListViewAdapter;
import catherine.com.myview.common.CLog;
import catherine.com.myview.common.Resources;
import catherine.com.myview.entities.MyData;
import catherine.com.myview.view.ViewUtils;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class RefreshableListFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ProgressBar progressBar;
    private ListViewAdapter adapter;
    private List<MyData> myDataList;
    private Handler timerHandler;

    /**
     * Load how many items at a time
     */
    private final int LOADING_ITEMS = 10;

    //Get MAX_DATA_LENGTH from your server
    private int MAX_DATA_LENGTH = Resources.IMAGES.length;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_refreshable_list, container, false);
        timerHandler = new Handler();
        myDataList = new ArrayList<>();
        fillInList(LOADING_ITEMS);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CLog.d(CLog.getTag(), "onRefresh()");
                //Call APIs and stop refreshing while getting response from the server
                //In this case, stopping refreshing in 3 seconds.
                timerHandler.postDelayed(runnable, 3000);
            }
        });
        listView = (ListView) view.findViewById(R.id.lv);
        adapter = new ListViewAdapter(getActivity(), myDataList);
        listView.setAdapter(adapter);

        //Set the header on ListView
        TextView title = new TextView(getActivity());
        title.setTextSize(18);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setBackgroundColor(getResources().getColor(R.color.action_bar_background, null));
        } else
            title.setBackgroundColor(getResources().getColor(R.color.action_bar_background));
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ViewUtils.convertDpToPixel(getActivity(), 25)));
        title.setText("Refreshable ListView");
        listView.addHeaderView(title);

//Set the footer on ListView
        progressBar = new ProgressBar(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        listView.addFooterView(progressBar);
        listView.setOnScrollListener(
                new AbsListView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {
                        CLog.d(CLog.getTag(), "onScrollStateChanged:" + i);
                    }

                    @Override
                    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (absListView == null || absListView.getChildCount() <= 0) return;
                        CLog.d(CLog.getTag(), "firstVisibleItem:" + firstVisibleItem + "/visibleItemCount:" + visibleItemCount + "/totalItemCount:" + totalItemCount);

                        if (listView.getFooterViewsCount() == 0)
                            listView.addFooterView(progressBar);
                        progressBar.setVisibility(View.VISIBLE);
                        if (absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && absListView.getChildAt(absListView.getChildCount() - 1).getBottom() <= absListView.getHeight()) {
                            //It is scrolled all the way down here

                            // 滑不动了
                            // You scrolled to the end of the ListView included the header
                            // Reach to the end of ListView
                            // Each downloaded data which is supposed to be filled in the ListView has been loaded.
                            if (MAX_DATA_LENGTH == adapter.getCount())
                                //Hide the footer
                                listView.removeFooterView(progressBar);
                            else
                                loadMore();
                        }

                    }
                }

        );
        return view;
    }

    private void loadMore() {
        CLog.i(CLog.getTag(), "loadMore()");
        progressBar.setVisibility(View.INVISIBLE);
        int rest = MAX_DATA_LENGTH - myDataList.size();
        //If the rest of items < LOADING_ITEMS, just load the rest.
        int loadingSize = rest > LOADING_ITEMS ? LOADING_ITEMS : rest;
        if (loadingSize > 0) {
            fillInList(loadingSize);
            //update data in adapter
            adapter.setMyDataList(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * It depends on APIs, <br>
     * you might need to post both fromPos and toPos or both fromPos and limit to server. <br>
     */
    private int lpPointer = 0;

    /**
     * Loading a part of items at a time.<br>
     *
     * @param loadingProgress how many items are supposed to be load at a time
     */
    private void fillInList(int loadingProgress) {
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
            fillInList(LOADING_ITEMS);
            //update data in adapter
            adapter.setMyDataList(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
            //stop refreshing
            swipeRefreshLayout.setRefreshing(false);
        }
    };

}
