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

    // Database keys
    private static final String KEY_THERMOSTAT = "thermostat";
    private static final String KEY_CURRENT_TEMPERATURE = "currentTemperature";
    private static final String KEY_CURRENT_HUMIDITY = "currentHumidity";
    private static final String KEY_NIGHT_TEMPERATURE = "nightTemperature";
    private static final String KEY_SUN_TEMPERATURE = "sunTemperature";
    private static final String KEY_TARGET_TEMPERATURE = "targetTemperature";
    private static final String KEY_STATUS = "status";
    private static final String KEY_RELE_STATUS = "releStatus";
    private static final String KEY_TARGET_TYPE = "targetType";

    public enum ThermostatStatus {
        ON,
        OFF
    }

    public enum ReleStatus {
        RELE_ON,
        RELE_OFF
    }

    public enum ThermostatTargetType {
        Manual,
        Sun,
        Night,
        Comfort
    }

    private MutableLiveData<Float> currentTemperature;
    private MutableLiveData<Float> currentTargetTemperature;
    private MutableLiveData<Float> currentHumidity;
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

        currentTargetTemperature = new MutableLiveData<> ();
        currentTargetTemperature.setValue (0f);

        currentHumidity = new MutableLiveData<> ();
        currentHumidity.setValue (0f);

        nightTemperature = new MutableLiveData<> ();
        nightTemperature.setValue (0f);

        sunTemperature = new MutableLiveData<> ();
        sunTemperature.setValue (0f);
    }

    private void registerListeners ()
    {
        addValueListener (dbRef.child (KEY_CURRENT_TEMPERATURE), (data) -> currentTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (KEY_TARGET_TEMPERATURE), (data) -> currentTargetTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (KEY_CURRENT_HUMIDITY), (data) -> currentHumidity.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (KEY_NIGHT_TEMPERATURE), (data) -> nightTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (KEY_SUN_TEMPERATURE), (data) -> sunTemperature.setValue (data.getValue (Float.class)));
    }

    public LiveData<Float> getCurrentTemperature ()
    {
        return currentTemperature;
    }

    public void setCurrentTemperature (Float value)
    {
        dbRef.child (KEY_CURRENT_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getCurrentTargetTemperature ()
    {
        return currentTargetTemperature;
    }

    public void setCurrentTargetTemperature (Float value)
    {
        dbRef.child (KEY_TARGET_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getCurrentHumidity ()
    {
        return currentHumidity;
    }

    public void setCurrentHumidity (Float value)
    {
        dbRef.child (KEY_CURRENT_HUMIDITY).setValue (value);
    }

    public LiveData<Float> getNightTemperature ()
    {
        return nightTemperature;
    }

    public void setNightTemperature (Float value)
    {
        dbRef.child (KEY_NIGHT_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getSunTemperature ()
    {
        return sunTemperature;
    }

    public void setSunTemperature (Float value)
    {
        dbRef.child (KEY_SUN_TEMPERATURE).setValue (value);
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
