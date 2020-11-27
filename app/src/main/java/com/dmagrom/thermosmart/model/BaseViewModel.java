package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.google.firebase.database.ChildEventListener;
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
                Log.d (BaseViewModel.class.getName (), "******** " + dataSnapshot.toString ());

                listener.onDataChange (dataSnapshot);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError)
            {
                Log.w (ThermostatViewModel.class.getName (), "Failed to read value.", databaseError.toException ());
            }
        });
    }

    protected void addChildListener (DatabaseReference db, SingleValueListener listener) {
        db.addChildEventListener (new ChildEventListener ()
        {
            @Override
            public void onChildAdded (@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Log.d (BaseViewModel.class.getName (), "******** childAdded: " + dataSnapshot.toString ());
                listener.onDataChange (dataSnapshot);
            }

            @Override
            public void onChildChanged (@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Log.d (BaseViewModel.class.getName (), "******** onChildChanged: " + dataSnapshot.toString ());
            }

            @Override
            public void onChildRemoved (@NonNull DataSnapshot dataSnapshot)
            {
                Log.d (BaseViewModel.class.getName (), "******** onChildRemoved: " + dataSnapshot.toString ());
            }

            @Override
            public void onChildMoved (@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Log.d (BaseViewModel.class.getName (), "******** onChildMoved: " + dataSnapshot.toString () + ", " + s);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError)
            {
                Log.d (BaseViewModel.class.getName (), "******** onCancelled: " + databaseError.toString ());
            }
        });
    }

    protected interface SingleValueListener
    {
        void onDataChange (@NonNull DataSnapshot data);
    }
}
