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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.ProgramsViewModel;
import com.dmagrom.thermosmart.model.dto.Program;

import java.util.List;

public class ProgramsFragment
        extends Fragment
{
    private ProgramsViewModel viewModel;

    public View onCreateView (@NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState)
    {
        ProgramAdapter adapter;
        RecyclerView recyclerView;
        ViewModelProvider provider;
        View root;

        provider = new ViewModelProvider (this);
        viewModel = provider.get (ProgramsViewModel.class);

        root = inflater.inflate (R.layout.fragment_programs, container, false);

        adapter = new ProgramAdapter ();
        recyclerView = (RecyclerView) root.findViewById (R.id.program_list);
        recyclerView.setLayoutManager (new LinearLayoutManager (getContext ()));
        recyclerView.setItemAnimator (new DefaultItemAnimator ());
        recyclerView.setAdapter (adapter);

        viewModel.getProgramList ().observe (getViewLifecycleOwner (), new Observer<List<Program>> ()
        {
            @Override
            public void onChanged (List<Program> programs)
            {
                adapter.setProgramList (programs);
            }
        });


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