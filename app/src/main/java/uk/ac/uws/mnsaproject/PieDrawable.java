package uk.ac.uws.mnsaproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class PieDrawable extends Drawable
{
    private Paint carbPaint;
    private Paint fatPaint;
    private Paint proteinPaint;

    private int mCarbs;
    private int mFat;
    private int mProtein;

    public PieDrawable(int carbs, int fat, int protein, Context aContext)
    {
        if (carbs + fat + protein == 100)
        {
            mCarbs = carbs;
            mFat = fat;
            mProtein = protein;
        }
        else
        {
            mCarbs = 25;
            mFat = 40;
            mProtein = 35;
        }

        carbPaint = new Paint();
        carbPaint.setColor(aContext.getColor(R.color.red));
        carbPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        fatPaint = new Paint();
        fatPaint.setColor(aContext.getColor(R.color.blue));
        fatPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        proteinPaint = new Paint();
        proteinPaint.setColor(aContext.getColor(R.color.yellow));
        proteinPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas)
    {
        //get the size of the canvas we're working on
        int canvasWidth = getBounds().width();
        int canvasHeight = getBounds().height();
        Log.i("Pie", canvasWidth + " x " + canvasHeight);

        float radius = (Math.min(canvasHeight, canvasWidth) - 32) / 2;
        float centreX = canvasWidth / 2;
        float centreY = canvasHeight / 2;

        float carbsAngle = mCarbs / 100f * 360f;
        float fatAngle = mFat / 100f * 360f;
        float proteinAngle = 360 - (carbsAngle + fatAngle);

        RectF pieBounds = new RectF(centreX - radius,
                                    centreY - radius,
                                    centreX + radius,
                                    centreY + radius);

        Path carbsPath = new Path();
        carbsPath.moveTo(centreX, centreY);
        carbsPath.lineTo(centreX, centreY - radius);
        carbsPath.addArc(pieBounds, -90, carbsAngle);
        carbsPath.lineTo(centreX, centreY);
        Matrix carbsMatrix = new Matrix();
        carbsMatrix.setTranslate(8, 0);
        carbsPath.transform(carbsMatrix);
        canvas.drawPath(carbsPath, carbPaint);

        float fatStartX = (float)(centreX + (Math.sin(Math.toRadians(carbsAngle)) * radius));
        float fatStartY = (float)(centreY + (Math.cos(Math.toRadians(carbsAngle)) * radius));

        Path fatPath = new Path();
        fatPath.moveTo(centreX, centreY);
        fatPath.lineTo(fatStartX, fatStartY);
        fatPath.addArc(pieBounds, -90 + carbsAngle, fatAngle);
        fatPath.lineTo(centreX, centreY);
        Matrix fatMatrix = new Matrix();
        fatMatrix.setTranslate(0, 8);
        fatPath.transform(fatMatrix);
        canvas.drawPath(fatPath, fatPaint);

        float proteinStartX = (float)(centreX + (Math.sin(Math.toRadians(carbsAngle + fatAngle)) * radius));
        float proteinStartY = (float)(centreY + (Math.cos(Math.toRadians(carbsAngle + fatAngle)) * radius));

        Path proteinPath = new Path();
        proteinPath.moveTo(centreX, centreY);
        proteinPath.lineTo(proteinStartX, proteinStartY);
        proteinPath.addArc(pieBounds, -90 + (fatAngle + carbsAngle), proteinAngle);
        proteinPath.lineTo(centreX, centreY);
        Matrix proteinMatrix = new Matrix();
        proteinMatrix.setTranslate(-8, 0);
        proteinPath.transform(proteinMatrix);
        canvas.drawPath(proteinPath, proteinPaint);
    }

    @Override
    public void setAlpha(int i)
    {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity()
    {
        return PixelFormat.OPAQUE;
    }
}
