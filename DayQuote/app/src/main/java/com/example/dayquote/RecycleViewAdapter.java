package com.example.dayquote;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.QuoteHolder> {

    private List<String> quoteList;
    private Activity activity;

    public RecycleViewAdapter(List<String> quoteList, Activity activity) {
        this.quoteList = quoteList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.QuoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_tile, parent, false);
        return new QuoteHolder(view, quoteList, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.QuoteHolder holder, int position) {
        holder.favQuote.setText(quoteList.get(position));
    }

    @Override
    public int getItemCount() {
        return quoteList.size();
    }

    public class QuoteHolder extends RecyclerView.ViewHolder {

        TextView favQuote;
        Button share, delete;
        List<String> quoteList;
        Activity activity;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor  editor;

        public QuoteHolder(@NonNull View itemView , List<String> quoteList, Activity activity) {
            super(itemView);
            this.quoteList = quoteList;
            this.activity = activity;
            favQuote = itemView.findViewById(R.id.favQuote);
            share = itemView.findViewById(R.id.favShareBtn);
            delete = itemView.findViewById(R.id.favDeleteBtn);
            sharedPreferences = activity.getSharedPreferences("myData", MODE_PRIVATE);
            editor = sharedPreferences.edit();


            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, quoteList.get(getAdapterPosition()));
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(Intent.createChooser(shareIntent, "Share Text"));
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quoteList.remove(getAdapterPosition());
                    Set<String> quotesSet = new HashSet<>(quoteList);
                    editor.putStringSet("savedQuotesList", quotesSet);
                    editor.commit();
                    Toast.makeText(activity, "Quote Removed", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), quoteList.size());
                }
            });

        }

    }
}
