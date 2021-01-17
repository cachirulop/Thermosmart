package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;

import java.util.ArrayList;
import java.util.List;

public class ProgramBar
        extends View
{
    private static final int MARGIN = 5;
    private static final int TODAY_PROGRAM_LENGTH = (24 * 4);

    private Paint hourLinePaint;
    private Paint sunProgramPaint;
    private Paint moonProgramPaint;
    private Paint manualProgramPaint;

    private int hourLineColor;
    private int sunProgramColor;
    private int moonProgramColor;
    private int manualProgramColor;

    private List<DatabaseGlobals.ThermosmartProgram> todayProgram;

    public ProgramBar (Context context)
    {
        super (context);

        init (null, 0, 0);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs)
    {
        super (context, attrs);

        init (attrs, 0, 0);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);

        init (attrs, defStyleAttr, 0);
    }

    public ProgramBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super (context, attrs, defStyleAttr, defStyleRes);

        init (attrs, defStyleAttr, defStyleRes);
    }

    public int getHourLineColor ()
    {
        return hourLineColor;
    }

    public void setHourLineColor (int hourLineColor)
    {
        this.hourLineColor = hourLineColor;
    }

    public int getSunProgramColor ()
    {
        return sunProgramColor;
    }

    public void setSunProgramColor (int sunProgramColor)
    {
        this.sunProgramColor = sunProgramColor;
    }

    public int getMoonProgramColor ()
    {
        return moonProgramColor;
    }

    public void setMoonProgramColor (int moonProgramColor)
    {
        this.moonProgramColor = moonProgramColor;
    }

    public int getManualProgramColor ()
    {
        return manualProgramColor;
    }

    public void setManualProgramColor (int manualProgramColor)
    {
        this.manualProgramColor = manualProgramColor;
    }

    private void init (AttributeSet attrs, int defAttr, int defRes)
    {
        todayProgram = new ArrayList<> ();
        for (int i = 0; i < TODAY_PROGRAM_LENGTH; i++) {
            if (i < 70) {
                todayProgram.add (DatabaseGlobals.ThermosmartProgram.Moon);
            }
            else {
                todayProgram.add (DatabaseGlobals.ThermosmartProgram.Sun);
            }
        }

        if (attrs != null) {
            final TypedArray attrArray;

            attrArray = getContext ().obtainStyledAttributes (attrs, R.styleable.ProgramBar, defAttr, defRes);

            hourLineColor = attrArray.getColor (R.styleable.ProgramBar_hour_line_color, Color.GRAY);
            sunProgramColor = attrArray.getColor (R.styleable.ProgramBar_sun_program_color, Color.BLUE);
            moonProgramColor = attrArray.getColor (R.styleable.ProgramBar_moon_program_color, Color.BLUE);
            manualProgramColor = attrArray.getColor (R.styleable.ProgramBar_hour_line_color, Color.YELLOW);
        }
        else {
            hourLineColor = Color.GRAY;
            sunProgramColor = Color.BLUE;
            moonProgramColor = Color.BLUE;
            manualProgramColor = Color.YELLOW;
        }


        hourLinePaint = new Paint ();
        hourLinePaint.setAntiAlias(true);
        hourLinePaint.setDither(true);
        hourLinePaint.setColor (hourLineColor);
        hourLinePaint.setStrokeWidth (4);
        hourLinePaint.setStyle (Paint.Style.FILL);
        hourLinePaint.setTextSize (20);

        sunProgramPaint = new Paint ();
        sunProgramPaint.setAntiAlias(false);
        sunProgramPaint.setDither(false);
        sunProgramPaint.setColor (sunProgramColor);
        sunProgramPaint.setStrokeWidth (1);
        sunProgramPaint.setStyle (Paint.Style.FILL);

        moonProgramPaint = new Paint ();
        moonProgramPaint.setAntiAlias(false);
        moonProgramPaint.setDither(false);
        moonProgramPaint.setColor (moonProgramColor);
        moonProgramPaint.setStrokeWidth (1);
        moonProgramPaint.setStyle (Paint.Style.FILL);

        manualProgramPaint = new Paint ();
        manualProgramPaint.setAntiAlias(false);
        manualProgramPaint.setDither(false);
        manualProgramPaint.setColor (manualProgramColor);
        manualProgramPaint.setStrokeWidth (1);
        manualProgramPaint.setStyle (Paint.Style.FILL);
    }

    public List<DatabaseGlobals.ThermosmartProgram> getTodayProgram ()
    {
        return todayProgram;
    }

    public void setTodayProgram (List<DatabaseGlobals.ThermosmartProgram> todayProgram)
    {
        this.todayProgram = todayProgram;
    }

    @Override
    public void draw (Canvas canvas)
    {
        Rect textBounds;
        float startLineX;
        float endLineX;
        float lineY;
        float lineWidth;

        super.draw (canvas);

        //canvas.drawLine (0, 0, getWidth (), getHeight (), hourLinePaint);

        textBounds = new Rect ();
        hourLinePaint.getTextBounds ("00:00",
                                     0,
                                     "00:00".length (),
                                     textBounds);

        // canvas.drawText ("00:00", MARGIN, getHeight () - MARGIN, hourLinePaint);
        startLineX = MARGIN + (textBounds.width () / 2);
        endLineX = getWidth () - (MARGIN + (textBounds.width () / 2));
        lineWidth = endLineX - startLineX;
        lineY = getHeight () - (MARGIN + textBounds.height () + MARGIN);

        for (int quarter = 0; quarter < TODAY_PROGRAM_LENGTH; quarter++) {
            float quarterStartX;
            float quarterEndX;

            quarterStartX = (quarter * (lineWidth / TODAY_PROGRAM_LENGTH)) + startLineX;
            quarterEndX = quarterStartX + (lineWidth / TODAY_PROGRAM_LENGTH);

            canvas.drawRect (quarterStartX, lineY - 40, quarterEndX, lineY, moonProgramPaint);
        }

        canvas.drawLine (startLineX, (lineY - (hourLinePaint.getStrokeWidth () / 2)), endLineX, (lineY - (hourLinePaint.getStrokeWidth () / 2)), hourLinePaint);

        for (int hour = 0; hour < 25; hour++) {
            float hourX;

            hourX = (hour * (lineWidth / 24)) + startLineX;

            if (hour % 6 == 0) {
                canvas.drawLine (hourX, lineY - 30, hourX, lineY, hourLinePaint);
                canvas.drawText (String.format ("%02d:00", hour),
                                 hourX - (textBounds.width () / 2),
                                 getHeight () - MARGIN,
                                 hourLinePaint);
            }
            else {
                canvas.drawLine (hourX, lineY - 10, hourX, lineY, hourLinePaint);
            }
        }
    }


}
