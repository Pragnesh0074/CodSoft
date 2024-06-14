package com.example.dayquote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitInstance {

    static RetroFitInstance retroFitInstance;
    String api = "https://zenquotes.io/api/";
    ApiInterface apiInterface;

    RetroFitInstance() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static RetroFitInstance getInstance() {
        if(retroFitInstance == null) {
            retroFitInstance = new RetroFitInstance();
        }
        return retroFitInstance;
    }

}
