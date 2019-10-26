package com.devmohamedibrahim1997.populartest.NetWork;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.devmohamedibrahim1997.populartest.Utils.Constant.BASE_URL;
import static com.devmohamedibrahim1997.populartest.Utils.Constant.CONNECTION_TIMEOUT;
import static com.devmohamedibrahim1997.populartest.Utils.Constant.READ_TIMEOUT;
import static com.devmohamedibrahim1997.populartest.Utils.Constant.WRITE_TIMEOUT;

public class APIClient {



    public static Retrofit getInstance(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();


        Retrofit.Builder builder = new Retrofit.Builder();

        return builder
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
