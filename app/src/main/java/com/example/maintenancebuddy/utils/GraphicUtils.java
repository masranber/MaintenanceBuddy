package com.example.maintenancebuddy.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import io.reactivex.rxjava3.annotations.NonNull;

public class GraphicUtils {

    public static @NonNull Bitmap rasterizeVectorDrawable(@NonNull Context context, int drawableId, int tintColor) throws IllegalArgumentException {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if(drawable == null) throw new IllegalArgumentException("bad drawable resource");
        drawable.setTint(context.getResources().getColor(tintColor));

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
