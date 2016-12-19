package catherine.com.myview.adapters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.Collections;
import java.util.List;

import catherine.com.myview.R;
import catherine.com.myview.common.CLog;
import catherine.com.myview.entities.MyData;
import catherine.com.myview.view.recycler_view.OnRecyclerViewItemTouch;

/**
 * Created by Catherine on 2016/12/16.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class RecyclerViewAdapterFrag1 extends RecyclerView.Adapter<RecyclerViewAdapterFrag1.MyViewHolder> implements OnRecyclerViewItemTouch {

    private Context ctx;
    private List<MyData> myDataList;

    public RecyclerViewAdapterFrag1(Context ctx, List<MyData> myDataList, @Nullable OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
        this.ctx = ctx;
        this.myDataList = myDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(
                ctx).inflate(R.layout.gc_frag1_item, parent,
                false));
    }

    /**
     * 取得交换过、移除过的正确的列表
     *
     * @return List<BlockedCaller> 当前列表
     */
    public List<MyData> getList() {
        return myDataList;
    }

    public void addItem(MyData item) {
        myDataList.add(item);
        notifyDataSetChanged();
    }

    /**
     * 数据改变后,呼叫此方法修改列表
     *
     * @param newList 新数据
     */
    public void updateDataSet(List<MyData> newList) {
        myDataList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }

    /**
     * 交换items(同时各自的position也会交换)
     *
     * @param fromPosition item1原本的位置
     * @param toPosition   item1新的位置
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(myDataList, fromPosition, toPosition);
        //非常重要，调用后Adapter才能知道发生了改变。
        notifyItemMoved(fromPosition, toPosition);
        mOnItemClickLitener.onItemSwap(fromPosition, toPosition);
    }

    /**
     * 滑动事件(移除该item)
     *
     * @param position item的位置
     */
    @Override
    public void onItemDismiss(int position) {
        mOnItemClickLitener.onItemDismiss(position, myDataList.get(position));
        myDataList.remove(position);
        //非常重要，调用后Adapter才能知道发生了改变。
        notifyItemRemoved(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

        void onItemSwap(int fromPosition, int toPosition);

        void onItemDismiss(int position, MyData item);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(myDataList.get(position).getTitle());
        holder.desc.setText(myDataList.get(position).getDescription());
        //If you don't need to do anything else and just want to show images, all you have to do is setImageURI()
        //viewHolder.sdv.setImageURI(myDataList.get(position).getPicUrl());

        //fresco
        //supports the streaming of progressive JPEG images over the network.
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(myDataList.get(position).getPicUrl()))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setUri(myDataList.get(position).getPicUrl())
                .setTapToRetryEnabled(true)
                .setOldController(holder.sdv.getController())
                .setControllerListener(listener)
                .build();
        holder.sdv.setController(controller);

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    private ControllerListener listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFailure(String id, Throwable throwable) {
            String content = String.format("Error loading %s", id);
            CLog.d(CLog.getTag(), content);
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            if (imageInfo == null) {
                return;
            }
            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            String content = String.format("Final image received! \nSize %d x %d \nQuality level %d, good enough: %s, full quality: %s",
                    imageInfo.getWidth(),
                    imageInfo.getHeight(),
                    qualityInfo.getQuality(),
                    qualityInfo.isOfGoodEnoughQuality(),
                    qualityInfo.isOfFullQuality());
//            CLog.d(CLog.getTag(), content);
        }
    };

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        SimpleDraweeView sdv;

        private MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_title);
            desc = (TextView) view.findViewById(R.id.tv_desc);
            sdv = (SimpleDraweeView) view.findViewById(R.id.sdv);
        }
    }
}