package dev.mhung.ltmobile.petapplication.service;

import dev.mhung.ltmobile.petapplication.model.Product;
import dev.mhung.ltmobile.petapplication.request.ProductRequest;
import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductApiService {
    @GET("/products/")
    Call<ProductApiRespone> getAllProducts();

    @POST("/products/create")
    Call<Void> addProduct(@Body Product add);

    @PUT("/products/update/{id}")
    Call<ProductResponse> updateProduct(@Path("id") int userId, @Body ProductRequest requestUpdate);

    @DELETE("/products/delete/{id}")
    Call<Void> deleteProduct(@Path("id") int userId);
}
