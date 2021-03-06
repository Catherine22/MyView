package catherine.com.myview.adapters;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import java.util.List;

import catherine.com.myview.R;
import catherine.com.myview.common.CLog;
import catherine.com.myview.entities.MyData;

/**
 * Created by Catherine on 2016/12/15.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class ListViewAdapter extends BaseAdapter {
    private List<MyData> myDataList;
    private LayoutInflater mInflater;

    public ListViewAdapter(Context ctx, List<MyData> myDataList) {
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
        //If you don't need to do anything else and just want to show images, all you have to do is setImageURI()
        //viewHolder.sdv.setImageURI(myDataList.get(i).getPicUrl());

        //fresco
        //supports the streaming of progressive JPEG images over the network.
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(myDataList.get(i).getPicUrl()))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setUri(myDataList.get(i).getPicUrl())
                .setTapToRetryEnabled(true)
                .setOldController(viewHolder.sdv.getController())
                .setControllerListener(listener)
                .build();
        viewHolder.sdv.setController(controller);
        return view;
    }

    private class ViewHolder {
        TextView title, desc;
        SimpleDraweeView sdv;
    }

    private ControllerListener listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFailure(String id, Throwable throwable) {
            String content = String.format("Loading %s failed", id);
            CLog.d(CLog.getTag(), content);
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            if (imageInfo == null) {
                return;
            }
//            QualityInfo qualityInfo = imageInfo.getQualityInfo();
//            String content = String.format("Final image received! \nSize %d x %d \nQuality level %d, good enough: %s, full quality: %s",
//                    imageInfo.getWidth(),
//                    imageInfo.getHeight(),
//                    qualityInfo.getQuality(),
//                    qualityInfo.isOfGoodEnoughQuality(),
//                    qualityInfo.isOfFullQuality());
//            CLog.d(CLog.getTag(), content);
        }

        @Override
        public void onIntermediateImageFailed(String id, Throwable throwable) {
            super.onIntermediateImageFailed(id, throwable);
        }

        @Override
        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
            super.onIntermediateImageSet(id, imageInfo);
        }

        @Override
        public void onRelease(String id) {
            super.onRelease(id);
        }

        @Override
        public void onSubmit(String id, Object callerContext) {
            super.onSubmit(id, callerContext);
        }
    };
}
