package com.dmagrom.thermosmart.ui.programs;

import android.os.Bundle;
import android.util.Log;
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
import com.dmagrom.thermosmart.model.ProgramsViewModelImpl;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;

import java.util.List;

public class ProgramsFragment
        extends Fragment
        implements ProgramAdapter.OnProgramChangeListener
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
        viewModel = provider.get (ProgramsViewModelImpl.class);

        root = inflater.inflate (R.layout.fragment_programs, container, false);

        adapter = new ProgramAdapter ();
        adapter.setChangeListener (this);

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

        return root;
    }

    @Override
    public void onChange (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram targetType)
    {
        Log.d (ProgramsFragment.class.getName (), "********* Updating program: " + idProgram + ", " + quarter + ", " +targetType);
        viewModel.setQuarterProgram (idProgram, quarter, targetType);
    }
}