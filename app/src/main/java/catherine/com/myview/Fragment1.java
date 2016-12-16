package catherine.com.myview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import catherine.com.myview.adapters.GridViewAdapterFrag1;
import catherine.com.myview.common.Resources;
import catherine.com.myview.entities.MyData;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class Fragment1 extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridView gv;
    private GridViewAdapterFrag1 adapter;
    private List<MyData> myDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        myDataList = new ArrayList<>();
        fillInData(50);
        adapter = new GridViewAdapterFrag1(getActivity(), myDataList);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        gv = (GridView) view.findViewById(R.id.gv);
        gv.setAdapter(adapter);
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

}
