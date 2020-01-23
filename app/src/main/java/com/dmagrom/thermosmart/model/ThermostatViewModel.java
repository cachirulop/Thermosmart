package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThermostatViewModel
        extends ViewModel
{
    DatabaseReference dbRef;
    Lifecycle lifecycle;

    public enum ThermostatStatus {
        ON,
        OFF
    }

    public enum ReleStatus {
        RELE_ON,
        RELE_OFF
    }

    public enum ThermostatGoalType {
        Manual,
        Sun,
        Night,
        Comfort
    }

    private MutableLiveData<Float> currentTemperature;
    private MutableLiveData<Float> currentGoalTemperature;
    private MutableLiveData<Float> currentHumidity;
    private MutableLiveData<Float> comfortTemperature;
    private MutableLiveData<Float> nightTemperature;
    private MutableLiveData<Float> sunTemperature;

    public ThermostatViewModel ()
    {
        FirebaseDatabase db;

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ().child ("thermostat");

        registerListeners ();

        currentTemperature = new MutableLiveData<> ();
        currentTemperature.setValue (0f);

        currentGoalTemperature = new MutableLiveData<> ();
        currentGoalTemperature.setValue (0f);

        currentHumidity = new MutableLiveData<> ();
        currentHumidity.setValue (0f);

        comfortTemperature = new MutableLiveData<> ();
        comfortTemperature.setValue (0f);

        nightTemperature = new MutableLiveData<> ();
        nightTemperature.setValue (0f);

        sunTemperature = new MutableLiveData<> ();
        sunTemperature.setValue (0f);
    }

    private void registerListeners ()
    {
        addValueListener (dbRef.child ("currentTemp"), (data) -> currentTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child ("currentGoalTemp"), (data) -> currentGoalTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child ("currentHumidity"), (data) -> currentHumidity.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child ("comfortTemp"), (data) -> comfortTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child ("nightTemp"), (data) -> nightTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child ("sunTemp"), (data) -> sunTemperature.setValue (data.getValue (Float.class)));
    }

    public LiveData<Float> getCurrentTemperature ()
    {
        return currentTemperature;
    }

    public void setCurrentTemperature (Float value)
    {
        dbRef.child ("currentTemp").setValue (value);
    }

    public LiveData<Float> getCurrentGoalTemperature ()
    {
        return currentGoalTemperature;
    }

    public void setCurrentGoalTemperature (Float value)
    {
        dbRef.child ("currentGoalTemp").setValue (value);
    }

    public LiveData<Float> getCurrentHumidity ()
    {
        return currentHumidity;
    }

    public void setCurrentHumidity (Float value)
    {
        dbRef.child ("currentHumidity").setValue (value);
    }

    public LiveData<Float> getComfortTemperature ()
    {
        return comfortTemperature;
    }

    public void setComfortTemperature (Float value)
    {
        dbRef.child ("comfortTemp").setValue (value);
    }

    public LiveData<Float> getNightTemperature ()
    {
        return nightTemperature;
    }

    public void setNightTemperature (Float value)
    {
        dbRef.child ("nightTemp").setValue (value);
    }

    public LiveData<Float> getSunTemperature ()
    {
        return sunTemperature;
    }

    public void setSunTemperature (Float value)
    {
        dbRef.child ("sunTemp").setValue (value);
    }

    private void addValueListener (DatabaseReference db, SingleValueListener listener) {
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

    private interface SingleValueListener
    {
        void onDataChange (@NonNull DataSnapshot data);
    }
}
