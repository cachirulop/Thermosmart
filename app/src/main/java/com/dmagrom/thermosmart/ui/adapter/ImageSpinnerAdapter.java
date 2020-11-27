package com.dmagrom.thermosmart.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.dmagrom.thermosmart.R;

import java.util.List;

public class ImageSpinnerAdapter
        extends ArrayAdapter<ImageSpinnerRow>
{
    List<ImageSpinnerRow> rows;
    private int idResource;

    public ImageSpinnerAdapter (Context context,
                                int resource,
                                int textViewResourceId,
                                List<ImageSpinnerRow> objects) {
        super(context, resource, textViewResourceId, objects);
        rows = objects;
        this.idResource = resource;
    }

    static class ViewHolder {
        ImageView icon;
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        ViewHolder  holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from (getContext ()).inflate (idResource,
                                                                       parent,
                                                                       false);

            holder.icon = (ImageView) convertView.findViewById (R.id.imgSpinnerRowImage);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageSpinnerRow currentRow = rows.get(position);
        // holder.icon.setImageDrawable(getContext().getResources().getDrawable(currentRow.getIconResourceId()));
        holder.icon.setImageResource (R.drawable.ic_humidity);

        return convertView;
    }
}
