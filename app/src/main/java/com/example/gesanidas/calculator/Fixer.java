package com.example.gesanidas.calculator;

/**
 * Created by gesanidas on 8/5/2017.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Fixer
{
    @GET("latest")
    Call<com.example.gesanidas.calculator.models.Rate> getRate(@Query("base") String strTest);


}
