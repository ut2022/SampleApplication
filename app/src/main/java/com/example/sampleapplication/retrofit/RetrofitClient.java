package com.example.sampleapplication.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance=null ;
    private Api myApi;

    private RetrofitClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        myApi = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getRetrofitInstance(){
        if(instance==null){
            instance=new RetrofitClient();
        }
        return instance;
    }

    public Api getMyApi(){
        return myApi;
    }
}
