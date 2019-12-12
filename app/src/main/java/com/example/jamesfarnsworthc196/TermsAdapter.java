package com.example.jamesfarnsworthc196;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Database.Term;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.MyViewHolder> {

    private Context context;
    private List<Term> termsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView termName;
            public TextView start;
            public TextView end;

            public MyViewHolder(View view){
                super(view);
                termName = view.findViewById(R.id.termName);
                start = view.findViewById(R.id.start);
                end = view.findViewById(R.id.end);
            }
    }

    public TermsAdapter(Context context, List<Term> termsList) {
        this.context = context;
        this.termsList = termsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_terms, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Term term = termsList.get(position);
        holder.termName.setText(term.getTermname());
        holder.start.setText(term.getStartdate());
        holder.end.setText(term.getEnddate());
    }

    @Override
    public int getItemCount(){
        return termsList.size();
    }
}
