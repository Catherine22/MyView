package catherine.com.myview.adapters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import catherine.com.myview.R;
import catherine.com.myview.common.CLog;
import catherine.com.myview.entities.MyData;
import catherine.com.myview.view.recycler_view.OnFooterClickListener;
import catherine.com.myview.view.recycler_view.OnHeaderClickListener;
import catherine.com.myview.view.recycler_view.OnItemClickListener;
import catherine.com.myview.view.recycler_view.OnItemMoveListener;

/**
 * Created by Catherine on 2016/12/16.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements OnItemMoveListener {

    private Context ctx;
    private List<MyData> myDataList;
    private List<View> headers;
    private List<View> footers;

    private final int DEFAULT = 0;
    private final int IS_HEADER = 1;
    private final int IS_FOOTER = 2;

    public RecyclerViewAdapter(Context ctx, List<MyData> myDataList, @Nullable OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
        this.ctx = ctx;
        this.myDataList = myDataList;
        headers = new ArrayList<>();
        footers = new ArrayList<>();
    }

    /**
     * 取得交换过、移除过的正确的列表
     *
     * @return List<BlockedCaller> 当前列表
     */
    public List<MyData> getList() {
        return myDataList;
    }

    /**
     * 添加item到列表
     *
     * @param item MyData
     */
    public void addItem(MyData item) {
        myDataList.add(item);
//        notifyDataSetChanged();
    }

    /**
     * 添加header
     *
     * @param view header
     */
    public void addHeader(View view) {
        if (view != null) {
            headers.add(view);
//        notifyDataSetChanged();
        }
    }

    public void removeHeader(int position) {
        headers.remove(position);
//        notifyDataSetChanged();
    }

    public int getHeaderSize() {
        return headers.size();
    }

    /**
     * 添加footer
     *
     * @param view footer
     */
    public void addFooter(View view) {
        if (view != null) {
            footers.add(view);
//        notifyDataSetChanged();
        }
    }

    public void removeFooter(int position) {
        footers.remove(position);
//        notifyDataSetChanged();
    }

    public int getFooterSize() {
        return footers.size();
    }

    /**
     * 数据改变后,呼叫此方法修改列表
     *
     * @param newList 新数据
     */
    public void updateDataSet(List<MyData> newList) {
        myDataList = newList;
    }


    /**
     * 取得总数(全部的items, headers and footers)
     *
     * @return items + headers + footers
     */
    @Override
    public int getItemCount() {
        return myDataList.size() + headers.size() + footers.size();
    }

    /**
     * 取得数组总数
     *
     * @return items
     */
    public int getRealItemCount() {
        return myDataList.size();
    }

    /**
     * 拖移item做交换(同时各自的position也会交换)
     *
     * @param fromPosition item1原本的位置
     * @param toPosition   item1新的位置
     */
    @Override
    public void onItemDragAndDrop(int fromPosition, int toPosition) {
        Collections.swap(myDataList, fromPosition, toPosition);
        //非常重要，调用后Adapter才能知道发生了改变。
        notifyItemMoved(fromPosition, toPosition);
        mOnItemClickListener.onItemSwap(fromPosition - headers.size(), toPosition - headers.size());
    }

    /**
     * 滑动事件(移除该item)
     *
     * @param position item的位置
     */
    @Override
    public void onItemSwipe(int position) {
        mOnItemClickListener.onItemDismiss(position - headers.size(), myDataList.get(position - headers.size()));
        myDataList.remove(position);
        //非常重要，调用后Adapter才能知道发生了改变。
        notifyItemRemoved(position);
    }


    private OnItemClickListener mOnItemClickListener;
    private OnHeaderClickListener mOnHeaderClickListener;

    /**
     * 设置监听item点击事件
     *
     * @param mOnItemClickListener 点击事件接口
     */
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /**
     * 设置监听header和footer点击事件
     *
     * @param mOnHeaderClickListener 点击事件接口
     */
    @SuppressWarnings("unused")
    public void setOnHeaderClickListener(OnHeaderClickListener mOnHeaderClickListener) {
        this.mOnHeaderClickListener = mOnHeaderClickListener;
    }

    private OnFooterClickListener mOnFooterClickListener;

    /**
     * 设置监听header和footer点击事件
     *
     * @param mOnFooterClickListener 点击事件接口
     */
    @SuppressWarnings("unused")
    public void setOnFooterClickListener(OnFooterClickListener mOnFooterClickListener) {
        this.mOnFooterClickListener = mOnFooterClickListener;
    }



    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view
     */
    @Override
    public int getItemViewType(int position) {
        if (headers.size() == 0 && footers.size() == 0) {
            return DEFAULT;
        }

        if (position == (headers.size() - 1)) {
            return IS_HEADER;
        }
        if (position >= (getItemCount() - footers.size())) {
            return IS_FOOTER;
        }

        return DEFAULT;
    }

    /**
     * GridLayoutManager中的setSpanSizeLookup可以设置每个item所占的格数，<br>
     * 当前item为header或footer时，则占用一整栏，若为一般item则占用一格。
     *
     * @param recyclerView my recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) == IS_HEADER || getItemViewType(position) == IS_FOOTER)
                        return gridLayoutManager.getSpanCount();
                    else
                        return 1;
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            return new MyViewHolder(headers.get(0), IS_HEADER);
        } else if (viewType == IS_FOOTER) {
            return new MyViewHolder(footers.get(0), IS_FOOTER);
        } else {
            return new MyViewHolder(LayoutInflater.from(
                    ctx).inflate(R.layout.gc_frag1_item, parent,
                    false), DEFAULT);
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // 如果设置了回调，则设置点击事件（回调事件中，返回的是该header在header数组里的位置）
        if (getItemViewType(position) == IS_HEADER && mOnHeaderClickListener != null) {
            headers.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnHeaderClickListener.onHeaderClick(headers.get(position), pos);
                }
            });
            headers.get(position).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnHeaderClickListener.onHeaderLongClick(headers.get(position), pos);
                    return false;
                }
            });
        }
        // 如果设置了回调，则设置点击事件（回调事件中，返回的是该footer在footer数组里的位置）
        else if (getItemViewType(position) == IS_FOOTER && mOnFooterClickListener != null) {
            footers.get(position - (myDataList.size() + headers.size())).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition() - (myDataList.size() + headers.size());
                    mOnFooterClickListener.onFooterClick(footers.get(pos), pos);
                }
            });
            footers.get(position - (myDataList.size() + headers.size())).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition() - (myDataList.size() + headers.size());
                    mOnFooterClickListener.onFooterLongClick(footers.get(pos), pos);
                    return false;
                }
            });
        } else {
            holder.title.setText(myDataList.get(position - headers.size()).getTitle());
            holder.desc.setText(myDataList.get(position - headers.size()).getDescription());
            //If you don't need to do anything else and just want to show images, all you have to do is setImageURI()
//            holder.sdv.setImageURI(myDataList.get(position-headers.size()).getPicUrl());

            //fresco
            //supports the streaming of progressive JPEG images over the network.
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(myDataList.get(position - headers.size()).getPicUrl()))
                    .setProgressiveRenderingEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setUri(myDataList.get(position - headers.size()).getPicUrl())
                    .setTapToRetryEnabled(true)
                    .setOldController(holder.sdv.getController())
                    .setControllerListener(listener)
                    .build();
            holder.sdv.setController(controller);

            // 如果设置了回调，则设置点击事件（回调事件中，返回的是item在数组里的位置，不含headers和footers）
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition() - headers.size();
                        mOnItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition() - headers.size();
                        mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }
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

        private MyViewHolder(View view, int type) {
            super(view);
            switch (type) {
                case IS_HEADER:
                    break;
                case IS_FOOTER:
                    break;
                default:
                    title = (TextView) view.findViewById(R.id.tv_title);
                    desc = (TextView) view.findViewById(R.id.tv_desc);
                    sdv = (SimpleDraweeView) view.findViewById(R.id.sdv);
            }
        }
    }
}