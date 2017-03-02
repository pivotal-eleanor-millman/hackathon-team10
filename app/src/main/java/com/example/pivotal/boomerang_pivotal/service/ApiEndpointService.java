package com.example.pivotal.boomerang_pivotal.service;

import com.example.pivotal.boomerang_pivotal.model.OpportunitiesResponse;
import com.example.pivotal.boomerang_pivotal.model.Opportunity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpointService {

    @POST("/opportunities")
    Call<Opportunity> createOpportunity(@Body Opportunity opportunity);

    @PUT("/opportunities/{id}")
    Call<Opportunity> updateOpportunity(@Body Opportunity opportunity, @Path("id") int id);

    @GET("/nearby")
    Call<Opportunity> getNearestOpportunity(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("radius") int radius
            );

    @GET("/opportunities")
    Call<OpportunitiesResponse> getAllOpportunities();
}

