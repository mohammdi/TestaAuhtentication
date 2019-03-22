package com.example.mahmood_mohammadi.testaauhtentication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class RoundCornersDrawable  extends Drawable {


    private final float mCornerRadius;
    private final RectF mRect = new RectF();
    //private final RectF mRectBottomR = new RectF();
    //private final RectF mRectBottomL = new RectF();
    private final BitmapShader mBitmapShader;
    private final Paint mPaint;
    private final int mMargin;

    public RoundCornersDrawable(Bitmap bitmap, float cornerRadius, int margin) {
        mCornerRadius = cornerRadius;

        mBitmapShader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);

        mMargin = margin;
    }


    @Override
    public void draw(@NonNull Canvas canvas) {

        canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, mPaint);
        //canvas.drawRect(mRectBottomR, mPaint); //only bottom-right corner not rounded
        //canvas.drawRect(mRectBottomL, mPaint); //only bottom-left corner not rounded
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
        //mRectBottomR.set( (bounds.width() -mMargin) / 2, (bounds.height() -mMargin)/ 2,bounds.width() - mMargin, bounds.height() - mMargin);
        // mRectBottomL.set( 0,  (bounds.height() -mMargin) / 2, (bounds.width() -mMargin)/ 2, bounds.height() - mMargin);
    }
}
