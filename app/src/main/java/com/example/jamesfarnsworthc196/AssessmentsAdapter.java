package com.example.jamesfarnsworthc196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Database.Assessment;
import Database.Course;
import Database.DatabaseHelper;
import Database.Term;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.MyViewHolder> {

    private Context context;
    private List<Assessment> assessmentList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView assessmentName;
        public TextView assessmenType;
        public TextView assessmentDate;
        public TextView assessmentEndDate;
        public TextView assessmentCourseName;

        public MyViewHolder(View view) {
            super(view);
            assessmentName = view.findViewById(R.id.assessmentName);
            assessmenType = view.findViewById(R.id.assessmentType);
            assessmentDate = view.findViewById(R.id.assessmentDate);
            assessmentEndDate = view.findViewById(R.id.assessmentFinishDate);
            assessmentCourseName = view.findViewById(R.id.assessmentCourseName);
        }
    }

    public AssessmentsAdapter(Context context, List<Assessment> assessmentList){
        this.context = context;
        this.assessmentList = assessmentList;

    }


    @Override
    public AssessmentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assessments, parent, false);
        return new AssessmentsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentsAdapter.MyViewHolder holder, int position){
        Assessment assessment = assessmentList.get(position);
        DatabaseHelper db = new DatabaseHelper(context);
        holder.assessmentName.setText(assessment.getName());
        holder.assessmenType.setText(assessment.getType());
        holder.assessmentDate.setText(assessment.getDate());
        holder.assessmentEndDate.setText(assessment.getEndDate());
        holder.assessmentCourseName.setText(Integer.toString(assessment.getCourseId()));
    }

    @Override
    public int getItemCount(){
        return assessmentList.size();
    }
}
