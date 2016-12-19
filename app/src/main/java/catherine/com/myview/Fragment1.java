package catherine.com.myview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import catherine.com.myview.adapters.RecyclerViewAdapterFrag1;
import catherine.com.myview.common.CLog;
import catherine.com.myview.common.Resources;
import catherine.com.myview.entities.MyData;
import catherine.com.myview.view.recycler_view.DividerGridItemDecoration;
import catherine.com.myview.view.ViewUtils;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class Fragment1 extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private RecyclerViewAdapterFrag1 adapter;
    private List<MyData> myDataList;
    private Handler timerHandler;
    private ProgressBar progressBar;

    /**
     * Load how many items at a time
     */
    private final int LOADING_ITEMS = 10;
    //Calculate totalDataLength referring to APIs response.
    private int totalDataLength = Resources.IMAGES.length;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        myDataList = new ArrayList<>();
        timerHandler = new Handler();
        fillInData(50);
        adapter = new RecyclerViewAdapterFrag1(getActivity(), myDataList, null);

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
        rv = (RecyclerView) view.findViewById(R.id.rv);
        //Set the header on GridView
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

        //Set the footer on GridView

        progressBar = new ProgressBar(getActivity());

        //类似ListView的效果
        //加入分割线
//        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //类似GridView的效果
        //加入分割线
        rv.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3, StaggeredGridLayoutManager.VERTICAL, false));

        rv.setAdapter(adapter);
        return view;
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
            adapter.updateDataSet(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
            //stop refreshing
            swipeRefreshLayout.setRefreshing(false);
        }
    };


    private void loadMore() {
        CLog.i(CLog.getTag(), "loadMore()");
        int rest = totalDataLength - myDataList.size();
        //If the rest of items < LOADING_ITEMS, just load the rest.
        int loadingSize = rest > LOADING_ITEMS ? LOADING_ITEMS : rest;
        if (loadingSize > 0) {
            fillInData(loadingSize);
            //update data in adapter
            adapter.updateDataSet(myDataList);
            //redraw ListView
            adapter.notifyDataSetChanged();
        }
    }
}
