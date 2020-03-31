/*
package com.dmagrom.thermosmart.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;

import java.time.Duration;
import java.time.LocalTime;

public class ProgramBar extends View
{
    private Paint rectPaint;
    private Paint hoursPaint;
    private Paint currentSelectionPaint;
    private Paint intervalPaint;

    private float barHeight;
    private float margin;
    private float textMargin;

    private Point initialTouchEvent = null;
    private Point currentMovementPosition = null;

    private Program program;

    public ProgramBar (Context context)
    {
        super (context);

        init (null);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs)
    {
        super (context, attrs);

        init (attrs);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);

        init (attrs);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super (context, attrs, defStyleAttr, defStyleRes);

        init (attrs);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        // Log.d (ProgramBar.class.getName (), "onTouchEvent: " + event.toString ());

        switch (event.getAction ()) {
            case MotionEvent.ACTION_DOWN:
                if (isPointInsideBar (event.getX (), event.getY())) {
                    initialTouchEvent = new Point ((int) event.getX (), (int) event.getY ());
                    currentMovementPosition = new Point ((int) event.getX (), (int) event.getY ());
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (currentMovementPosition != null && !positionXHasInterval (event.getX ())) {
                    float realX;
                    int currentMinute;

                    if (event.getX () < margin) {
                        realX = margin;
                    }
                    else if (event.getX () > (getRight () - margin)) {
                        realX = (getRight () - margin);
                    }
                    else {
                        realX = event.getX ();
                    }

                    currentMinute = getMinuteFromPosition (realX);
                    if (currentMinute % 5 == 0) {
                        currentMovementPosition.set ((int) realX, (int) event.getY ());
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                ProgramInterval interval;

                if (initialTouchEvent != null && currentMovementPosition != null) {
                    interval = new ProgramInterval ();
                    interval.setInitialMinute (getMinuteFromPosition (initialTouchEvent.x));
                    interval.setFinalMinute (getMinuteFromPosition (currentMovementPosition.x));
                    interval.setTargetType (DatabaseGlobals.ThermostatTargetType.Night);

                    program.addInterval (interval);
                }

                initialTouchEvent = null;
                currentMovementPosition = null;

                invalidate ();

                break;
        }

        invalidate ();

        return true;
        // return super.onTouchEvent (event);
    }

    private void init (AttributeSet attrs)
    {
        program = new Program ();

        rectPaint = new Paint ();
        // rectPaint.setAntiAlias(true);
        // rectPaint.setDither(true);
        rectPaint.setColor(Color.RED);
        rectPaint.setStrokeWidth(4);
        rectPaint.setStyle(Paint.Style.STROKE);

        hoursPaint = new Paint ();
        // hoursPaint.setAntiAlias(true);
        // hoursPaint.setDither(true);
        hoursPaint.setColor(Color.BLUE);
        hoursPaint.setStrokeWidth(4);
        hoursPaint.setStyle(Paint.Style.STROKE);
        hoursPaint.setTextSize (35);

        currentSelectionPaint = new Paint ();
        // currentSelectionPaint.setAntiAlias(true);
        // currentSelectionPaint.setDither(true);
        currentSelectionPaint.setColor(Color.BLUE);
        currentSelectionPaint.setStrokeWidth(4);
        currentSelectionPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        currentSelectionPaint.setTextSize (35);

        intervalPaint = new Paint ();
        // intervalPaint.setAntiAlias(true);
        // intervalPaint.setDither(true);
        intervalPaint.setColor(Color.BLUE);
        intervalPaint.setStrokeWidth(4);
        intervalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        intervalPaint.setTextSize (35);

        // TODO: read attributes or calculate in onMeasure
        barHeight = 80;
        margin = 75;
        textMargin = 20;
    }

    @SuppressLint ("MissingSuperCall")
    @Override
    public void draw (Canvas canvas)
    {
        Rect textBounds;
        float textY;
        float textX;
        float lineX;
        float startLineY;
        float endLineY;

        textBounds = new Rect ();
        hoursPaint.getTextBounds ("0", 0, 1, textBounds);

        startLineY = margin + (barHeight / 2);
        endLineY = barHeight + margin + textMargin - (textMargin / 2);
        textY = barHeight + margin + textMargin + textBounds.height ();

        // lineX = margin;
        lineX = getPositionFromHour (0);
        textX = lineX - (textBounds.width () / 2);

        canvas.drawText ("0", textX, textY, hoursPaint);
        canvas.drawLine (lineX, startLineY, lineX, endLineY, hoursPaint);

        hoursPaint.getTextBounds ("12", 0, 2, textBounds);
        lineX = getPositionFromHour (12);
        //lineX = ((getRight () - margin) / 2);
        textX = lineX - (textBounds.width () / 2);

        canvas.drawText ("12", textX, textY, hoursPaint);
        canvas.drawLine (lineX, startLineY, lineX, endLineY, hoursPaint);

        hoursPaint.getTextBounds ("24", 0, 2, textBounds);
        //lineX = (getRight () - margin);
        lineX = getPositionFromHour (24);
        textX = lineX - (textBounds.width () / 2);

        canvas.drawText ("24", textX, textY, hoursPaint);
        canvas.drawLine (lineX, startLineY, lineX, endLineY, hoursPaint);

        // Current selection
        ///////////////////////////////////////////////////////////////////////////////////////
        if (initialTouchEvent != null && currentMovementPosition != null) {
            String time;

            time = getTimeFormattedFromPosition (initialTouchEvent.x);
            currentSelectionPaint.getTextBounds (time, 0, time.length (), textBounds);

            canvas.drawText (time,
                             initialTouchEvent.x - (textBounds.width () / 2),
                             margin - textMargin,
                             currentSelectionPaint);

            time = getTimeFormattedFromPosition (currentMovementPosition.x);
            currentSelectionPaint.getTextBounds (time, 0, time.length (), textBounds);
            canvas.drawText (time,
                             currentMovementPosition.x - (textBounds.width () / 2),
                             margin - textMargin,
                             currentSelectionPaint);

            canvas.drawRect (initialTouchEvent.x,
                             margin,
                             currentMovementPosition.x,
                             margin + barHeight,
                             currentSelectionPaint);
        }

        // Intervals
        ///////////////////////////////////////////////////////////////////////////////////////

        for (ProgramInterval current : program.getIntervals ()) {
            float leftX;
            float rightX;

            switch (current.getTargetType ()) {
                case Sun:
                    intervalPaint.setColor (Color.rgb (200, 100, 100));
                    break;

                case Night:
                    intervalPaint.setColor (Color.CYAN);
                    break;

                case Comfort:
                    intervalPaint.setColor (Color.GREEN);
                    break;

                default:
                    intervalPaint.setColor (Color.BLACK);
                    break;
            }

            leftX = getPositionFromMinute (current.getInitialMinute ());
            rightX = getPositionFromMinute (current.getFinalMinute ());

            canvas.drawRect (leftX, margin, rightX, margin + barHeight, intervalPaint);
        }


        // Bar
        ///////////////////////////////////////////////////////////////////////////////////////
        canvas.drawRect (margin,
                         margin,
                         getRight () - margin,
                         barHeight + margin,
                         rectPaint);
    }

    public String getTimeFormattedFromPosition (float positionX) {
        return LocalTime.MIN.plus (Duration.ofMinutes (getMinuteFromPosition (positionX))).toString ();
    }

    // https://es.wikiversity.org/wiki/Transformaci%C3%B3n_lineal_de_intervalos
    public int getMinuteFromPosition (float positionX) {
        return (int) (((0 - 1440) / (margin - (getRight () - margin))) * (positionX - margin));
    }

    public float getPositionFromHour (float hour) {
        return getPositionFromMinute ((int)(hour * 60));
    }

    // https://es.wikiversity.org/wiki/Transformaci%C3%B3n_lineal_de_intervalos
    public float getPositionFromMinute (int minute) {
        return (((margin - (getRight () - margin)) / (0 - 1440)) * minute) + margin;
    }

    public boolean isPointInsideBar (float x, float y) {
        return (x >= margin && x <= (getRight () - margin) && y >= margin && y <= (barHeight + margin));
    }

    public boolean positionXHasInterval (float positionX) {
        int minute;
        boolean result;

        minute= getMinuteFromPosition (positionX);

        result = program.getIntervals ()
                    .stream ()
                    .anyMatch (i -> (i.getInitialMinute () < minute && i.getFinalMinute () > minute));

        Log.d (ProgramBar.class.getName (), "************ " + result + "-.-" + minute + "-.-" + program.getIntervals ().toString ());

        return result;
    }
}
*/
