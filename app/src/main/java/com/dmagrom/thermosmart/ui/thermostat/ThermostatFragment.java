package com.dmagrom.thermosmart.ui.thermostat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ThermostatViewModel;
import com.dmagrom.thermosmart.ui.adapter.ImageSpinnerRow;
import com.dmagrom.thermosmart.widget.CircularSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThermostatFragment
        extends Fragment
{
    private ThermostatViewModel viewModel;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        View root;
        final CircularSeekBar seekBar;
        final TextView txtCurrentTemperature;
        final TextView txtTargetTemperature;
        ViewModelProvider provider;

        provider = new ViewModelProvider (this);

        viewModel = provider.get (ThermostatViewModel.class);
        root = inflater.inflate (R.layout.fragment_thermostat, container, false);

        seekBar = root.findViewById (R.id.seek_circular_bar_degrees);
        txtCurrentTemperature = root.findViewById (R.id.txt_current_temperature);
        txtTargetTemperature = root.findViewById (R.id.txt_target_temperature);

        seekBar.setOnSeekBarChangeListener (new CircularSeekBar.OnCircularSeekBarChangeListener ()
        {
            // Round to the nearest .5 value
            private float roundValue (float value) {
                return Math.round(value * 2f) / 2.0f;
            }

            @Override
            public void onProgressChanged (CircularSeekBar circularSeekBar, float progress, boolean fromUser)
            {
                txtTargetTemperature.setText (String.format ("%.1fº", roundValue (progress)));
            }


            @Override
            public void onStopTrackingTouch (CircularSeekBar seekBar)
            {
                seekBar.setProgress (roundValue (seekBar.getProgress ()));
                viewModel.setCurrentTargetTemperature (seekBar.getProgress ());
            }

            @Override
            public void onStartTrackingTouch (CircularSeekBar seekBar)
            {

            }
        });

        viewModel.getCurrentTemperature ().observe (getViewLifecycleOwner (), new Observer<Float> ()
        {
            @Override
            public void onChanged (@Nullable Float s)
            {
                txtCurrentTemperature.setText (String.format ("%.1fº", s.floatValue ()));
                seekBar.setCurrentValue (s.floatValue ());
            }
        });

        viewModel.getCurrentTargetTemperature ().observe (getViewLifecycleOwner (), new Observer<Float> ()
        {
            @Override
            public void onChanged (@Nullable Float s)
            {
                txtTargetTemperature.setText (String.format ("%.1fº", s.floatValue ()));
                seekBar.setProgress (s.floatValue ());
            }
        });

        int[] targetImages ={R.drawable.ic_humidity,R.drawable.ic_sun,R.drawable.ic_night};

        List<ImageSpinnerRow> options = new ArrayList<> ();
        ArrayList<Map<String, Integer>> arrayList=new ArrayList<>();

        options.add(new ImageSpinnerRow(1, R.drawable.ic_night));
        options.add(new ImageSpinnerRow(2, R.drawable.ic_sun));
        options.add(new ImageSpinnerRow(3, R.drawable.ic_humidity));

        for (int i = 0; i < targetImages.length; i++) {
            Map<String, Integer> map;

            map = new HashMap<> ();
            map.put ("targetImage", targetImages[i]);

            arrayList.add (map);
        }

        Spinner targetTypeSpinner;
        SimpleAdapter tmpAdapter;

        targetTypeSpinner = root.findViewById (R.id.spnr_target_type);
        tmpAdapter = new SimpleAdapter (getContext (), arrayList, R.layout.spinner_row, new String [] { "targetImage"}, new int [] { R.id.imgSpinnerRowImage });
        targetTypeSpinner.setAdapter (tmpAdapter);

        targetTypeSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ()
        {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText (getContext (), targetImages[position], Toast.LENGTH_LONG).show ();//show the selected image in toast according to position
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent)
            {

            }
        });

        return root;
    }
}