package dev.mhung.ltmobile.petapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.Gson;

import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.ProductAdapter;
import dev.mhung.ltmobile.petapplication.adapter.ProductAdapter1;
import dev.mhung.ltmobile.petapplication.model.Product;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;
import dev.mhung.ltmobile.petapplication.request.ProductRequest;
import dev.mhung.ltmobile.petapplication.response.ProductApiRespone;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ProductApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProductController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView lsvDanhSach;

    static public int state=0;

    Button Add;
    ProductAdapter1 adapter;
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
        setContentView(R.layout.activity_update_product);
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
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state=1;
                if(state==1) {
                    final Dialog g = new Dialog(v.getContext());
                    g.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    g.setContentView(R.layout.dialog_layout);

                    Window window = g.getWindow();
                    if (window == null) return;
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    //  window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                    WindowManager.LayoutParams win = window.getAttributes();
                    win.gravity = Gravity.CENTER;
                    window.setAttributes(win);
                    g.show();

                    Button close=g.findViewById(R.id.btnClose);
                    Button update=g.findViewById(R.id.btnUpdate);
                    EditText Name=g.findViewById(R.id.editName);
                    EditText Id=g.findViewById(R.id.editId);
                    EditText Age=g.findViewById(R.id.editAge);
                    EditText Breed=g.findViewById(R.id.editBreed);
                    EditText Color=g.findViewById(R.id.editColor);
                    EditText Size=g.findViewById(R.id.editSize);
                    EditText Img=g.findViewById(R.id.editImg);
                    EditText Description=g.findViewById(R.id.editDescription);
                    EditText Quantity=g.findViewById(R.id.editQuantity);
                    EditText Price=g.findViewById(R.id.editPrice);
                    update.setText("Thêm");


                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(state==1){
                                //int id =Id.getText()!=null? Integer.parseInt(Id.getText().toString()):67676;
                                String name = Name.getText().toString();
                                int age = Integer.parseInt(Age.getText().toString());
                                int price = Integer.parseInt(Price.getText().toString());

                                int quantity = Integer.parseInt(Quantity.getText().toString());

                                String size = Size.getText().toString();

                                String color = Color.getText().toString();
                                String img = Img.getText().toString();
                                String description = Description.getText().toString();
                                String breed = Breed.getText().toString();
                                Product a = new Product(name, age, price, quantity, size, color, img, description, breed);
                                Log.d("ProductRequest", new Gson().toJson(a));

                                ProductApiService api = RetrofitClient.product();
                                if (api == null) {
                                    Log.e("RetrofitClient", "LỖI: Retrofit chưa được khởi tạo!");
                                    return;
                                }

                                api.addProduct(a).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> Call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(UpdateProductController.this, "Thêm dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                                            Log.d("RetrofitClient", "Thêm thành công!");
                                            Name.setText("");
                                            Age.setText("");
                                            Color.setText("");
                                            Breed.setText("");
                                            Size.setText("");
                                            Quantity.setText("");
                                            Img.setText("");
                                            Description.setText("");
                                            Price.setText("");
                                            loadProducts();
                                            //

                                        } else {
                                            Toast.makeText(UpdateProductController.this, " Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                                            Log.e("RetrofitClient", "Lỗi  " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.print(t.getMessage());
                                        Toast.makeText(UpdateProductController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                            }
                        }
                    });
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            g.dismiss();
                        }
                    });
                }


            }
        });
    }

    private void addViews() {
        Add=findViewById(R.id.btnAdd);
        lsvDanhSach = (ListView)findViewById(R.id.lsvDanhSach);
        // imgProduct = (ImageView)findViewById(R.id.imgProduct);
//        adapter = new ProductAdapter1(this, productList);
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
                    adapter = new ProductAdapter1(UpdateProductController.this, productList);
                    lsvDanhSach.setAdapter(adapter);

                }else{
                    Log.e("API Error", "Code: " + response.code());
                    Toast.makeText(UpdateProductController.this, "Lỗi khi lấy sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductApiRespone> call, Throwable t) {
                System.out.print(t.getMessage());
                Toast.makeText(UpdateProductController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        try {
            if(id == R.id.nav_dangnhap){
                SwitchScreen.switchScreen(UpdateProductController.this, LoginController.class);
            }else if(id == R.id.nav_trangchu){
                SwitchScreen.switchScreen(UpdateProductController.this, MainActivity.class);
            } else if (id == R.id.nav_lienhe) {
                SwitchScreen.switchScreen(UpdateProductController.this, ContactController.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Lỗi khi chuyển sang màn hình: " + e.getMessage());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}