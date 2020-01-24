package com.dmagrom.thermosmart.ui.thermostat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ThermostatViewModel;
import com.dmagrom.thermosmart.widget.CircularSeekBar;

public class ThermostatFragment
        extends Fragment
{
    private ThermostatViewModel viewModel;

    // private ArcSeekBar seekBarDegrees;

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
            @Override
            public void onProgressChanged (CircularSeekBar circularSeekBar, float progress, boolean fromUser)
            {
                txtTargetTemperature.setText (String.format ("%.1fº", progress));
            }


            @Override
            public void onStopTrackingTouch (CircularSeekBar seekBar)
            {
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

        ///////////////////////////////////////////////////////////////////////////

        Button btn;


        btn = root.findViewById (R.id.btn_minus);
        btn.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                float current;

                current = viewModel.getCurrentTemperature ().getValue ();
                if (current > 0) {
                    viewModel.setCurrentTemperature (current - 0.5f);
                }
            }
        });

        btn = root.findViewById (R.id.btn_plus);
        btn.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                float current;

                current = viewModel.getCurrentTemperature ().getValue ();
                if (current < seekBar.getMax ()) {
                    viewModel.setCurrentTemperature (current + 0.5f);
                }
            }
        });

        return root;
    }
}