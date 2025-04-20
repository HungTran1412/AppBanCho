package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
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

import dev.mhung.ltmobile.petapplication.model.CheckEmail;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;
import dev.mhung.ltmobile.petapplication.request.LoginRequest;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.LoginApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Button btnLoginLogin;
    TextView txtLoginEmail, txtLoginPassword;
    Toolbar toolbar;
    NavigationView navMenu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        addEvents();
    }

    private void addEvents() {
        btnLoginLogin.setOnClickListener(v->{
            Log.d("RetrofitClient", "Bắt đầu gọi API...");
            //kiem tra email va password
            String email = txtLoginEmail.getText().toString();
            String password = txtLoginPassword.getText().toString();

            LoginApiService service = RetrofitClient.login();
            if (service == null) {
                Log.e("RetrofitClient", "LỖI: Retrofit chưa được khởi tạo!");
                return;
            }

            if(email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Vui long nhap thong tin", Toast.LENGTH_SHORT).show();
            else if(!CheckEmail.emailCheck(email))
                Toast.makeText(this, "Email khong hop le", Toast.LENGTH_SHORT).show();

            LoginRequest request = new LoginRequest(email, password);
            service.login(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginController.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                        SwitchScreen.switchScreen(LoginController.this, AdministrationController.class);
                        Log.d("RetrofitClient", "Đăng nhập thành công!");
                    } else {
                        Toast.makeText(LoginController.this, "Lỗi đăng nhập! Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("RetrofitClient", "Lỗi đăng nhập! Mã lỗi: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(LoginController.this, "Lỗi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("RetrofitClient", "Lỗi API: " + t.getMessage());
                }
            });
        });
    
        navMenu.setNavigationItemSelectedListener(this);
//        btnLoginHome.setOnClickListener(v -> SwitchScreen.switchScreen(LoginController.this, MainActivity.class));
    }

    private void addViews() {
//        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        try {
            if(id == R.id.nav_sanpham){
                SwitchScreen.switchScreen(LoginController.this, ProductController.class);
            }else if(id == R.id.nav_gioithieu){
                SwitchScreen.switchScreen(LoginController.this, IntroduceController.class);
            }else if(id == R.id.nav_trangchu){
                SwitchScreen.switchScreen(LoginController.this, MainActivity.class);
            } else if (id == R.id.nav_lienhe) {
                SwitchScreen.switchScreen(LoginController.this, ContactController.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Lỗi khi chuyển sang màn hình: " + e.getMessage());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}