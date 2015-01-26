package ru.camino.tinyrenderer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import ru.camino.tinyrenderer.utils.TargaImageReader;
import ru.camino.tinyrenderer.R;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ImageView iv = (ImageView) findViewById(R.id.activity_main_image);

        try {
            // Cheating with extension to avoid asset compression and related problems. See: http://ponystyle.com/blog/2010/03/26/dealing-with-asset-compression-in-android-apps/
            final Bitmap b = TargaImageReader.getImage(this, "african_head_diffuse.tga.jpg");
            //final Bitmap b = TargaImageReader.getImage(this, "targa_test.tga.jpg");

            Log.d(TAG, "Image loaded. Width: " + b.getWidth() + ", height: " + b.getHeight());

            iv.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
