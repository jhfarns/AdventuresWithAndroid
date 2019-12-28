package com.example.jamesfarnsworthc196;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import Database.Course;
import Database.Term;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private Context context;
    private List<Course> coursesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView courseName;
        public TextView start;
        public TextView end;
        public TextView status;
        public TextView note;
        // This does not have the mentors, but it should

        public MyViewHolder(View view) {
            super(view);
            courseName = view.findViewById(R.id.courseName);
            start = view.findViewById(R.id.courseStart);
            end = view.findViewById(R.id.courseEnd);
            status = view.findViewById(R.id.courseStatus);
            note = view.findViewById(R.id.courseNote);
        }
    }

    public CoursesAdapter(Context context, List<Course> coursesList){
        this.context = context;
        this.coursesList = coursesList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_courses, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoursesAdapter.MyViewHolder holder, int position){
        Course course = coursesList.get(position);
        holder.courseName.setText(course.getCoursename());
        holder.start.setText(course.getStartdate());
        holder.end.setText(course.getEnddate());
        holder.status.setText(course.getStatus());
        holder.note.setText(course.getNote());
    }

    @Override
    public int getItemCount(){
        return coursesList.size();
    }
}
