package dev.mhung.ltmobile.petapplication.retrofit;

import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import dev.mhung.ltmobile.petapplication.service.LoginApiService;
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
}
