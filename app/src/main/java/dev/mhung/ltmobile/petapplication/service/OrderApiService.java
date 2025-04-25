package dev.mhung.ltmobile.petapplication.service;

import java.util.List;

import dev.mhung.ltmobile.petapplication.request.OrderRequest;
import dev.mhung.ltmobile.petapplication.response.AllOrderRespsonse;
import dev.mhung.ltmobile.petapplication.response.OrderResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApiService {
    @POST("/orders")
    Call<Void> placeOrder(@Body OrderRequest orderRequest);

    @GET("/admin/order/today")
    Call<OrderResponse> getTodayOrder();

    @GET("admin/order/month")
    Call<OrderResponse> getMonthOrder();

    @GET("admin/order/year")
    Call<OrderResponse> getYearOrder();

    @GET("/admin/order")
    Call<List<AllOrderRespsonse>> getAllOrder();

    @DELETE("/admin/order/{orderId}")
    Call<Void> deleteOrder(@Path("orderId") String orderId);
}
