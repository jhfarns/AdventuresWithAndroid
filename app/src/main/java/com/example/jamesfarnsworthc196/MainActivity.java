package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openTerm(View view) {
        Intent termIntent = new Intent(this, Terms.class);
        startActivity(termIntent);
    }

    public void openCourse(View view) {
        Intent courseIntent = new Intent(this, Courses.class);
        startActivity(courseIntent);
    }

    public void openAssessment(View view) {
        Intent assessmentIntent = new Intent(this, Assessments.class);
        startActivity(assessmentIntent);
    }

}
