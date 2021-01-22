package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;

public class ProgramHourCell
        extends LinearLayout
{
    private TextView txtHour;
    private ImageView imgFirstHalf;
    private ImageView imgSecondHalf;
    private DatabaseGlobals.ThermosmartProgram targetTypeFirstHalf = DatabaseGlobals.ThermosmartProgram.Moon;
    private DatabaseGlobals.ThermosmartProgram targetTypeSecondHalf = DatabaseGlobals.ThermosmartProgram.Moon;
    private int hour;

    OnProgramHourCellChangeListener changeListener;

    public interface OnProgramHourCellChangeListener {
        void onFirstHalfTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType);
        void onSecondHalfTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType);

        void onHourTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType);
    }

    public ProgramHourCell (Context context)
    {
        super (context);

        initLayout (null);
    }

    public ProgramHourCell (Context context, @Nullable AttributeSet attrs)
    {
        super (context, attrs);

        initLayout (attrs);
    }

    public ProgramHourCell (Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super (context, attrs, defStyleAttr);

        initLayout (attrs);
    }

    public ProgramHourCell (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super (context, attrs, defStyleAttr, defStyleRes);

        initLayout (attrs);
    }

    public int getHour ()
    {
        return hour;
    }

    public void setHour (int value)
    {
        hour = value;
    }

    public DatabaseGlobals.ThermosmartProgram getTargetTypeFirstHalf ()
    {
        return targetTypeFirstHalf;
    }

    public void setTargetTypeFirstHalf (DatabaseGlobals.ThermosmartProgram targetType)
    {
        this.targetTypeFirstHalf = targetType;

        updateImage ();
    }

    public DatabaseGlobals.ThermosmartProgram getTargetTypeSecondHalf ()
    {
        return targetTypeSecondHalf;
    }

    public void setTargetTypeSecondHalf (DatabaseGlobals.ThermosmartProgram targetType)
    {
        this.targetTypeSecondHalf = targetType;

        updateImage ();
    }

    public OnProgramHourCellChangeListener getChangeListener ()
    {
        return changeListener;
    }

    public void setChangeListener (OnProgramHourCellChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }

    private void initLayout (AttributeSet attributes)
    {
        View vSeparator;
        View hSeparator;
        ConstraintLayout images;
        ConstraintSet imagesConstraints;
        final int imgWidth = getDIP (56);
        final int imgHeight = getDIP (35);
        final int textHPadding = getDIP (2);
        final int textVPadding = getDIP (5);
        final int imgHPadding = getDIP (0);
        final int imgVPadding = getDIP (5);

        if (attributes != null) {
            final TypedArray attrArray;

            attrArray = getContext ().obtainStyledAttributes (attributes, R.styleable.ProgramHourCell, 0, 0);

            hour = attrArray.getInt (R.styleable.ProgramHourCell_hour, 0);
        }

        this.setBackgroundResource (R.drawable.border);
        this.setOrientation (VERTICAL);
        this.setLayoutParams (new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        // Images
        images = new ConstraintLayout (getContext ());
        images.setId (View.generateViewId ());
        images.setLayoutParams (new ConstraintLayout.LayoutParams (imgWidth, imgHeight));

        imgFirstHalf = new ImageView (getContext ());
        imgFirstHalf.setId (View.generateViewId ());
        imgFirstHalf.setPadding (imgHPadding, imgVPadding, imgHPadding, imgVPadding);
        imgFirstHalf.setImageResource (getImageResourceIdByTargetType (targetTypeFirstHalf));
        imgFirstHalf.setLayoutParams (new ConstraintLayout.LayoutParams (imgWidth / 2, ConstraintLayout.LayoutParams.MATCH_PARENT));

        vSeparator = new View (getContext ());
        vSeparator.setId (View.generateViewId ());
        vSeparator.setBackgroundColor (Color.BLACK);
        vSeparator.setLayoutParams (new ConstraintLayout.LayoutParams (getDIP (1), ConstraintLayout.LayoutParams.MATCH_PARENT));

        imgSecondHalf = new ImageView (getContext ());
        imgSecondHalf.setId (View.generateViewId ());
        imgSecondHalf.setPadding (imgHPadding, imgVPadding, imgHPadding, imgVPadding);
        imgSecondHalf.setImageResource (getImageResourceIdByTargetType (targetTypeSecondHalf));
        imgSecondHalf.setLayoutParams (new ConstraintLayout.LayoutParams (imgWidth / 2, ConstraintLayout.LayoutParams.MATCH_PARENT));

        images.addView (imgFirstHalf);
        images.addView (vSeparator);
        images.addView (imgSecondHalf);

        imagesConstraints = new ConstraintSet ();
        imagesConstraints.clone (images);

        imagesConstraints.connect (imgFirstHalf.getId (), ConstraintSet.TOP, images.getId (), ConstraintSet.TOP);
        imagesConstraints.connect (imgFirstHalf.getId (), ConstraintSet.BOTTOM, images.getId (), ConstraintSet.BOTTOM);
        imagesConstraints.connect (imgFirstHalf.getId (), ConstraintSet.START, images.getId (), ConstraintSet.START);
        imagesConstraints.connect (imgFirstHalf.getId (), ConstraintSet.END, vSeparator.getId (), ConstraintSet.START);

        imagesConstraints.connect (vSeparator.getId (), ConstraintSet.TOP, images.getId (), ConstraintSet.BOTTOM);
        imagesConstraints.connect (vSeparator.getId (), ConstraintSet.BOTTOM, images.getId (), ConstraintSet.BOTTOM);
        imagesConstraints.connect (vSeparator.getId (), ConstraintSet.START, images.getId (), ConstraintSet.START);
        imagesConstraints.connect (vSeparator.getId (), ConstraintSet.END, images.getId (), ConstraintSet.END);

        imagesConstraints.connect (imgSecondHalf.getId (), ConstraintSet.TOP, images.getId (), ConstraintSet.BOTTOM);
        imagesConstraints.connect (imgSecondHalf.getId (), ConstraintSet.BOTTOM, images.getId (), ConstraintSet.BOTTOM);
        imagesConstraints.connect (imgSecondHalf.getId (), ConstraintSet.START, vSeparator.getId (), ConstraintSet.END);
        imagesConstraints.connect (imgSecondHalf.getId (), ConstraintSet.END, images.getId (), ConstraintSet.END);

        imagesConstraints.applyTo (images);

        hSeparator = new View (getContext ());
        hSeparator.setBackgroundColor (Color.BLACK);
        hSeparator.setLayoutParams (new ConstraintLayout.LayoutParams (ConstraintLayout.LayoutParams.MATCH_PARENT, getDIP (1)));

        // Textview
        txtHour = new TextView (getContext ());
        txtHour.setLayoutParams (new LinearLayout.LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        txtHour.setPadding (textHPadding, textVPadding, textHPadding, textVPadding);
        txtHour.setText (String.format ("%s:00", hour));
        txtHour.setTextAlignment (TEXT_ALIGNMENT_CENTER);
        txtHour.setTextSize (15);

        imgFirstHalf.setOnClickListener (v -> doClickFirstHalf ());
        imgSecondHalf.setOnClickListener (v -> doClickSecondHalf ());
        txtHour.setOnClickListener (v -> doClickHourText ());
        // setOnClickListener (v -> doClick ());

        addView (images);
        addView (hSeparator);
        addView (txtHour);

        updateImage ();
    }

    private void doClickFirstHalf ()
    {
        targetTypeFirstHalf = changeTargetType (targetTypeFirstHalf);
        if (changeListener != null) {
            changeListener.onFirstHalfTargetTypeChanged (hour, targetTypeFirstHalf);
        }

        updateImage ();
    }

    private void doClickSecondHalf ()
    {
        targetTypeSecondHalf = changeTargetType (targetTypeSecondHalf);
        if (changeListener != null) {
            changeListener.onSecondHalfTargetTypeChanged (hour, targetTypeSecondHalf);
        }

        updateImage ();
    }

    private void doClickHourText ()
    {
        targetTypeFirstHalf = changeTargetType (targetTypeFirstHalf);
        targetTypeSecondHalf = targetTypeFirstHalf;
        if (changeListener != null) {
            changeListener.onHourTargetTypeChanged (hour, targetTypeFirstHalf);
        }

        updateImage ();
    }

    private DatabaseGlobals.ThermosmartProgram changeTargetType (DatabaseGlobals.ThermosmartProgram targetType)
    {
        switch (targetType) {
            case Moon:
                return DatabaseGlobals.ThermosmartProgram.Sun;

            case Sun:
                return DatabaseGlobals.ThermosmartProgram.Off;

            case Off:
            default:
                return DatabaseGlobals.ThermosmartProgram.Moon;
        }
    }

    private void updateImage ()
    {
        imgFirstHalf.setImageResource (getImageResourceIdByTargetType (targetTypeFirstHalf));
        imgSecondHalf.setImageResource (getImageResourceIdByTargetType (targetTypeSecondHalf));

        imgFirstHalf.setBackgroundResource (getBackgroundResourceIdByTargetType (targetTypeFirstHalf));
        imgSecondHalf.setBackgroundResource (getBackgroundResourceIdByTargetType (targetTypeSecondHalf));

        invalidate ();
    }

    private int getImageResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram targetType)
    {
        switch (targetType) {
            case Sun:
                return R.drawable.ic_sun;
            case Moon:
                return R.drawable.ic_moon;
            default:
                return R.drawable.ic_turn_on;
        }
    }

    private int getBackgroundResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram targetType)
    {
        switch (targetType) {
            case Sun:
                return R.drawable.border_sun;
            case Moon:
                return R.drawable.border_night;
            default:
                return R.drawable.border;
        }
    }

    private int getDIP (int pixels)
    {
        float density;

        density = getContext ().getResources ().getDisplayMetrics ().density;

        return (int) (density * pixels);
    }
}
