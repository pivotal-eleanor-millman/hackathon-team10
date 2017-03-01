package com.example.pivotal.boomerang_pivotal;

import com.example.pivotal.boomerang_pivotal.model.Opportunity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEndpointInterface {

    @POST("/opportunity")
    Call<ResponseBody> createOpportunity(@Body Opportunity opportunity);

}
