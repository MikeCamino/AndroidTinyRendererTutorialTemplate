package ru.camino.tinyrenderer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import ru.camino.tinyrenderer.utils.TargaImageReader;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    // Change these values to match desired canvas size
    private static final int CANVAS_WIDTH = 100;
    private static final int CANVAS_HEIGHT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ImageView iv = (ImageView) findViewById(R.id.activity_main_image);

        // Bitmap to draw into
        final Bitmap b = Bitmap.createBitmap(CANVAS_WIDTH, CANVAS_HEIGHT, Bitmap.Config.ARGB_8888);
        // Canvas to draw on
        final Canvas c = new Canvas(b);

        draw(c);

        iv.setImageBitmap(b);
    }

    /**
     * Perform all your drawings here
     * @param c {@link android.graphics.Canvas} to draw on
     */
    private void draw(Canvas c) {

    }
}
