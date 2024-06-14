package com.example.dayquote;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<QuoteModel> quoteModels;
    TextView quoteText, authorName;
    ProgressBar progressBar;
    Button goBtn, shareBtn, cpyBtn, favBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;
    List<String> savedQuotesList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        getWindow().setStatusBarColor(Color.BLACK);

        quoteText = findViewById(R.id.quoteText);
        authorName = findViewById(R.id.authorName);
        progressBar = findViewById(R.id.progressBar);
        goBtn = findViewById(R.id.goBtn);
        shareBtn = findViewById(R.id.shareBtn);
        cpyBtn = findViewById(R.id.cpyBtn);
        favBtn = findViewById(R.id.favBtn);

        sharedPreferences = getSharedPreferences("myData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        savedQuotesList = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        RetroFitInstance.getInstance().apiInterface.getQuote().enqueue(new Callback<List<QuoteModel>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<QuoteModel>> call, @NonNull Response<List<QuoteModel>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                quoteModels = response.body();
                assert quoteModels != null;
                quoteText.setText(quoteModels.get(0).getQ());
                authorName.setText("~ "+quoteModels.get(0).getA());

            }

            @Override
            public void onFailure(@NonNull Call<List<QuoteModel>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        cpyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(quoteModels.get(0).getQ());
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(quoteModels.get(0).getQ());
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuote(quoteModels.get(0).getQ());
            }
        });

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FavoriteDisplay.class);
                startActivity(i);
            }
        });

    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Today Quote", text);
        clipboard.setPrimaryClip(clip);
    }

    private void shareText(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Share Text"));
    }

    private void saveQuote(String text) {
        if(!savedQuotesList.contains(text)) {
            savedQuotesList.add(text);
            Set<String> quotesSet = new HashSet<>(savedQuotesList);
            editor.putStringSet("savedQuotesList", quotesSet);
            editor.commit();
            Toast.makeText(this, "Quote Added To Favorite", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Already In Favorite", Toast.LENGTH_SHORT).show();
        }
    }
}