package com.dmagrom.thermosmart.ui.home;

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
import androidx.lifecycle.ViewModelProviders;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.widget.ArcSeekBar;

public class HomeFragment
        extends Fragment
{

    private HomeViewModel homeViewModel;

    private ArcSeekBar seekBarDegrees;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        homeViewModel = ViewModelProviders.of (this).get (HomeViewModel.class);
        View root = inflater.inflate (R.layout.fragment_home, container, false);

        seekBarDegrees = root.findViewById (R.id.seek_bar_degrees);

        // final TextView textView = root.findViewById (R.id.text_home);
        /*
        homeViewModel.getText ().observe (this, new Observer<String> ()
        {
            @Override
            public void onChanged (@Nullable String s)
            {
                textView.setText (s);
            }
        });
        */

        ///////////////////////////////////////////////////////////////////////////

        Button btn;

        btn = root.findViewById (R.id.btn_minus);
        btn.setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                float current;

                current = seekBarDegrees.getDegrees ();
                if (current > ArcSeekBar.MIN_TEMPERATURE) {
                    seekBarDegrees.setDegrees (current - 0.5f);
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

                current = seekBarDegrees.getDegrees ();
                if (current < ArcSeekBar.MAX_TEMPERATURE) {
                    seekBarDegrees.setDegrees (current + 0.5f);
                }
            }
        });

        return root;
    }
}