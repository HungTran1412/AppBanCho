package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

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

public class ProductController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ListView lsvDanhSach;
    ProductAdapter adapter;
    List<ProductResponse> productList;
    ImageView imgProduct;
    Toolbar toolbar;
    NavigationView navMenu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

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
        addEvents();
        loadProducts();
    }

    private void addEvents() {
        navMenu.setNavigationItemSelectedListener(this);
    }

    private void addViews() {
        lsvDanhSach = (ListView)findViewById(R.id.lsvDanhSach);
        imgProduct = (ImageView)findViewById(R.id.imgProduct);
//        adapter = new ProductAdapter(this, productList);
//        lsvDanhSach.setAdapter(adapter);
        navMenu = findViewById(R.id.navMenu);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        try {
            if(id == R.id.nav_dangnhap){
                SwitchScreen.switchScreen(ProductController.this, LoginController.class);
            }else if(id == R.id.nav_gioithieu){
                SwitchScreen.switchScreen(ProductController.this, IntroduceController.class);
            }else if(id == R.id.nav_trangchu){
                SwitchScreen.switchScreen(ProductController.this, MainActivity.class);
            } else if (id == R.id.nav_lienhe) {
                SwitchScreen.switchScreen(ProductController.this, ContactController.class);
            }else if(id == R.id.nav_cart){
                SwitchScreen.switchScreen(ProductController.this, CartController.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Lỗi khi chuyển sang màn hình: " + e.getMessage());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}