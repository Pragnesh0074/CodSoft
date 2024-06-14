package com.example.dayquote;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteDisplay extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Set<String> savedQuotes;
    List<String> quoteList;
    RecyclerView recyclerView;
    RecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite_display);

        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        getWindow().setStatusBarColor(Color.BLACK);

        sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        savedQuotes = sharedPreferences.getStringSet("savedQuotesList", new HashSet<>());
        if (savedQuotes.isEmpty()) {
            Toast.makeText(this, "No favorite quotes found", Toast.LENGTH_SHORT).show();
            return;
        }
        quoteList = new ArrayList<>(savedQuotes);


        recyclerView = findViewById(R.id.quoteListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleViewAdapter = new RecycleViewAdapter(quoteList, this);
        recyclerView.setAdapter(recycleViewAdapter);

    }
}