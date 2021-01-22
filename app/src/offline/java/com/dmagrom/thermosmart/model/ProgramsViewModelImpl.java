package com.dmagrom.thermosmart.model;

import androidx.lifecycle.MutableLiveData;

import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProgramsViewModelImpl
        extends ProgramsViewModel
{
    public ProgramsViewModelImpl ()
    {
        super ();

        readProgramList ();
    }

    @Override
    public void setQuarterProgram (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram targetType)
    {
        // do nothing
    }

    protected void readProgramList ()
    {
        programList = new MutableLiveData<> (new ArrayList<> ());
        programList.setValue (new ArrayList<> ());

        Program p;

        for (int i = 1; i < 4; i++) {
            List<Integer> targetTypes;

            p = new Program ();
            p.setId (i);

            if (i == 1) {
                p.setName ("Laborables");
                p.setDescription ("Programación de lunes a viernes");
            }
            else if (i == 2) {
                p.setName ("Fin de semana");
                p.setDescription ("Programación para los fines de semana");
            }
            else if (i == 3) {
                p.setName ("Frio");
                p.setDescription ("Para cuando hace mucho frio");
            }

            targetTypes = new ArrayList<> ();
            for (int currentTargetType = 0; currentTargetType < 96; currentTargetType++) {
                if (currentTargetType >= 37) {
                    targetTypes.add (DatabaseGlobals.ThermosmartProgram.Sun.getIntValue ());
                }
                else {
                    targetTypes.add (DatabaseGlobals.ThermosmartProgram.Moon.getIntValue ());
                }
            }

            p.setProgram (targetTypes);

            programList.getValue ().add (p);
        }
    }
}
