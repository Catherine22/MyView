package catherine.com.myview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import catherine.com.myview.R;
import catherine.com.myview.entities.MyData;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class ListViewAdapterFrag0 extends BaseAdapter {
    private List<MyData> myDataList;
    private LayoutInflater mInflater;

    public ListViewAdapterFrag0(Context ctx, List<MyData> myDataList) {
        this.myDataList = myDataList;
        mInflater = LayoutInflater.from(ctx);
    }

    public void setMyDataList(List<MyData> newList) {
        myDataList = newList;
    }

    @Override
    public int getCount() {
        return myDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return myDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.lv_frag0_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.desc = (TextView) view.findViewById(R.id.tv_desc);
            viewHolder.sdv = (SimpleDraweeView) view.findViewById(R.id.sdv);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();

        viewHolder.title.setText(myDataList.get(i).getTitle());
        viewHolder.desc.setText(myDataList.get(i).getDescription());
        viewHolder.sdv.setImageURI(myDataList.get(i).getPicUrl());

        return view;
    }

    private class ViewHolder {
        TextView title, desc;
        SimpleDraweeView sdv;
    }
}
