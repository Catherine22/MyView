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

import catherine.com.myview.adapters.ListViewAdapterFrag0;
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
    private ListViewAdapterFrag0 adapter;
    private List<MyData> myDataList;
    private Handler timerHandler;

    /**
     * Load how many items at a time
     */
    private final int LOADING_ITEMS = 10;

    //Calculate totalDataLength referring to APIs response.
    private int totalDataLength = Resources.IMAGES.length;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_refreshable_list, container, false);
        timerHandler = new Handler();
        myDataList = new ArrayList<>();
        fillInData(LOADING_ITEMS);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.yellow, R.color.green, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CLog.d(CLog.getTag(), "onRefresh()");
                //Call APIs and stop refreshing while getting response from server
                //In this case, stopping refreshing in 3 seconds.
                timerHandler.postDelayed(runnable, 3000);
            }
        });
        listView = (ListView) view.findViewById(R.id.lv);
        adapter = new ListViewAdapterFrag0(getActivity(), myDataList);
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
        title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) ViewUtils.convertDpToPixel(getActivity(),25)));
        title.setText("Refreshable ListView");
        listView.addHeaderView(title);

//Set the footer on ListView
        progressBar = new ProgressBar(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        listView.addFooterView(progressBar);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                CLog.d(CLog.getTag(), "firstVisibleItem:" + firstVisibleItem + "/visibleItemCount:" + visibleItemCount + "/totalItemCount:" + totalItemCount);
                final int lastItem = firstVisibleItem + visibleItemCount;

                if (listView.getFooterViewsCount() == 0)
                    listView.addFooterView(progressBar);

                //Reach to end of ListView
                if ((lastItem == totalItemCount) && myDataList != null && myDataList.size() != 0) {
                    //Every downloaded data which is supposed to be filled in the ListView has been loaded.
                    if (totalDataLength == myDataList.size())
                        //Hide the footer
                        listView.removeFooterView(progressBar);
                    else {
                        loadMore();
                    }
                }

            }
        });
        return view;
    }

    private void loadMore() {
        CLog.i(CLog.getTag(), "loadMore()");
        int rest = totalDataLength - myDataList.size();
        //If the rest of items < LOADING_ITEMS, just load the rest.
        int loadingSize = rest > LOADING_ITEMS ? LOADING_ITEMS : rest;
        if (loadingSize > 0) {
            fillInData(loadingSize);
            //update data in adapter
            adapter.setMyDataList(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
        }
    }

    private int lpPointer = 0;

    /**
     * Loading loadingProgress items at a time
     *
     * @param loadingProgress how many items are supposed to be load at a time
     */
    private void fillInData(int loadingProgress) {
        loadingProgress += lpPointer;
        for (int i = lpPointer; i < loadingProgress; i++) {
            lpPointer++;
            MyData mData = new MyData();
            mData.setTitle("TITLE " + i);
            mData.setDescription("DESC. " + i);
            mData.setPicUrl(Resources.IMAGES[i]);
            myDataList.add(mData);
        }
    }

    private void fillInNewData(int loadingProgress) {
        loadingProgress += lpPointer;
        for (int i = lpPointer; i < loadingProgress; i++) {
            MyData mData = new MyData();
            mData.setTitle("new TITLE " + i);
            mData.setDescription("new DESC. " + i);
            mData.setPicUrl(Resources.IMAGES[i]);
            myDataList.add(mData);
        }
    }

    private Runnable runnable = new Runnable() {
        public void run() {
            //refill data
            myDataList.clear();
            lpPointer = 0;
            fillInNewData(LOADING_ITEMS);
            //update data in adapter
            adapter.setMyDataList(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
            //stop refreshing
            swipeRefreshLayout.setRefreshing(false);
        }
    };

}
