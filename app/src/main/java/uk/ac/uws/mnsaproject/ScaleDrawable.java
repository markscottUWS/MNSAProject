package uk.ac.uws.mnsaproject;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ScaleDrawable extends Drawable
{
    private Paint healthyScale;
    private Paint mainScale;
    private Paint marker;

    private int mBMI;

    private final float lowHealthyBmi = 18.5f;
    private final float highHealthyBmi = 25f;
    private final float zero = 0f;
    private final float max = 50f;



    public ScaleDrawable(int bmi, Context context)
    {
        //set up colours
        healthyScale = new Paint();
        healthyScale.setColor(context.getColor(R.color.healthyScale));
        healthyScale.setStyle(Paint.Style.FILL_AND_STROKE);
        mainScale = new Paint();
        mainScale.setColor(context.getColor(R.color.mainScale));
        mainScale.setStyle(Paint.Style.FILL_AND_STROKE);
        marker = new Paint();
        marker.setColor(context.getColor(R.color.marker));
        marker.setStyle(Paint.Style.FILL_AND_STROKE);

        mBMI = bmi;
    }


    @Override
    public void draw(@NonNull Canvas canvas)
    {
        //get the size of the canvas we're working on
        int canvasWidth = getBounds().width();
        int canvasHeight = getBounds().height();

        //where the healthy range starts and ends
        float healthyStart = (lowHealthyBmi / max) * canvasWidth;
        float healthyEnd = (highHealthyBmi / max) * canvasWidth;

        //and where we're going to put the axis
        float scaleYMin = (canvasHeight / 3) * 2;
        float scaleHeight = (canvasHeight / 6);
        float scaleYMax = scaleYMin + scaleHeight;

        //first lay down the first segment of the scale
        canvas.drawRect(zero, scaleYMax, healthyStart, scaleYMin, mainScale);

        //then the healthy range
        canvas.drawRect(healthyStart, scaleYMax, healthyEnd, scaleYMin, healthyScale);

        //then the end of the scale
        canvas.drawRect(healthyEnd, scaleYMax, canvasWidth, scaleYMin, mainScale);

        //then draw the marker
        float bmiPos = (mBMI / max) * canvasWidth;
        float markerLeft = bmiPos - (scaleHeight / 2);
        float markerRight = bmiPos + (scaleHeight / 2);
        float markerBottom = scaleYMin - 4;
        float markerMid = markerBottom - scaleHeight;
        float markerTop = markerMid - scaleHeight;

        Path markerPath = new Path();
        markerPath.moveTo(bmiPos, markerBottom);
        markerPath.lineTo(markerLeft, markerMid);
        markerPath.lineTo(markerLeft, markerTop);
        markerPath.lineTo(markerRight, markerTop);
        markerPath.lineTo(markerRight, markerMid);
        markerPath.lineTo(bmiPos, markerBottom);

        canvas.drawPath(markerPath, marker);
    }

    @Override
    public void setAlpha(int i)
    {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter)
    {

    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.OPAQUE;
    }
}
