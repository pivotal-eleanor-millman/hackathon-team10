package com.example.pivotal.boomerang_pivotal.service;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndpointService {

    @POST("/opportunity")
    Call<ResponseBody> createOpportunity(@Body Opportunity opportunity);

    @GET("/nearby")
    Call<Opportunity> getNearestOpportunity(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("radius") int radius
            );

    @GET("/opportunities")
    Call<List<Opportunity>> getAllOpportunities();

}
