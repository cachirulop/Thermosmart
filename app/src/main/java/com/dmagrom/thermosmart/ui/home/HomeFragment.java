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
import com.dmagrom.thermosmart.widget.ArcSeekBar;
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

    private HomeViewModel homeViewModel;

    // private ArcSeekBar seekBarDegrees;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of (this).get (HomeViewModel.class);
        View root = inflater.inflate (R.layout.fragment_home, container, false);

        //seekBarDegrees = root.findViewById (R.id.seek_bar_degrees);

        final TextView textView;
        final CircularSeekBar seekBar;

        textView = root.findViewById (R.id.txt_current_temp);
        seekBar = root.findViewById (R.id.seek_circular_bar_degrees);

        seekBar.setOnSeekBarChangeListener (new CircularSeekBar.OnCircularSeekBarChangeListener ()
        {
            @Override
            public void onProgressChanged (CircularSeekBar circularSeekBar, float progress, boolean fromUser)
            {
                homeViewModel.setCurrentTemperature (progress);
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

        homeViewModel.getCurrentTemperature ().observe (this, new Observer<Float> ()
        {
            @Override
            public void onChanged (@Nullable Float s)
            {
                // textView.setText (String.format ("%.1fº", s.floatValue ()));
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

                current = seekBar.getCurrentValue ();
                if (current > ArcSeekBar.MIN_TEMPERATURE) {
                    seekBar.setCurrentValue (current - 0.5f);
                }

                textView.setText (String.format ("%.1fº", current));
            }
        });

        btn = root.findViewById (R.id.btn_plus);
        btn.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                float current;

                current = seekBar.getCurrentValue ();
                if (current < ArcSeekBar.MAX_TEMPERATURE) {
                    seekBar.setCurrentValue (current + 0.5f);
                }

                textView.setText (String.format ("%.1fº", current));
            }
        });

        // Firebase
        FirebaseDatabase db = FirebaseDatabase.getInstance ();
        DatabaseReference dbRef = db.getReference ();

        //dbRef = db.getReference ("thermostat");

        dbRef.child ("thermostat").child ("currentTemp").addValueEventListener (new ValueEventListener () {
            public void onDataChange (DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Float value = dataSnapshot.getValue(Float.class);
                Log.d(TAG, "************************* Value is: " + value);

                seekBar.setCurrentValue (value);
                textView.setText (String.format ("%.1fº", value));
            }

            @Override
            public void onCancelled (DatabaseError error) {
                // Failed to read value
                Log.w (TAG, "Failed to read value.", error.toException ());
            }
        });

        return root;
    }
}