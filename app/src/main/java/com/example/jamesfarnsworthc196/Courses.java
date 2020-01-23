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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Database.Course;
import Database.DatabaseHelper;
import Database.RecyclerTouchListener;
import Database.Term;

public class Courses extends AppCompatActivity {

    private CoursesAdapter mAdapter;
    private List<Course> coursesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;
    private List<Term> termsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_recyclerview);

        recyclerView = findViewById(R.id.recyclerview);

        db = new DatabaseHelper(this);

        coursesList.addAll(db.getAllCourses());

        mAdapter = new CoursesAdapter(this, coursesList);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position){
                // load new activity here
                // pass the database with an intent
                Intent viewTerm = new Intent(context, ViewSelectedCourse.class);
                viewTerm.putExtra("selectedCourseId", coursesList.get(position).getId());
                int test = coursesList.get(position).getId();
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
        termsList.addAll(db.getAllTerms());
        ListIterator<Term> termsListIterator = termsList.listIterator();
        List<Integer> termIds = new ArrayList<>();
        int largestTermId = 0;
        ListIterator<Integer> termIdsIterator;

        switch(item.getItemId()){
            case R.id.create:
                LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                View view = layoutInflater.inflate(R.layout.coursesave,null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Courses.this);
                alertDialogBuilder.setView(view);

                final EditText startDate = view.findViewById(R.id.editCourseStart);
                final EditText endDate = view.findViewById(R.id.editCourseEnd);
                final EditText courseName = view.findViewById(R.id.editCourseName);
                final EditText courseStatus = view.findViewById(R.id.editCourseStatus);
                final EditText courseNote = view.findViewById(R.id.editCourseNote);
                final EditText courseTermId = view.findViewById(R.id.editCourseTermId);


                while(termsListIterator.hasNext()){
                    Term term = termsListIterator.next();
                    termIds.add(term.getId());
                }
                termIdsIterator = termIds.listIterator();

                while(termIdsIterator.hasNext()){
                  int nextVal = termIdsIterator.next();
                    if( nextVal > largestTermId){
                        largestTermId = nextVal;
                    }
                }

                courseName.setText("Course Name");
                startDate.setText("Start Date");
                endDate.setText("End Date");
                courseStatus.setText("Status");
                courseNote.setText("I am a note");
                courseTermId.setText("Choose term ID between " +termIds.get(0)+" " + largestTermId);

                alertDialogBuilder.setCancelable(true).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Course newCourse = new Course();
                        newCourse.setStartdate(startDate.getText().toString());
                        newCourse.setEnddate(endDate.getText().toString());
                        newCourse.setCoursename(courseName.getText().toString());
                        newCourse.setStatus(courseStatus.getText().toString());
                        newCourse.setNote(courseNote.getText().toString());
                        newCourse.setTermid(Integer.parseInt(courseTermId.getText().toString()));
                        db.insertCourse(newCourse.getStartdate(), newCourse.getEnddate(), newCourse.getCoursename(), newCourse.getNote(), newCourse.getStatus(), newCourse.getTermid());

                        AlarmManager alarm;
                        Intent alarmIntent;
                        PendingIntent pendingIntent;
                        // You may need to add these _ids to the database with the class so that it can be canceled...
                        // You are going to need to make two of these ids because of the start and end date...
                        final int _id = (int) System.currentTimeMillis();
                        // for the calendar you are going

                        alarmIntent = new Intent(Courses.this, CreateCatcher.class);
                        alarmIntent.putExtra("createBroadcastAlarm", "create");
                        pendingIntent = PendingIntent.getBroadcast(Courses.this, _id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+1000, pendingIntent);

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
