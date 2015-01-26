package ru.camino.tinyrenderer.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Reads RLE-compressed Targa image from assets into Android {@link android.graphics.Bitmap}<br/>
 * Refer to <a href="http://ponystyle.com/blog/2010/03/26/dealing-with-asset-compression-in-android-apps/">this hint</a> to find out how to cheat Android to read "unknown" asset files
 */
public class TargaImageReader {
    public static Bitmap getImage(Context context, String fileName) throws IOException {
        final AssetFileDescriptor afd = context.getAssets().openFd(fileName);

        byte[] buf = new byte[(int) afd.getDeclaredLength()];
        BufferedInputStream bis = new BufferedInputStream(afd.createInputStream());
        bis.read(buf);
        bis.close();
        return decode(buf);
    }

    private static int offset;

    private static int btoi(byte b) {
        int a = b;
        return (a<0?256+a:a);
    }

    private static int read(byte[] buf) {
        return btoi(buf[offset++]);
    }

    public static Bitmap decode(byte[] buf) throws IOException {
        offset = 0;

        // Reading header
        for (int i = 0; i < 12; i++) {
            read(buf);
        }
        int width = read(buf) + (read(buf) << 8);
        int height = read(buf) + (read(buf) << 8);
        read(buf);
        read(buf);

        // Reading data
        int n = width*height;
        int[] pixels = new int[n];
        int idx=0;

        while (n>0) {
            int nb = read(buf);
            if ((nb&0x80) == 0) {
                for (int i = 0; i <=nb; i++) {
                    int b = read(buf);
                    int g = read(buf);
                    int r = read(buf);
                    pixels[idx++] = 0xff000000 | (r<<16) | (g<<8) | b;
                }
            } else {
                nb &= 0x7f;
                int b = read(buf);
                int g = read(buf);
                int r = read(buf);
                int v = 0xff000000 | (r<<16) | (g<<8) | b;
                for (int i = 0; i <= nb; i++) {
                    pixels[idx++] = v;
                }
            }
            n -= nb + 1;
        }

        final Bitmap b = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
        final Bitmap fb = Bitmap.createBitmap(b.getWidth(), b.getHeight(), b.getConfig()); // flipped image
        final Canvas c = new Canvas(fb);
        final Paint p = new Paint();
        final Matrix m = new Matrix();

        m.setScale(1, -1);
        m.postTranslate(0, b.getHeight());

        c.drawBitmap(b, m, p);

        return fb;
    }
}
