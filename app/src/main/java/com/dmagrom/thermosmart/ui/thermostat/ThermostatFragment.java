package com.dmagrom.thermosmart.ui.thermostat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ThermostatViewModel;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.widget.CircularSeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ThermostatFragment
        extends Fragment
{
    private ThermostatViewModel viewModel;
    View root;

    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate (R.layout.fragment_thermostat, container, false);

        initViewModel ();
        initSeekBarDegrees ();
        initTargetTypeSpiner ();

        return root;
    }

    private void initViewModel ()
    {
        ViewModelProvider provider;

        provider = new ViewModelProvider (this);
        viewModel = provider.get (ThermostatViewModel.class);
    }

    private void initSeekBarDegrees ()
    {
        final CircularSeekBar seekBar;
        final TextView        txtCurrentTemperature;
        final TextView        txtTargetTemperature;
        final TextView        txtCurrentHumidity;

        seekBar = root.findViewById (R.id.seek_circular_bar_degrees);
        txtCurrentTemperature = root.findViewById (R.id.txt_current_temperature);
        txtTargetTemperature = root.findViewById (R.id.txt_target_temperature);
        txtCurrentHumidity = root.findViewById (R.id.txt_current_humidity);

        seekBar.setOnSeekBarChangeListener (new CircularSeekBar.OnCircularSeekBarChangeListener ()
        {
            @Override
            public void onProgressChanged (CircularSeekBar circularSeekBar, float progress, boolean fromUser)
            {
                txtTargetTemperature.setText (String.format ("%.1fº", roundValue (progress)));
            }

            // Round to the nearest .5 value
            private float roundValue (float value)
            {
                return Math.round (value * 2f) / 2.0f;
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

        viewModel.getCurrentTemperature ()
                 .observe (getViewLifecycleOwner (), new Observer<Float> ()
                 {
                     @Override
                     public void onChanged (@Nullable Float s)
                     {
                         txtCurrentTemperature.setText (String.format ("%.1fº", s.floatValue ()));
                         seekBar.setCurrentValue (s.floatValue ());
                     }
                 });

        viewModel.getCurrentTargetTemperature ()
                 .observe (getViewLifecycleOwner (), new Observer<Float> ()
                 {
                     @Override
                     public void onChanged (@Nullable Float s)
                     {
                         txtTargetTemperature.setText (String.format ("%.1fº", s.floatValue ()));
                         seekBar.setProgress (s.floatValue ());
                     }
                 });

        viewModel.getCurrentHumidity ()
                 .observe (getViewLifecycleOwner (), new Observer<Float> ()
                 {
                     @Override
                     public void onChanged (@Nullable Float s)
                     {
                         txtCurrentHumidity.setText (String.format ("%.1f", s.floatValue ()));
                     }
                 });

        viewModel.getTargetType ()
                 .observe (getViewLifecycleOwner (), new Observer<DatabaseGlobals.ThermosmartProgram> ()
                 {
                     @Override
                     public void onChanged (@Nullable DatabaseGlobals.ThermosmartProgram s)
                     {
                         initTargetTypeSpiner ();
                     }
                 });
    }

    private void initTargetTypeSpiner ()
    {
        ArrayList<Map<String, Integer>> arrayList = new ArrayList<> ();
        Spinner                         targetTypeSpinner;
        SimpleAdapter                   tmpAdapter;
        DatabaseGlobals.ThermosmartProgram targetType;

        targetType = viewModel.getTargetType ().getValue ();
        arrayList.add (createImageMap (targetType.getImageId ()));

        switch (targetType) {
            case None:
            case Off:
                arrayList.add (createImageMap (R.drawable.ic_manual_sun));
                arrayList.add (createImageMap (R.drawable.ic_manual_moon));
                arrayList.add (createImageMap (R.drawable.ic_turn_on));
                break;

            case Holidays:
                arrayList.add (createImageMap (R.drawable.ic_manual_sun));
                arrayList.add (createImageMap (R.drawable.ic_manual_moon));
                arrayList.add (createImageMap (R.drawable.ic_plane_delete));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;

            case Manual:
                arrayList.add (createImageMap (R.drawable.ic_manual_sun));
                arrayList.add (createImageMap (R.drawable.ic_manual_moon));
                arrayList.add (createImageMap (R.drawable.ic_hand_delete));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;

            case ManualMoon:
                arrayList.add (createImageMap (R.drawable.ic_manual_sun));
                arrayList.add (createImageMap (R.drawable.ic_hand_delete));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;

            case ManualSun:
                arrayList.add (createImageMap (R.drawable.ic_manual_moon));
                arrayList.add (createImageMap (R.drawable.ic_hand_delete));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;

            case Moon:
                arrayList.add (createImageMap (R.drawable.ic_manual_sun));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;

            case Sun:
                arrayList.add (createImageMap (R.drawable.ic_manual_moon));
                arrayList.add (createImageMap (R.drawable.ic_turn_off));
                break;
        }

        targetTypeSpinner = root.findViewById (R.id.spnr_target_type);
        tmpAdapter = new SimpleAdapter (getContext (), arrayList, R.layout.spinner_row, new String[]{ "targetImage" }, new int[]{ R.id.imgSpinnerRowImage });
        targetTypeSpinner.setAdapter (tmpAdapter);

        targetTypeSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ()
        {
            @Override
            public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
            {
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent)
            {

            }
        });
    }

    private HashMap<String, Integer> createImageMap (int imageId) {
        HashMap<String, Integer> result;

        result = new HashMap<> ();
        result.put ("targetImage", imageId);

        return result;
    }
}