package com.dmagrom.thermosmart.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel
        extends ViewModel
{
    // private MutableLiveData<String> mText;
    private MutableLiveData<Float> currentTemperature;

    public HomeViewModel ()
    {
        // mText = new MutableLiveData<> ();
        // mText.setValue ("0º");
        currentTemperature = new MutableLiveData<> ();
        currentTemperature.setValue (0f);
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