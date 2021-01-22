package com.dmagrom.thermosmart.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Program
{
    private int        id;
    private String     name;
    private String     description;
    private List<Integer> program;

    public Program ()
    {
    }

    public Program (int id, String name, String description, List<Integer> program)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.program = program;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public List<Integer> getProgram ()
    {
        return program;
    }

    public void setProgram (List<Integer> program)
    {
        this.program = program;
    }

    public void setTargetType (int hour, DatabaseGlobals.ThermosmartProgram newType)
    {
        program.set (hour, newType.getIntValue ());
    }

    public DatabaseGlobals.ThermosmartProgram getTargetType (int hour) {
        int idProgram;

        idProgram = program.get (hour);

        return DatabaseGlobals.ThermosmartProgram.values () [idProgram];
    }

    @Override
    public String toString ()
    {
        return "Program{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", program=" + program + '}';
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass () != o.getClass ()) {
            return false;
        }
        Program program1 = (Program) o;
        return id == program1.id && Objects.equals (name, program1.name) && Objects.equals (description, program1.description) && Objects.equals (program, program1.program);
    }

    @Override
    public int hashCode ()
    {
        return Objects.hash (id, name, description, program);
    }

}
