package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private List<Course> courseList = new ArrayList<>();

    public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

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
        courseList.addAll(db.getAllCourses());
        ListIterator<Course> termsListIterator = courseList.listIterator();
        List<Integer> termIds = new ArrayList<>();
        int largestTermId = 0;
        ListIterator<Integer> termIdsIterator;

        switch(item.getItemId()){
            case R.id.create:
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View assessmentView = layoutInflater.inflate(R.layout.assessmentsave,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Assessments.this);
                alertDialogBuilder.setView(assessmentView);

                while(termsListIterator.hasNext()){
                     Course course = termsListIterator.next();
                    termIds.add(course.getId());
                }
                termIdsIterator = termIds.listIterator();

                while(termIdsIterator.hasNext()){
                    int nextVal = termIdsIterator.next();
                    if( nextVal > largestTermId){
                        largestTermId = nextVal;
                    }
                }

                final int firstID = termIds.get(0);
                final Calendar c = Calendar.getInstance();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                String currentTime = sdf.format(c.getTime());

                final EditText assessmentName = assessmentView.findViewById(R.id.assessmentDialogName);
                final EditText assessmentType = assessmentView.findViewById(R.id.assessmentDialogType);
                final EditText assessmentDate = assessmentView.findViewById(R.id.assessmentDialogDate);
                final EditText assessmentEndDate = assessmentView.findViewById(R.id.assessmentDialogFinishDate);
                final EditText assessmentCourseId = assessmentView.findViewById(R.id.assessmentCourseDialogName);

                assessmentName.setText("Assesment Name");
                assessmentType.setText("Objective or Performance");
                assessmentDate.setText(currentTime);
                assessmentEndDate.setText(currentTime);
                assessmentCourseId.setText("Choose course ID between " +termIds.get(0)+" " + largestTermId);

                alertDialogBuilder.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Assessment assessment = new Assessment();
                        assessment.setName(assessmentName.getText().toString());
                        assessment.setType(assessmentType.getText().toString());
                        assessment.setDate(assessmentDate.getText().toString());
                        assessment.setEndDate(assessmentEndDate.getText().toString());


                        if (isNumeric(assessmentCourseId.getText().toString())) {
                            assessment.setCourseId(Integer.parseInt(assessmentCourseId.getText().toString()));
                        } else {
                            assessment.setCourseId(firstID);
                        }

                        db.insertAssessment(assessment.getType(),assessment.getName(),assessment.getDate(),assessment.getEndDate(),assessment.getCourseId());

                        String startTimeSet = assessmentDate.getText().toString();
                        String endTimeSet = assessmentEndDate.getText().toString();

                        try {
                            c.setTime(sdf.parse(startTimeSet));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        AlarmManager alarm;
                        Intent alarmIntent;
                        PendingIntent pendingIntent;
                        // You may need to add these _ids to the database with the class so that it can be canceled...
                        // You are going to need to make two of these ids because of the start and end date...
                        int _id = (int) System.currentTimeMillis();
                        // for the calendar you are going

                        alarmIntent = new Intent(Assessments.this, CreateCatcher.class);
                        alarmIntent.putExtra("createBroadcastAlarm", assessment.getName() + " Goal date is due today!");
                        pendingIntent = PendingIntent.getBroadcast(Assessments.this, _id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                        _id = (int) System.currentTimeMillis();
                        alarmIntent = new Intent(Assessments.this, CreateCatcher.class);
                        alarmIntent.putExtra("createBroadcastAlarm", assessment.getName() + " is ending today");
                        pendingIntent = PendingIntent.getBroadcast(Assessments.this,_id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        try {
                            c.setTime(sdf.parse(endTimeSet));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

                        Toast.makeText(context, "A notification has been created for your assessment start and end time", Toast.LENGTH_LONG).show();


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
