package com.dmagrom.thermosmart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ThermostatViewModel;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;

public class ProgramHourCell
        extends LinearLayout
{
    private TextView txtHour;
    private ImageView imgProgramTarget;
    private DatabaseGlobals.ThermostatTargetType targetType = DatabaseGlobals.ThermostatTargetType.None;
    private String hour;

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

    private void initLayout (AttributeSet attributes)
    {
        LinearLayout.LayoutParams params;

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
        txtHour.setText (hour);
        txtHour.setTextAlignment (TEXT_ALIGNMENT_CENTER);
        txtHour.setTextSize (12);
        // txtHour.setPadding (2, 2, 2, 2);

        params = new LinearLayout.LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins (getDIP (2),getDIP (2),getDIP (2),getDIP (2));
        txtHour.setLayoutParams (params);

        // Image
        imgProgramTarget = new ImageView (getContext ());
        imgProgramTarget.setImageResource (getImageResourceIdByTargetType (targetType));
        imgProgramTarget.setTextAlignment (TEXT_ALIGNMENT_CENTER);

        params = new LinearLayout.LayoutParams (LayoutParams.MATCH_PARENT, getDIP (25));
        imgProgramTarget.setLayoutParams (params);

        addView (imgProgramTarget);
        addView (txtHour);
    }

    @Override
    public boolean onTouchEvent (MotionEvent event)
    {
        switch (event.getAction ()) {
            case MotionEvent.ACTION_DOWN:
                if (targetType == DatabaseGlobals.ThermostatTargetType.Night) {
                    targetType = DatabaseGlobals.ThermostatTargetType.Sun;
                }
                else {
                    targetType = DatabaseGlobals.ThermostatTargetType.Night;
                }

                imgProgramTarget.setImageResource (getImageResourceIdByTargetType (targetType));
                this.setBackgroundResource (getBackgroundResourceIdByTargetType (targetType));

                invalidate ();
                break;
        }

        return true;
    }

    private int getImageResourceIdByTargetType (DatabaseGlobals.ThermostatTargetType targetType) {
        switch (targetType) {
            case Sun: return R.drawable.ic_sun;
            case Night: return R.drawable.ic_night;
            default: return R.drawable.ic_turn_on;
        }
    }

    private int getBackgroundResourceIdByTargetType (DatabaseGlobals.ThermostatTargetType targetType) {
        switch (targetType) {
            case Sun: return R.drawable.border_sun;
            case Night: return R.drawable.border_night;
            default: return R.drawable.border;
        }
    }

    private int getDIP (int pixels) {
        float density;

        density = getContext ().getResources().getDisplayMetrics().density;

        return (int) (density * pixels);
    }

}
