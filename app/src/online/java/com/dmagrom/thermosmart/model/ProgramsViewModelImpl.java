package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProgramsViewModelImpl
        extends ProgramsViewModel
{
    final private DatabaseReference dbRef;
    private boolean updating = false;

    public ProgramsViewModelImpl ()
    {
        super ();

        FirebaseDatabase db;

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ().child (DatabaseGlobals.KEY_PROGRAMS);

        programList = new MutableLiveData<> ();
        programList.setValue (new ArrayList<> ());

        addValueListener (dbRef, this::readProgramList);
    }

    protected void readProgramList (DataSnapshot data)
    {
        List<Program> programs;

        if (!updating) {
            programs = programList.getValue ();

            if (programs != null) {
                programs.clear ();

                for (DataSnapshot ds : data.getChildren ()) {
                    Log.d (ProgramsViewModelImpl.class.getName (), "******** readProgramList: " + ds);
                    try {
                        programs.add (Integer.parseInt (ds.getKey ()) - 1, ds.getValue (Program.class));
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }
                }

                Log.d (ProgramsViewModelImpl.class.getName (), "******** programs: " + programs.toString ());
                programList.setValue (programs);
            }
            else {
                Log.d (ProgramsViewModelImpl.class.getName (), "******** Programs is null");
            }
        }
    }

    @Override
    public void setQuarterProgram (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram targetType)
    {
        Task<Void> task;
        updating = true;

        task = dbRef.child (Integer.toString (idProgram))
                .child (DatabaseGlobals.KEY_PROGRAM_PROGRAM)
                .child (Integer.toString (quarter)).setValue (targetType.getIntValue ());
        task.addOnCompleteListener (new OnCompleteListener<Void> ()
        {
            @Override
            public void onComplete (@NonNull Task<Void> task)
            {
                updating = false;

                EventsLauncher.launchProgramChange (idProgram, quarter, targetType);
            }
        });
    }
}
