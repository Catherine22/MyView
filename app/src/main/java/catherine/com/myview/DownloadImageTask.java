package catherine.com.myview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Catherine on 2016/12/16.
 * Soft-World Inc.
 * catherine919@soft-world.com.tw
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;
    Bitmap imgBmp;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlDisplay = strings[0];
        imgBmp = null;
        try {
            InputStream is = new java.net.URL(urlDisplay).openStream();
            imgBmp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgBmp;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
