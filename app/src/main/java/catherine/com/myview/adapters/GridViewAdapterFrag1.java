package catherine.com.myview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import catherine.com.myview.R;
import catherine.com.myview.entities.MyData;

/**
 * Created by Catherine on 2016/12/16.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class GridViewAdapterFrag1 extends BaseAdapter {
    private List<MyData> myDataList;
    private LayoutInflater mInflater;

    public GridViewAdapterFrag1(Context ctx, List<MyData> myDataList) {
        this.myDataList = myDataList;
        mInflater = LayoutInflater.from(ctx);
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
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.gc_frag1_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(R.id.tv_title);
            holder.desc = (TextView) view.findViewById(R.id.tv_desc);
            holder.pic = (ImageView) view.findViewById(R.id.iv);
            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        holder.title.setText(myDataList.get(i).getTitle());
        holder.desc.setText(myDataList.get(i).getDescription());
        return view;
    }

    private class ViewHolder {
        TextView title, desc;
        ImageView pic;
    }
}
