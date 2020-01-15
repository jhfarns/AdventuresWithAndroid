package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import Database.Course;
import Database.DatabaseHelper;
import Database.RecyclerTouchListener;
import Database.Term;

public class ViewSelectedTerm extends AppCompatActivity {

    private List<Term> termsList = new ArrayList<>();
    private List<Course> coursesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;
    private String startDate;
    private String endDate;
    private String termName;
    private Term term;
    private CoursesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_term);

        recyclerView = findViewById(R.id.recyclerview);

        db = new DatabaseHelper(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("selectedTermId", 1);
        term = db.getTerm(id);


        coursesList.addAll(db.getAllAssociatedCourses(term.getId()));

        mAdapter = new CoursesAdapter(this, coursesList);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position){
                // load new activity here
                // pass the database with an intent
                Intent viewTerm = new Intent(context, ViewSelectedTermCourse.class);
                viewTerm.putExtra("selectedCourseId", coursesList.get(position).getId());
                int test = coursesList.get(position).getId();
                startActivity(viewTerm);
            }
            @Override
            public void onLongClick(View view, int position){
            }
        }));


        startDate = term.getStartdate();
        endDate = term.getEnddate();
        termName = term.getTermname();

        TextView viewStartDate = findViewById(R.id.selectedTermStartDate);
        TextView viewEndDate = findViewById(R.id.selectedTermEndDate);
        TextView viewTermName = findViewById(R.id.selectedTermName);

        viewStartDate.setText(startDate);
        viewEndDate.setText(endDate);
        viewTermName.setText(termName);
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
                LayoutInflater  layoutInflaterSave = LayoutInflater.from(getApplicationContext());
                View alertDialogViewTermSave = layoutInflaterSave.inflate(R.layout.termsave, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewSelectedTerm.this);
                alertDialogBuilder.setView(alertDialogViewTermSave);

                final EditText startDate = alertDialogViewTermSave.findViewById(R.id.editStart);
                final EditText endDate = alertDialogViewTermSave.findViewById(R.id.editEnd);
                final EditText termName = alertDialogViewTermSave.findViewById(R.id.editTermName);

                startDate.setText(term.getStartdate());
                endDate.setText(term.getEnddate());
                termName.setText(term.getTermname());

                alertDialogBuilder.setCancelable(true).setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        term.setStartdate(startDate.getText().toString());
                        term.setEnddate(endDate.getText().toString());
                        term.setTermname(termName.getText().toString());
                        db.updateTerm(term);
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

                List<Course> termCourses = new ArrayList<>();

                termCourses.addAll(db.getAllAssociatedCourses(term.getId()));

                if(termCourses.isEmpty()){
                    // delete term
                    db.deleteTerm(term);
                    // redirect to the parent activity
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show();
                    Intent courseIntent = new Intent(context, Terms.class);
                    startActivity(courseIntent);
                    return true;
                }else{
                    Toast.makeText(context, "Courses are still assigned to this term", Toast.LENGTH_LONG).show();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
