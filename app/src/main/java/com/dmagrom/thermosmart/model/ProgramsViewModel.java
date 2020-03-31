package com.dmagrom.thermosmart.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dmagrom.thermosmart.model.dto.Program;

import java.util.ArrayList;
import java.util.List;

public class ProgramsViewModel
        extends BaseViewModel
{
    MutableLiveData<List<Program>> programList;

    public ProgramsViewModel ()
    {
        super();

        // TODO: Load programs from database
        programList = new MutableLiveData<> (new ArrayList<> ());
        programList.getValue ().add (new Program ());

        // TODO: Register listeners
    }

    public MutableLiveData<List<Program>> getProgramList ()
    {
        return programList;
    }

    public void setProgramList (MutableLiveData<List<Program>> programList)
    {
        this.programList = programList;
    }
}
