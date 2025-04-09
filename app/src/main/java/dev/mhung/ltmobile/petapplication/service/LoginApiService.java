package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.request.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiService {
    @POST("/admin/login")
    Call<Void> login(@Body LoginRequest request);
}
