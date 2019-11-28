package com.devmohamedibrahim1997.populartest.network;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.devmohamedibrahim1997.populartest.utils.Constant.API_KEY;
import static com.devmohamedibrahim1997.populartest.utils.Constant.BASE_URL;

public class APIClient {


    public static Retrofit getInstance(){

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    HttpUrl httpUrl = original.url().newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();

                    Request request = original.newBuilder()
                            .url(httpUrl).build();

                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
