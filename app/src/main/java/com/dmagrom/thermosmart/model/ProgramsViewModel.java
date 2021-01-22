package com.dmagrom.thermosmart.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public abstract class ProgramsViewModel
        extends BaseViewModel
{
    MutableLiveData<List<Program>> programList;

    public MutableLiveData<List<Program>> getProgramList ()
    {
        return programList;
    }

    public void setProgramList (MutableLiveData<List<Program>> programList)
    {
        this.programList = programList;
    }

    public abstract void setQuarterProgram (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram targetType);
}
