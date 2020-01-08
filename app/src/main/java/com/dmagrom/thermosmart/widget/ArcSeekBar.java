package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ArcSeekBar
        extends View
{
    private static final int ARC_STROKE_WIDTH = 25;
    private static final int INT_DEGREE_TEXT_FONT_SIZE = 250;

    private Paint arcPaint;
    private float arcDiameter;

    private Paint intDegreeTextPaint;

    public ArcSeekBar (Context context)
    {
        super (context);
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs)
    {
        super (context, attrs);
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super (context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged (w, h, oldw, oldh);

        Shader gradient;
        float radius;

        arcDiameter = (w - (ARC_STROKE_WIDTH * 2));
        radius = arcDiameter / 2;


        gradient = new SweepGradient (radius,
                                      radius,
                                      new int [] {
                                              Color.RED,
                                              Color.BLACK,
                                              Color.BLUE,
                                              Color.GREEN,
                                              Color.YELLOW,
                                              Color.RED },
                                      new float [] { 0f, 0.35f, 0.50f, 0.65f, 0.95f, 1f });
        arcPaint = new Paint ();
        arcPaint.setColor (Color.RED);
        arcPaint.setStyle (Paint.Style.STROKE);
        arcPaint.setStrokeWidth (ARC_STROKE_WIDTH);
        arcPaint.setShader (gradient);

        intDegreeTextPaint = new Paint ();
        intDegreeTextPaint.setColor (Color.BLACK);
        intDegreeTextPaint.setStyle (Paint.Style.FILL_AND_STROKE);
        intDegreeTextPaint.setTextSize (INT_DEGREE_TEXT_FONT_SIZE);
    }

    @Override
    protected void onFinishInflate ()
    {
        super.onFinishInflate ();

    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)
    {
        if (widthMeasureSpec > heightMeasureSpec) {
            setMeasuredDimension (heightMeasureSpec, heightMeasureSpec);
        }
        else {
            setMeasuredDimension (widthMeasureSpec, widthMeasureSpec);
        }
    }

    @Override
    protected void onDraw (Canvas canvas)
    {
        super.onDraw (canvas);

        // Arc
        canvas.drawArc (ARC_STROKE_WIDTH * 2,
                        ARC_STROKE_WIDTH * 2,
                        arcDiameter,
                        arcDiameter,
                        135,
                        270,
                        false,
                        arcPaint);

        // Temperature
        Rect textBounds;

        float textXPosition;
        float textYPosition;

        textBounds = new Rect ();
        intDegreeTextPaint.getTextBounds ("18.5", 0, "18.5".length (), textBounds);

        textXPosition = (getRight () /2) - (textBounds.width () / 2);
        textYPosition = (arcDiameter / 2); // - (textBounds.height () / 2);

        canvas.drawText ("18.5", textXPosition, textYPosition, intDegreeTextPaint);

        // Temperature pointer
        canvas.drawCircle (ARC_STROKE_WIDTH / 2, arcDiameter / 2, ARC_STROKE_WIDTH, arcPaint);

        ////////////////////////////////////////////////////////////////////////////////////
        // Debug
        Paint dbgPaint;
        dbgPaint = new Paint ();
        dbgPaint.setColor (Color.RED);
        dbgPaint.setStyle (Paint.Style.STROKE);
        dbgPaint.setStrokeWidth (14);

        canvas.drawRect (0, 0, this.getRight (), this.getBottom (), dbgPaint);
    }
}
