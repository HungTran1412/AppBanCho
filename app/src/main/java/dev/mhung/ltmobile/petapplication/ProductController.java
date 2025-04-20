package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.ProductAdapter;
import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ProductApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductController extends AppCompatActivity {

    private ListView lsvDanhSach;
    private ProductAdapter adapter;
    private List<ProductResponse> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        loadProducts();
    }

    private void addViews() {
        lsvDanhSach = (ListView)findViewById(R.id.lsvDanhSach);
//        adapter = new ProductAdapter(this, productList);
//        lsvDanhSach.setAdapter(adapter);
    }

    private void loadProducts() {
        ProductApiService api = RetrofitClient.product();

        api.getAllProducts().enqueue(new Callback<ProductApiRespone>() {
            @Override
            public void onResponse(Call<ProductApiRespone> call, Response<ProductApiRespone> response) {
                if (response.isSuccessful()) {
                    productList = response.body().getProduct();
                    adapter = new ProductAdapter(ProductController.this, productList);
                    lsvDanhSach.setAdapter(adapter);
                }else{
                    Log.e("API Error", "Code: " + response.code());
                    Toast.makeText(ProductController.this, "Lỗi khi lấy sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductApiRespone> call, Throwable t) {
                Toast.makeText(ProductController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}