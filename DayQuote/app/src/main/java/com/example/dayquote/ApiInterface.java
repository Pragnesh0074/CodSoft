package com.example.dayquote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("today")
    Call<List<QuoteModel>> getQuote();

}
