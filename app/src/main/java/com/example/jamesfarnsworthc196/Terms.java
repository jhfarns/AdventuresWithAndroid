package com.example.jamesfarnsworthc196;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.DatabaseHelper;
import Database.RecyclerTouchListener;
import Database.Term;

public class Terms extends AppCompatActivity {
    private TermsAdapter mAdapter;
    private List<Term> termsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_recyclerview);

        recyclerView = findViewById(R.id.recyclerview);

        db = new DatabaseHelper(this);

        termsList.addAll(db.getAllTerms());

        mAdapter = new TermsAdapter(this, termsList);
        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position){
                // load new activity here
                // pass the database with an intent
                Intent viewTerm = new Intent(context, ViewSelectedTerm.class);
                viewTerm.putExtra("selectedTermId", termsList.get(position).getId());
                startActivity(viewTerm);
            }
            @Override
            public void onLongClick(View view, int position){
            }
        }));

    }
}
