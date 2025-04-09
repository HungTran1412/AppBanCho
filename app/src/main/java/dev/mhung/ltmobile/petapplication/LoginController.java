package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.mhung.ltmobile.petapplication.model.CheckEmail;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;
import dev.mhung.ltmobile.petapplication.request.LoginRequest;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.LoginApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController extends AppCompatActivity {

    Button btnLoginHome, btnLoginLogin;
    TextView txtLoginEmail, txtLoginPassword;

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

        btnLoginHome.setOnClickListener(v -> SwitchScreen.switchScreen(LoginController.this, MainActivity.class));
    }

    private void addViews() {
        btnLoginHome = findViewById(R.id.btnLoginHome);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
    }


}