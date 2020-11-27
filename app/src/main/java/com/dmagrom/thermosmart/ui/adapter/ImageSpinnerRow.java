package com.dmagrom.thermosmart.ui.adapter;

import android.graphics.drawable.Drawable;

public class ImageSpinnerRow
{
    private int id;
    private int drawable;

    public ImageSpinnerRow (int id, int drawable)
    {
        this.id = id;
        this.drawable = drawable;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public int getDrawable ()
    {
        return drawable;
    }

    public void setDrawable (int drawable)
    {
        this.drawable = drawable;
    }
}
