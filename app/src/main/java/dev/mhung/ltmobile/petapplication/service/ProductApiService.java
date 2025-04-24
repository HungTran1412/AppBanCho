package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApiService {
    @GET("/products/")
    Call<ProductApiRespone> getAllProducts();

    @GET("/products/?limit=4")
    Call<ProductApiRespone> getProducts();

}
