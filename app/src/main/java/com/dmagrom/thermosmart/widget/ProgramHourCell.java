package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;

public class ProgramHourCell
        extends LinearLayout
{
    private TextView                           txtHour;
    private ImageView                          imgProgramTarget;
    private ImageView                          imgProgramTarget1;
    private DatabaseGlobals.ThermosmartProgram targetType = DatabaseGlobals.ThermosmartProgram.Moon;
    private String                             hour;

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

    public String getHour () {
        return hour;
    }

    public void setHour (String value) {
        hour = value;
    }

    public DatabaseGlobals.ThermosmartProgram getTargetType ()
    {
        return targetType;
    }

    public void setTargetType (DatabaseGlobals.ThermosmartProgram targetType)
    {
        this.targetType = targetType;
    }

    private void initLayout (AttributeSet attributes)
    {
        LinearLayout.LayoutParams params;
        LinearLayout images;

        if (attributes != null) {
            final TypedArray attrArray;

            attrArray = getContext ().obtainStyledAttributes (attributes, R.styleable.ProgramHourCell, 0, 0);

            hour = String.format ("%02d", attrArray.getInt (R.styleable.ProgramHourCell_hour, 0));
        }
        else {
            hour = "01";
        }

        // LinearLayout (this) layoutparams
        // this.setBackground (border);
        this.setBackgroundResource (R.drawable.border);
        this.setOrientation (VERTICAL);
        this.setPadding (getDIP (5), getDIP (5), getDIP (5), getDIP (5));

        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //params.setMargins (getDIP (5), getDIP (5), getDIP (5), getDIP (5));
        this.setLayoutParams (params);

        // Textview
        txtHour = new TextView (getContext ());
        txtHour.setText (hour + ":00");
        txtHour.setTextAlignment (TEXT_ALIGNMENT_CENTER);
        txtHour.setTextSize (12);
        // txtHour.setPadding (2, 2, 2, 2);

        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins (getDIP (2),getDIP (2),getDIP (2),getDIP (2));
        txtHour.setLayoutParams (params);

        // Images
        images = new LinearLayout (getContext ());
        images.setOrientation (HORIZONTAL);
        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        images.setLayoutParams (params);

        imgProgramTarget = new ImageView (getContext ());
        imgProgramTarget.setImageResource (getImageResourceIdByTargetType (targetType));
        imgProgramTarget.setTextAlignment (TEXT_ALIGNMENT_VIEW_START);
        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, getDIP (15));
        imgProgramTarget.setLayoutParams (params);

        imgProgramTarget1 = new ImageView (getContext ());
        imgProgramTarget1.setImageResource (getImageResourceIdByTargetType (targetType));
        imgProgramTarget1.setTextAlignment (TEXT_ALIGNMENT_VIEW_END);
        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, getDIP (15));
        imgProgramTarget1.setLayoutParams (params);

        images.addView (imgProgramTarget);
        images.addView (imgProgramTarget1);

        updateImage ();

        addView (images);
        addView (txtHour);

        setOnClickListener (new OnClickListener ()
        {
            @Override
            public void onClick (View v)
            {
                doClick ();
            }
        });
    }

    private void doClick ()
    {
        if (targetType == DatabaseGlobals.ThermosmartProgram.Moon) {
            targetType = DatabaseGlobals.ThermosmartProgram.Sun;
        }
        else {
            targetType = DatabaseGlobals.ThermosmartProgram.Moon;
        }

        updateImage ();
    }

    private void updateImage () {
        // imgProgramTarget.setImageResource (getImageResourceIdByTargetType (targetType));
        imgProgramTarget.setImageResource (getImageResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram.Sun));
        imgProgramTarget1.setImageResource (getImageResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram.Moon));

        this.setBackgroundResource (getBackgroundResourceIdByTargetType (targetType));

        invalidate ();
    }

    private int getImageResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram targetType) {
        switch (targetType) {
            case Sun: return R.drawable.ic_sun;
            case Moon: return R.drawable.ic_night;
            default: return R.drawable.ic_turn_on;
        }
    }

    private int getBackgroundResourceIdByTargetType (DatabaseGlobals.ThermosmartProgram targetType) {
        switch (targetType) {
            case Sun: return R.drawable.border_sun;
            case Moon: return R.drawable.border_night;
            default: return R.drawable.border;
        }
    }

    private int getDIP (int pixels) {
        float density;

        density = getContext ().getResources().getDisplayMetrics().density;

        return (int) (density * pixels);
    }

}
