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

import java.util.ArrayList;
import java.util.List;

import Database.Assessment;
import Database.DatabaseHelper;
import Database.Term;

public class ViewSelectedAssessment extends AppCompatActivity {

    private AssessmentsAdapter mAdapter;
    private List<Assessment> assessmentsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;
    private String assessmentsName;
    private String assessmentsType;
    private String assessmentsDate;
    private String assessmentsCourseId;
    private Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("selectedAssessmentId", 1);
        assessment = db.getAssessment(id);


        assessmentsName = assessment.getName();
        assessmentsType = assessment.getType();
        assessmentsDate = assessment.getDate();
        int tempVal = assessment.getCourseId();
        assessmentsCourseId = String.valueOf(assessment.getCourseId());

        TextView viewName = findViewById(R.id.assessmentName);
        TextView viewType = findViewById(R.id.assessmentType);
        TextView viewDate = findViewById(R.id.assessmentDate);
        TextView viewCourse = findViewById(R.id.assessmentCourseName);

        viewName.setText(assessmentsName);
        viewType.setText(assessmentsType);
        viewDate.setText(assessmentsDate);
        viewCourse.setText(assessmentsCourseId);
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
                View alertDialogViewTermSave = layoutInflaterSave.inflate(R.layout.assessmentsave, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewSelectedAssessment.this);
                alertDialogBuilder.setView(alertDialogViewTermSave);

                final EditText assessmentName = alertDialogViewTermSave.findViewById(R.id.assessmentDialogName);
                final EditText assessmentType = alertDialogViewTermSave.findViewById(R.id.assessmentDialogType);
                final EditText assessmentDate = alertDialogViewTermSave.findViewById(R.id.assessmentDialogDate);
                final EditText assessmentCourseId = alertDialogViewTermSave.findViewById(R.id.assessmentCourseDialogName);

                assessmentName.setText(assessment.getName());
                assessmentType.setText(assessment.getType());
                assessmentDate.setText(assessment.getDate());
                assessmentCourseId.setText(Integer.toString(assessment.getCourseId()));

                alertDialogBuilder.setCancelable(true).setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        assessment.setName(assessmentName.getText().toString());
                        assessment.setType(assessmentType.getText().toString());
                        assessment.setDate(assessmentDate.getText().toString());
                        assessment.setCourseId(Integer.parseInt(assessmentCourseId.getText().toString()));

                        db.updateAssessment(assessment);
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
                db.deleteAssessment(assessment);
                // redirect to the parent activity
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                Intent courseIntent = new Intent(context, Assessments.class);
                startActivity(courseIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
