package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Database.Assessment;
import Database.Course;
import Database.DatabaseHelper;
import Database.RecyclerTouchListener;
import Database.Term;

public class Assessments extends AppCompatActivity {

    private AssessmentsAdapter mAdapter;
    private List<Assessment> assessmentsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_recyclerview);

        recyclerView = findViewById(R.id.recyclerview);

        db = new DatabaseHelper(this);

        assessmentsList.addAll(db.getAllAssessments());

        mAdapter = new AssessmentsAdapter(this, assessmentsList);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position){
                // load new activity here
                // pass the database with an intent
                Intent viewTerm = new Intent(context, ViewSelectedAssessment.class);
                viewTerm.putExtra("selectedAssessmentId", assessmentsList.get(position).getId());
                int test = assessmentsList.get(position).getId();
                startActivity(viewTerm);
            }
            @Override
            public void onLongClick(View view, int position){
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_terms, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.create:
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View assessmentView = layoutInflater.inflate(R.layout.assessmentsave,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Assessments.this);
                alertDialogBuilder.setView(assessmentView);

                final EditText assessmentName = assessmentView.findViewById(R.id.assessmentDialogName);
                final EditText assessmentType = assessmentView.findViewById(R.id.assessmentDialogType);
                final EditText assessmentDate = assessmentView.findViewById(R.id.assessmentDialogDate);
                final EditText assessmentCourseId = assessmentView.findViewById(R.id.assessmentCourseDialogName);

                assessmentName.setText("Assesment Name");
                assessmentType.setText("Objective or Performance");
                assessmentDate.setText("SomeDate");
                assessmentCourseId.setText("1");

                alertDialogBuilder.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Assessment assessment = new Assessment();
                        assessment.setName(assessmentName.getText().toString());
                        assessment.setType(assessmentType.getText().toString());
                        assessment.setDate(assessmentDate.getText().toString());
                        assessment.setCourseId(Integer.parseInt(assessmentCourseId.getText().toString()));
                        db.insertAssessment(assessment.getType(),assessment.getName(),assessment.getDate(),assessment.getCourseId());
                        finish();
                        startActivity(getIntent());
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
