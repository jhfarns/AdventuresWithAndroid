package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView viewCourseMentor = findViewById(R.id.selectedCourseMentor);
        TextView viewCourseMentorNumber = findViewById(R.id.selectedCourseMentorNumber);
        TextView viewCourseMentorEmail = findViewById(R.id.selectedCourseMentorEmail);


        viewStartDate.setText(startDate);
        viewEndDate.setText(endDate);
        viewCourseName.setText(courseName);
        viewCourseStatus.setText(courseStatus);
        viewCourseNote.setText(courseNote);
        viewCourseMentor.setText("Jenny Mentor");
        viewCourseMentorNumber.setText("555-555-5555");
        viewCourseMentorEmail.setText("JMentor@ImaHelpYou.com");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.save:
                // open dialog box with prepop data
                LayoutInflater layoutInflaterSave = LayoutInflater.from(getApplicationContext());
                View view = layoutInflaterSave.inflate(R.layout.coursesave, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewSelectedCourse.this);
                alertDialogBuilder.setView(view);

                final EditText startDate = view.findViewById(R.id.editCourseStart);
                final EditText endDate = view.findViewById(R.id.editCourseEnd);
                final EditText courseName = view.findViewById(R.id.editCourseName);
                final EditText courseStatus = view.findViewById(R.id.editCourseStatus);
                final EditText courseNote = view.findViewById(R.id.editCourseNote);

                courseName.setText("Course Name");
                startDate.setText("Start Date");
                endDate.setText("End Date");
                courseStatus.setText("Status");
                courseNote.setText("I am a note");

                alertDialogBuilder.setCancelable(true).setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        course.setStartdate(startDate.getText().toString());
                        course.setEnddate(endDate.getText().toString());
                        course.setCoursename(courseName.getText().toString());
                        course.setStatus(courseStatus.getText().toString());
                        course.setNote(courseNote.getText().toString());
                        db.updateCourse(course);
                        //reload activity
                        finish();
                        startActivity(getIntent());
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                // capture data and save
                // close dialog box
                Toast.makeText(context, "save", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                // delete term
                db.deleteCourse(course);
                // redirect to the parent activity
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                Intent courseIntent = new Intent(context, Courses.class);
                startActivity(courseIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
