package com.dmagrom.thermosmart.model;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public abstract class DummyFirebaseValueEventListener
        implements ValueEventListener
{
    @Override
    public void onCancelled (DatabaseError error) {
        Log.w (DummyFirebaseValueEventListener.class.getName (), "Failed to read value.", error.toException ());
    }
}
