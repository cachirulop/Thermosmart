package com.dmagrom.thermosmart.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Program
{
    private String name;
    private String description;
    private DatabaseGlobals.ThermostatTargetType [] scheduler;

    public Program ()
    {
        name = "";
        description = "";
        scheduler = new DatabaseGlobals.ThermostatTargetType[24];
        for (int i = 0; i < scheduler.length; i++) {
            scheduler [i] = DatabaseGlobals.ThermostatTargetType.None;
        }
    }

    public Program (String name, String description, DatabaseGlobals.ThermostatTargetType [] scheduler)
    {
        this.name = name;
        this.description = description;
        this.scheduler = scheduler;
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

    public DatabaseGlobals.ThermostatTargetType [] getScheduler ()
    {
        return scheduler;
    }

    public void setScheduler (DatabaseGlobals.ThermostatTargetType [] scheduler)
    {
        this.scheduler = scheduler;
    }

    @Override
    public String toString ()
    {
        return "Program{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", scheduler=" + Arrays.toString (scheduler) +
                '}';
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Program)) {
            return false;
        }
        Program program = (Program) o;
        return Objects.equals (name, program.name) &&
                Objects.equals (description, program.description) &&
                Arrays.equals (scheduler, program.scheduler);
    }

    @Override
    public int hashCode ()
    {
        int result = Objects.hash (name, description);
        result = 31 * result + Arrays.hashCode (scheduler);
        return result;
    }

    public void setTargetType (int hour, DatabaseGlobals.ThermostatTargetType newType) {
        scheduler [hour] = newType;
    }
}
