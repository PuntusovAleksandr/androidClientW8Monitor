package com.lucerotech.aleksandrp.w8monitor.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by AleksandrP on 22.09.2016.
 */

public class ShowImages {

    public static void showImage(Context mContext, ImageView mView, int res) {
        Picasso.with(mContext)
                .load(res)
                .placeholder(res)
                .error(res)
                .into(mView);
    }

}
