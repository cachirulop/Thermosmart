package com.dmagrom.thermosmart.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ThermostatViewModel;
import com.dmagrom.thermosmart.widget.CircularSeekBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment
        extends Fragment
{
    private ThermostatViewModel viewModel;

    // private ArcSeekBar seekBarDegrees;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        View root;
        final TextView textView;
        final CircularSeekBar seekBar;

        viewModel = ViewModelProviders.of (this).get (ThermostatViewModel.class);
        root = inflater.inflate (R.layout.fragment_home, container, false);

        textView = root.findViewById (R.id.txt_current_temperature);
        seekBar = root.findViewById (R.id.seek_circular_bar_degrees);

        seekBar.setOnSeekBarChangeListener (new CircularSeekBar.OnCircularSeekBarChangeListener ()
        {
            @Override
            public void onProgressChanged (CircularSeekBar circularSeekBar, float progress, boolean fromUser)
            {
            }

            @Override
            public void onStopTrackingTouch (CircularSeekBar seekBar)
            {

            }

            @Override
            public void onStartTrackingTouch (CircularSeekBar seekBar)
            {

            }
        });

        viewModel.getCurrentTemperature ().observe (this, new Observer<Float> ()
        {
            @Override
            public void onChanged (@Nullable Float s)
            {
                textView.setText (String.format ("%.1fº", s.floatValue ()));
                seekBar.setCurrentValue (s.floatValue ());
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

        // Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance ();
        DatabaseReference dbRef = db.getReference ();

        //dbRef = db.getReference ("thermostat");

        dbRef.child ("thermostat").child ("currentTemp").addValueEventListener (new ValueEventListener () {
            public void onDataChange (DataSnapshot dataSnapshot) {
                Float value = dataSnapshot.getValue(Float.class);

                viewModel.setCurrentTemperature (value);
            }

            @Override
            public void onCancelled (DatabaseError error) {
                Log.w (TAG, "Failed to read value.", error.toException ());
            }
        });

        return root;
    }
}