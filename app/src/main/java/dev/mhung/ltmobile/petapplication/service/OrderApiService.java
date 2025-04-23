package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.request.OrderRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderApiService {
    @POST("/orders")
    Call<Void> placeOrder(@Body OrderRequest orderRequest);
}
