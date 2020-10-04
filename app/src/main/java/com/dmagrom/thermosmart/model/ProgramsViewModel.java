package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProgramsViewModel
        extends BaseViewModel
{
    protected DatabaseReference dbRef;
    MutableLiveData<List<Program>>           programList;
    List<Long> currentDailyProgram;

    public ProgramsViewModel ()
    {
        super();

        // TODO: Load programs from database
/*
        programList = new MutableLiveData<> (new ArrayList<> ());
        programList.getValue ().add (new Program ());
*/
        // TODO: Register listeners

        FirebaseDatabase db;

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ();

        registerListeners ();

        programList = new MutableLiveData<> (new ArrayList<> ());

        // TODO: For testing
        Program p;

        for (int i = 1; i < 3; i++) {
            p = new Program ();
            p.setId (i);

            if (i == 1) {
                p.setName ("Laborables");
                p.setDescription ("Programación de lunes a viernes");
            }
            else if (i == 2) {
                p.setName ("Fin de semana");
                p.setDescription ("Programación para los fines de semana");
            }

            programList.getValue ().add (p);

            dbRef.child (DatabaseGlobals.KEY_PROGRAMS + "/" + p.getId ()).setValue (p);
        }

        currentDailyProgram = new ArrayList<> ();
        for (int i = 0; i < 96; i++) {
            currentDailyProgram.add (new Long(2));
        }

        dbRef.child (DatabaseGlobals.KEY_PROGRAMS).child (DatabaseGlobals.KEY_CURRENT_DAILY_PROGRAM).setValue (currentDailyProgram);
    }

    public MutableLiveData<List<Program>> getProgramList ()
    {
        return programList;
    }

    public void setProgramList (MutableLiveData<List<Program>> programList)
    {
        this.programList = programList;
    }

    private void registerListeners ()
    {
        addValueListener (dbRef.child (DatabaseGlobals.KEY_PROGRAMS),
                          (data) -> readProgramList (data));

    }

    private void readProgramList (DataSnapshot data)
    {
        List<Program> programs;

        programs = programList.getValue ();
        programs.clear ();

        for (DataSnapshot ds : data.getChildren ()) {
            Log.d (ProgramsViewModel.class.getName (), "******** readProgramList: " + ds);
            if (ds.getKey ().equals (DatabaseGlobals.KEY_CURRENT_DAILY_PROGRAM)) {
                currentDailyProgram = (List<Long>) ds.getValue ();
                Log.d (ProgramsViewModel.class.getName (), "******** current program: " + currentDailyProgram);
            }
            else {
                try {
                    programs.add (Integer.parseInt (ds.getKey ()) - 1, ds.getValue (Program.class));
                }
                catch (Exception e) {
                    e.printStackTrace ();
                }
            }
        }

        Log.d (ProgramsViewModel.class.getName (), "******** programs: " + programs.toString ());
        programList.setValue (programs);
    }

}
