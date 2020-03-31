package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class BaseViewModel
        extends ViewModel
{
    protected void addValueListener (DatabaseReference db, SingleValueListener listener) {
        db.addValueEventListener (new ValueEventListener ()
        {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot)
            {
                listener.onDataChange (dataSnapshot);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError)
            {
                Log.w (ThermostatViewModel.class.getName (), "Failed to read value.", databaseError.toException ());
            }
        });
    }

    protected interface SingleValueListener
    {
        void onDataChange (@NonNull DataSnapshot data);
    }
}
