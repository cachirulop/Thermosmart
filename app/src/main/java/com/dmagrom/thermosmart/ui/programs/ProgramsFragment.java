package com.dmagrom.thermosmart.ui.programs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ProgramsViewModel;

public class ProgramsFragment
        extends Fragment
{
    private ProgramsViewModel viewModel;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        ViewModelProvider provider;
        View root;

        provider = new ViewModelProvider (this);

        viewModel = provider.get (ProgramsViewModel.class);
        root = inflater.inflate (R.layout.fragment_programs, container, false);

        /*
        final TextView textView = root.findViewById (R.id.text_gallery);
        galleryViewModel.getText ().observe (this, new Observer<String> ()
        {
            @Override
            public void onChanged (@Nullable String s)
            {
                textView.setText (s);
            }
        });
         */

        return root;
    }
}