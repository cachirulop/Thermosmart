package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ArcSeekBar
        extends View
{
    private static final String LOG_TAG = ArcSeekBar.class.getName ();

    private static final int ARC_STROKE_WIDTH = 25;
    private static final int INT_DEGREE_TEXT_FONT_SIZE = 250;

    private static final int ARC_START_ANGLE = 135;    // 90 + 45
    private static final int ARC_END_ANGLE = 405;      // 360 + 45

    public static final float MIN_TEMPERATURE = 0;
    public static final float MAX_TEMPERATURE = 50;

    private float degrees = 0.0f;

    private Paint arcPaint;
    private float arcDiameter;

    private Paint circlePaint;
    private GestureDetector circleGestureDetector;

    private Paint intDegreeTextPaint;


    public ArcSeekBar (Context context)
    {
        super (context);

        init ();
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs)
    {
        super (context, attrs);

        init ();
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);

        init ();
    }

    public ArcSeekBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super (context, attrs, defStyleAttr, defStyleRes);

        init ();
    }

    public void setDegrees (float value)
    {
        degrees = value;
        invalidate ();
    }

    public float getDegrees ()
    {
        return degrees;
    }

    private void init ()
    {
        arcPaint = new Paint ();
        arcPaint.setColor (Color.RED);
        arcPaint.setStyle (Paint.Style.STROKE);
        arcPaint.setStrokeWidth (ARC_STROKE_WIDTH);

        circlePaint = new Paint ();
        circlePaint.setColor (Color.BLUE);
        circlePaint.setStyle (Paint.Style.FILL_AND_STROKE);
        circlePaint.setStrokeWidth (ARC_STROKE_WIDTH);

        intDegreeTextPaint = new Paint ();
        intDegreeTextPaint.setColor (Color.BLACK);
        intDegreeTextPaint.setStyle (Paint.Style.FILL_AND_STROKE);
        intDegreeTextPaint.setTextSize (INT_DEGREE_TEXT_FONT_SIZE);

        circleGestureDetector = new GestureDetector (getContext (), new TemperaturePointerListener ());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return circleGestureDetector.onTouchEvent(event);
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

        arcPaint.setShader (gradient);
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
                        ARC_START_ANGLE,
                        ARC_END_ANGLE - ARC_START_ANGLE,
                        false,
                        arcPaint);

        // Temperature
        Rect textBounds;
        String degreesString;

        float textXPosition;
        float textYPosition;

        degreesString = String.format ("%.1fº", degrees);

        textBounds = new Rect ();
        intDegreeTextPaint.getTextBounds (degreesString, 0, degreesString.length (), textBounds);

        textXPosition = (getRight () /2) - (textBounds.width () / 2);
        textYPosition = (arcDiameter / 2); // - (textBounds.height () / 2);

        canvas.drawText (degreesString, textXPosition, textYPosition, intDegreeTextPaint);

        // Temperature pointer
        Point circlePosition;

        circlePosition = calculateCirclePosition ();
        canvas.drawCircle (circlePosition.x, circlePosition.y, ARC_STROKE_WIDTH, circlePaint);

        ////////////////////////////////////////////////////////////////////////////////////
        // Debug
        Paint dbgPaint;
        dbgPaint = new Paint ();
        dbgPaint.setColor (Color.RED);
        dbgPaint.setStyle (Paint.Style.STROKE);
        dbgPaint.setStrokeWidth (14);

        canvas.drawRect (0, 0, this.getRight (), this.getBottom (), dbgPaint);
    }

    private Point calculateCirclePosition ()
    {
        int x;
        int y;
        double currentAngle;
        int radius;
        final float transformationConstant = ((ARC_START_ANGLE - ARC_END_ANGLE) / (MIN_TEMPERATURE - MAX_TEMPERATURE));

        radius = (int) ((arcDiameter - (2 * ARC_STROKE_WIDTH)) / 2);

        // MIN_TEMPERATURE --> ARC_START_ANGLE
        // MAX_TEMPERATURE --> ARC_END_ANGLE
        // degrees         --> currentAngle
        // Linear transformation https://es.wikiversity.org/wiki/Transformaci%C3%B3n_lineal_de_intervalos
        currentAngle = Math.toRadians ((transformationConstant * (degrees - MIN_TEMPERATURE)) + ARC_START_ANGLE);

        x = ((int) Math.round (Math.cos (currentAngle) * radius) + radius + (2 * ARC_STROKE_WIDTH));
        y = ((int) Math.round (Math.sin (currentAngle) * radius) + radius + (2 * ARC_STROKE_WIDTH));

        return new Point(x, y);
    }


/*    private int calculateRectXInArc (Point referencePoint)
    {
        int result;
        Point center;
        double radius;

        radius = arcDiameter / 2;

        center = new Point ((int) (ARC_STROKE_WIDTH * 2 + radius), (int) (ARC_STROKE_WIDTH * 2 + radius));

        // (y - y1) / (y2 - y1) = (x - x1) / (x2 - x1)
        ()
    }*/

    private float calculateDegreesFromPoint (Point point)
    {
        int radius;
        double angle;
        double asinPoint;
        final float transformationConstant = ((MIN_TEMPERATURE - MAX_TEMPERATURE) / (ARC_START_ANGLE - ARC_END_ANGLE));

        radius = (int) ((arcDiameter - (2 * ARC_STROKE_WIDTH)) / 2);

        asinPoint =  (point.x - (radius + (2 * ARC_STROKE_WIDTH))) / ((double) radius);
        angle = Math.toDegrees (Math.acos (asinPoint));

        Log.d (LOG_TAG, "*************** calculate: " + point.y + "-.-" + asinPoint + "-.-" + angle + "-.-" + radius);

        // Linear transformation
        return (float) (transformationConstant * (angle - ARC_START_ANGLE) + MIN_TEMPERATURE);
    }

    class TemperaturePointerListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown (MotionEvent e) {
            Point circlePosition;
            Rect circleRect;

            circlePosition = calculateCirclePosition ();

            circleRect = new Rect ();
            circleRect.left = circlePosition.x - (ARC_STROKE_WIDTH * 2);
            circleRect.top = circlePosition.y - (ARC_STROKE_WIDTH * 2);
            circleRect.right = circleRect.left + (ARC_STROKE_WIDTH * 4);
            circleRect.bottom = circleRect.top + (ARC_STROKE_WIDTH * 4);

            if (circleRect.contains ((int) e.getX (), (int) e.getY ())) {
                Log.d (LOG_TAG, "**************************** Pulsado!!");
                return true;
            }

            return false;
        }

        @Override
        public boolean onScroll (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            Log.d (LOG_TAG, "**************************** onScroll!!: " + distanceX + "-.-" + distanceY + "-.-" + e2.getY ());

            float degrees;

            degrees = calculateDegreesFromPoint (new Point (Math.round (e2.getX ()), Math.round (e2.getY ())));

            Log.d (LOG_TAG, "*************** degrees: " + degrees);

            return false;
        }

        @Override
        public void onShowPress (MotionEvent motionEvent)
        {
            Log.d (LOG_TAG, "**************************** onShowPress!!");
        }

        @Override
        public boolean onSingleTapUp (MotionEvent motionEvent)
        {
            Log.d (LOG_TAG, "**************************** onSingleTapUp!!");
            return false;
        }


        @Override
        public void onLongPress (MotionEvent motionEvent)
        {
            Log.d (LOG_TAG, "**************************** onLongPress!!");
        }

        @Override
        public boolean onFling (MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1)
        {
            Log.d (LOG_TAG, "**************************** onFling!!");
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed (MotionEvent e)
        {
            return true;
        }
    }
}
