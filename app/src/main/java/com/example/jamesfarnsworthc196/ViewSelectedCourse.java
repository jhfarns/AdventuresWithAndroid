package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelper;
import Database.Course;

public class ViewSelectedCourse extends AppCompatActivity {

    private TermsAdapter mAdapter;
    private List<Course> coursesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;
    private String startDate;
    private String endDate;
    private String courseName;
    private String courseStatus;
    private String courseNote;
    private Course course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_course);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("selectedCourseId", 1);
        course = db.getCourse(id);


        startDate = course.getStartdate();
        endDate = course.getEnddate();
        courseName = course.getCoursename();
        courseStatus = course.getStatus();
        courseNote = course.getNote();

        TextView viewStartDate = findViewById(R.id.selectedCourseStartDate);
        TextView viewEndDate = findViewById(R.id.selectedCourseEndDate);
        TextView viewCourseName = findViewById(R.id.selectedCourseName);
        TextView viewCourseStatus = findViewById(R.id.selectedCourseStatus);
        TextView viewCourseNote = findViewById(R.id.selectedCourseNote);

        viewStartDate.setText(startDate);
        viewEndDate.setText(endDate);
        viewCourseName.setText(courseName);
        viewCourseStatus.setText(courseStatus);
        viewCourseNote.setText(courseNote);
    }


}
