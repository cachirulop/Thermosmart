package com.dmagrom.thermosmart.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThermostatViewModel
        extends BaseViewModel
{
    protected DatabaseReference dbRef;

    private MutableLiveData<Float>                              currentTemperature;
    private MutableLiveData<Float>                              currentTargetTemperature;
    private MutableLiveData<Float>                              currentHumidity;
    private MutableLiveData<Float>                              nightTemperature;
    private MutableLiveData<Float>                              sunTemperature;
    private MutableLiveData<DatabaseGlobals.ThermosmartProgram> targetType;

    public ThermostatViewModel ()
    {
        super();

        FirebaseDatabase db;

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ().child (DatabaseGlobals.KEY_STATUS);

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

        targetType = new MutableLiveData<> ();
        targetType.setValue (DatabaseGlobals.ThermosmartProgram.None);
    }

    private void registerListeners ()
    {
        addValueListener (dbRef.child (DatabaseGlobals.KEY_CURRENT_TEMPERATURE),
                          (data) -> currentTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (DatabaseGlobals.KEY_TARGET_TEMPERATURE),
                          (data) -> currentTargetTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (DatabaseGlobals.KEY_CURRENT_HUMIDITY),
                          (data) -> currentHumidity.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (DatabaseGlobals.KEY_NIGHT_TEMPERATURE),
                          (data) -> nightTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (DatabaseGlobals.KEY_SUN_TEMPERATURE),
                          (data) -> sunTemperature.setValue (data.getValue (Float.class)));
        addValueListener (dbRef.child (DatabaseGlobals.KEY_TARGET_TYPE),
                          (data) -> targetType.setValue (DatabaseGlobals.ThermosmartProgram.values () [data.getValue (Integer.class)]));
    }

    public LiveData<Float> getCurrentTemperature ()
    {
        return currentTemperature;
    }

    public void setCurrentTemperature (Float value)
    {
        dbRef.child (DatabaseGlobals.KEY_CURRENT_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getCurrentTargetTemperature ()
    {
        return currentTargetTemperature;
    }

    public void setCurrentTargetTemperature (Float value)
    {
        dbRef.child (DatabaseGlobals.KEY_TARGET_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getCurrentHumidity ()
    {
        return currentHumidity;
    }

    public void setCurrentHumidity (Float value)
    {
        dbRef.child (DatabaseGlobals.KEY_CURRENT_HUMIDITY).setValue (value);
    }

    public LiveData<Float> getNightTemperature ()
    {
        return nightTemperature;
    }

    public void setNightTemperature (Float value)
    {
        dbRef.child (DatabaseGlobals.KEY_NIGHT_TEMPERATURE).setValue (value);
    }

    public LiveData<Float> getSunTemperature ()
    {
        return sunTemperature;
    }

    public void setSunTemperature (Float value)
    {
        dbRef.child (DatabaseGlobals.KEY_SUN_TEMPERATURE).setValue (value);
    }

    public LiveData<DatabaseGlobals.ThermosmartProgram> getTargetType ()
    {
        return targetType;
    }

    public void setTargetType (DatabaseGlobals.ThermosmartProgram value)
    {
        dbRef.child (DatabaseGlobals.KEY_TARGET_TYPE).setValue (value.getIntValue ());
    }
}
