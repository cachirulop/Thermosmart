package com.dmagrom.thermosmart.model;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventsLauncher
{
    public static void launchProgramChange (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram program)
    {
        final DatabaseReference dbRef;
        final FirebaseDatabase  db;
        String value;

        value = String.format ("%d;%d;%d", idProgram, quarter, program.getIntValue ());

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ().child (DatabaseGlobals.KEY_EVENTS);

        dbRef.child (DatabaseGlobals.KEY_EVENT_PROGRAM_CHANGE).setValue (value);
    }
}
