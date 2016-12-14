/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.net.Uri
 *  java.io.FileNotFoundException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.lefu.es.constant;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class imageUtil {
    public static Bitmap getBitmapfromPath(String string2) {
        Bitmap bitmap = null;
        if (string2 != null) {
            boolean bl = "".equals((Object)string2);
            bitmap = null;
            if (!bl) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
            }
        }
        return bitmap;
    }

    public Bitmap getBitmapTodifferencePath(String string2, Context context) {
        if (string2.length() < 7) {
            return null;
        }
        if ("content".equals((Object)string2.substring(0, 7))) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream((InputStream)context.getContentResolver().openInputStream(Uri.parse((String)string2)), (Rect)null, (BitmapFactory.Options)options);
                if (options.outWidth > 100) {
                    options.inSampleSize = 1 + (1 + options.outWidth / 100);
                    options.outWidth = 100;
                    options.outHeight /= options.inSampleSize;
                }
                options.inJustDecodeBounds = false;
                options.inPurgeable = true;
                options.inInputShareable = true;
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream)context.getContentResolver().openInputStream(Uri.parse((String)string2)), (Rect)null, (BitmapFactory.Options)options);
                return bitmap;
            }
            catch (FileNotFoundException var7_5) {
                var7_5.printStackTrace();
                return null;
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
        if (options.outWidth > 100) {
            options.inSampleSize = 1 + (1 + options.outWidth / 100);
            options.outWidth = 100;
            options.outHeight /= options.inSampleSize;
        }
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile((String)string2, (BitmapFactory.Options)options);
    }
}

