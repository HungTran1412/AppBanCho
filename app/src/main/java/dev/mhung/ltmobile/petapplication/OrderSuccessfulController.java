package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.ProductAdapter;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;
import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ProductApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSuccessfulController extends AppCompatActivity {

    Button btnCart, btnHome;
    ListView lsvOSCProd;
    ProductAdapter adapter;
    List<ProductResponse> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_successful);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchScreen.switchScreen(OrderSuccessfulController.this, ContactController.class);
            }
        });

        btnHome.setOnClickListener(v->{SwitchScreen.switchScreen(OrderSuccessfulController.this, MainActivity.class);});
        loadProducts();
    }

    private void addViews() {
        btnCart = findViewById(R.id.btnCart);
        btnHome = findViewById(R.id.btnHome);
        lsvOSCProd = findViewById(R.id.lsvOSCProd);
    }

    private void loadProducts(){
        ProductApiService api = RetrofitClient.product();

        api.getAllProducts().enqueue(new Callback<ProductApiRespone>() {
            @Override
            public void onResponse(Call<ProductApiRespone> call, Response<ProductApiRespone> response) {
                if(response.isSuccessful()){
                    productList = response.body().getProduct();
                    adapter = new ProductAdapter(OrderSuccessfulController.this, productList);
                    lsvOSCProd.setAdapter(adapter);
                }else {
                    Log.e("API Error", "Code: " + response.code());
                    Toast.makeText(OrderSuccessfulController.this, "Lỗi khi lấy sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductApiRespone> call, Throwable t) {
                Toast.makeText(OrderSuccessfulController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}