package dev.mhung.ltmobile.petapplication.service;

import java.util.List;

import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApiService {
    @GET("/products/")
    Call<ProductApiRespone> getAllProducts();
}
