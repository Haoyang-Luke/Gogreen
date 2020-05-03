package com.example.gogreen.map.Remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IGoogleAPI {

    //new retrofit to get info  from google api

    @GET
    Call<String> getPath(@Url String url);

}
