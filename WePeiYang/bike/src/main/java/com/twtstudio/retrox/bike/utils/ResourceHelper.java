package com.twtstudio.retrox.bike.utils;

import android.graphics.drawable.Drawable;

import com.twtstudio.retrox.bike.WePeiYangAppOld;


/**
 * Created by Rex on 2015/8/4.
 */
public class ResourceHelper {
    public static int getColor(int colorId) {
        return WePeiYangAppOld.getContext().getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        return WePeiYangAppOld.getContext().getResources().getDrawable(drawableId);
    }

    public static String getString(int stringId) {
        return WePeiYangAppOld.getContext().getResources().getString(stringId);
    }
}
