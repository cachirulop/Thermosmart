package com.dmagrom.thermosmart.ui.programs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmagrom.thermosmart.R;
import com.dmagrom.thermosmart.model.dto.DatabaseGlobals;
import com.dmagrom.thermosmart.model.dto.Program;
import com.dmagrom.thermosmart.widget.ProgramHourCell;

import java.util.ArrayList;
import java.util.List;

public class ProgramAdapter
        extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>
{
    private List<Program> programList;
    OnProgramChangeListener changeListener;

    public interface OnProgramChangeListener {
        void onChange (int idProgram, int quarter, DatabaseGlobals.ThermosmartProgram targetType);
    }

    public ProgramAdapter () {
        programList = new ArrayList<> ();
    }
    public ProgramAdapter (List<Program> programs)
    {
        programList = programs;
    }

    public void setProgramList (List<Program> programList)
    {
        this.programList = programList;
        this.notifyDataSetChanged ();
    }

    public List<Program> getProgramList ()
    {
        return programList;
    }

    public OnProgramChangeListener getChangeListener ()
    {
        return changeListener;
    }

    public void setChangeListener (OnProgramChangeListener changeListener)
    {
        this.changeListener = changeListener;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType)
    {
        View view;
        ProgramViewHolder result;

        view = LayoutInflater.from (parent.getContext ())
                .inflate (R.layout.widget_program, parent, false);

        result = new ProgramViewHolder (view);

        return result;
    }

    @Override
    public void onBindViewHolder (@NonNull ProgramViewHolder holder, int position)
    {
        Program currentProgram;

        currentProgram = programList.get (position);

        holder.idProgram = currentProgram.getId ();
        holder.title.setText (currentProgram.getName ());
        holder.description.setText (currentProgram.getDescription ());
        for (int programIndex = 0; programIndex < holder.programHourList.length; programIndex++) {
            int quarterIndex;
            ProgramHourCell currentCell;

            quarterIndex = programIndex * 4;
            currentCell = holder.programHourList [programIndex];
            currentCell.setTargetTypeFirstHalf (currentProgram.getTargetType (quarterIndex));
            currentCell.setTargetTypeSecondHalf (currentProgram.getTargetType (quarterIndex + 2));
            currentCell.setChangeListener (new ProgramHourCell.OnProgramHourCellChangeListener ()
            {
                @Override
                public void onFirstHalfTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType)
                {
                    if (changeListener != null) {
                        int quarter;

                        quarter = (hour * 4);

                        launchOnProgramChange (currentProgram.getId (), targetType, quarter, quarter + 1);
                    }
                }

                @Override
                public void onSecondHalfTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType)
                {
                    if (changeListener != null) {
                        int quarter;

                        quarter = (hour * 4) + 2;

                        launchOnProgramChange (currentProgram.getId (), targetType, quarter, quarter + 1);
                    }
                }

                @Override
                public void onHourTargetTypeChanged (int hour, DatabaseGlobals.ThermosmartProgram targetType)
                {
                    if (changeListener != null) {
                        int quarter;

                        quarter = (hour * 4);

                        launchOnProgramChange (currentProgram.getId (),
                                               targetType,
                                               quarter,
                                               quarter + 1,
                                               quarter + 2,
                                               quarter + 3);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount ()
    {
        return programList.size ();
    }

    public void launchOnProgramChange (int idProgram, DatabaseGlobals.ThermosmartProgram targetType, int... quarters)
    {
        for (int currentQuarter : quarters) {
            changeListener.onChange (idProgram, currentQuarter, targetType);
        }
    }

    public static class ProgramViewHolder
            extends RecyclerView.ViewHolder
    {
        private static final int[] ID_PROGRAM_CELLS = {R.id.program_hour_cell_0,
                R.id.program_hour_cell_1,
                R.id.program_hour_cell_2,
                R.id.program_hour_cell_3,
                R.id.program_hour_cell_4,
                R.id.program_hour_cell_5,
                R.id.program_hour_cell_6,
                R.id.program_hour_cell_7,
                R.id.program_hour_cell_8,
                R.id.program_hour_cell_9,
                R.id.program_hour_cell_10,
                R.id.program_hour_cell_11,
                R.id.program_hour_cell_12,
                R.id.program_hour_cell_13,
                R.id.program_hour_cell_14,
                R.id.program_hour_cell_15,
                R.id.program_hour_cell_16,
                R.id.program_hour_cell_17,
                R.id.program_hour_cell_18,
                R.id.program_hour_cell_19,
                R.id.program_hour_cell_20,
                R.id.program_hour_cell_21,
                R.id.program_hour_cell_22,
                R.id.program_hour_cell_23
        };

        int idProgram;
        TextView title;
        TextView description;
        ProgramHourCell[] programHourList;

        public ProgramViewHolder (@NonNull View itemView)
        {
            super (itemView);

            title = itemView.findViewById (R.id.txt_program_title);
            description = itemView.findViewById (R.id.txt_program_description);

            programHourList = new ProgramHourCell[24];
            for (int programIndex = 0; programIndex < programHourList.length; programIndex++) {
                programHourList[programIndex] = itemView.findViewById (ID_PROGRAM_CELLS[programIndex]);
            }
        }
    }
}
