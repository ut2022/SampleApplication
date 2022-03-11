package com.example.sampleapplication.retrofit;

import com.example.sampleapplication.login.model.LoginResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL="https://mocki.io/v1/";
    @GET("a67abe3b-c4e5-4402-a0a2-fc04466252d8")
    Call<List<LoginResults>> getData();
}
