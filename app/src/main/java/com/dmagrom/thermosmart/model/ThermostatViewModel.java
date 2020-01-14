package com.dmagrom.thermosmart.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThermostatViewModel
    extends ViewModel
{
    DatabaseReference dbRef;

    private MutableLiveData<Float> currentTemperature;
    private MutableLiveData<Float> currentGoalTemperature;
    private MutableLiveData<Float> currentHumidity;
    private MutableLiveData<Float> confortTemperature;
    private MutableLiveData<Float> nightTemperature;
    private MutableLiveData<Float> sunTemperature;

    public ThermostatViewModel ()
    {
        FirebaseDatabase db;

        db = FirebaseDatabase.getInstance ();
        dbRef = db.getReference ();

        registerListeners ();

        currentTemperature = new MutableLiveData<> ();
        currentTemperature.setValue (0f);

        currentGoalTemperature = new MutableLiveData<> ();
        currentGoalTemperature.setValue (0f);

        currentHumidity = new MutableLiveData<> ();
        currentHumidity.setValue (0f);

        confortTemperature = new MutableLiveData<> ();
        confortTemperature.setValue (0f);

        nightTemperature = new MutableLiveData<> ();
        nightTemperature.setValue (0f);

        sunTemperature = new MutableLiveData<> ();
        sunTemperature.setValue (0f);
    }

    private void registerListeners ()
    {
        dbRef.child ("thermostat").child ("currentTemp").addValueEventListener (new DummyFirebaseValueEventListener () {
            public void onDataChange (DataSnapshot dataSnapshot) {
                setCurrentTemperature (dataSnapshot.getValue(Float.class));
            }
        });
    }

    public void setCurrentTemperature (MutableLiveData<Float> currentTemperature)
    {
        this.currentTemperature = currentTemperature;
    }

    public MutableLiveData<Float> getCurrentGoalTemperature ()
    {
        return currentGoalTemperature;
    }

    public void setCurrentGoalTemperature (MutableLiveData<Float> currentGoalTemperature)
    {
        this.currentGoalTemperature = currentGoalTemperature;
    }

    public MutableLiveData<Float> getCurrentHumidity ()
    {
        return currentHumidity;
    }

    public void setCurrentHumidity (MutableLiveData<Float> currentHumidity)
    {
        this.currentHumidity = currentHumidity;
    }

    public MutableLiveData<Float> getConfortTemperature ()
    {
        return confortTemperature;
    }

    public void setConfortTemperature (MutableLiveData<Float> confortTemperature)
    {
        this.confortTemperature = confortTemperature;
    }

    public MutableLiveData<Float> getNightTemperature ()
    {
        return nightTemperature;
    }

    public void setNightTemperature (MutableLiveData<Float> nightTemperature)
    {
        this.nightTemperature = nightTemperature;
    }

    public MutableLiveData<Float> getSunTemperature ()
    {
        return sunTemperature;
    }

    public void setSunTemperature (MutableLiveData<Float> sunTemperature)
    {
        this.sunTemperature = sunTemperature;
    }

    public LiveData<Float> getCurrentTemperature ()
    {
        return currentTemperature;
    }

    public void setCurrentTemperature (Float value)
    {
        currentTemperature.setValue (value);
    }
}
