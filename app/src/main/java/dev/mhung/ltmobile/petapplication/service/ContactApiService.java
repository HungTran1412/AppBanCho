package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.request.ContactRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ContactApiService {
    @POST("/contacts")
    Call<Void> sendContact(@Body ContactRequest request);
}
