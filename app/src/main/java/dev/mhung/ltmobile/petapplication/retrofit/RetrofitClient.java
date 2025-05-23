package dev.mhung.ltmobile.petapplication.retrofit;

import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import dev.mhung.ltmobile.petapplication.service.LoginApiService;
import dev.mhung.ltmobile.petapplication.service.OrderApiService;
import dev.mhung.ltmobile.petapplication.service.ProductApiService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.81:8080/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit;
        }

        return retrofit;
    }

    public static ContactApiService getInstance(){
        getClient();
        return retrofit.create(ContactApiService.class);
    }

    public static LoginApiService login(){
        getClient();
        return retrofit.create(LoginApiService.class);
    }

    public static ProductApiService product(){
        getClient();
        return retrofit.create(ProductApiService.class);
    }

    public static OrderApiService placeOrder(){
        getClient();
        return retrofit.create(OrderApiService.class);
    }
}
