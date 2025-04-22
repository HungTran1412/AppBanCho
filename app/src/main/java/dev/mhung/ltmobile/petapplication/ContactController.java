package dev.mhung.ltmobile.petapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

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
import dev.mhung.ltmobile.petapplication.request.ContactRequest;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactController extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button btnCTGui, btnCTGoi;
    EditText txtCTHoTen, txtCTEmail, txtCTSDT, txtCTContent, txtCTDiaChi;
    TextView lblCTHotLine;
    Toolbar tbrManHinhChinh;
    NavigationView navMenu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        addEvents();
    }

    private void addEvents() {
        btnCTGui.setOnClickListener(v -> sendContact());
        btnCTGoi.setOnClickListener(v -> {
            String hotLine = lblCTHotLine.getText().toString();
            for(int i = 0; i < hotLine.length(); i++){
                if(hotLine.charAt(i) == ':'){
                    hotLine = hotLine.substring(i + 1);
                    break;
                }
            }
//            Toast.makeText(this, hotLine, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));
            startActivity(intent);
        });
        navMenu.setNavigationItemSelectedListener(this);
    }

    private void addViews() {
        btnCTGui = findViewById(R.id.btnCTGui);
        btnCTGoi = (Button)findViewById(R.id.btnCTGoi);

        lblCTHotLine = (TextView)findViewById(R.id.lblCTHotLine);

        txtCTHoTen = findViewById(R.id.txtCTHoTen);
        txtCTEmail = findViewById(R.id.txtCTEmail);
        txtCTSDT = findViewById(R.id.txtCTSDT);
        txtCTContent = findViewById(R.id.txtCTContent);
        txtCTDiaChi = findViewById(R.id.txtCTDiaChi);

        navMenu = findViewById(R.id.navMenu);
        drawerLayout = findViewById(R.id.drawer_layout);

        tbrManHinhChinh = findViewById(R.id.tbrManHinhChinh);
        setSupportActionBar(tbrManHinhChinh);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, tbrManHinhChinh,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void sendContact() {
        Log.d("RetrofitClient", "Bắt đầu gọi API...");
        String hoTen = txtCTHoTen.getText().toString();
        String email = txtCTEmail.getText().toString();
        String sdt = txtCTSDT.getText().toString();
        String diaChi = txtCTDiaChi.getText().toString();
        String content = txtCTContent.getText().toString();

        ContactApiService apiService = RetrofitClient.getInstance();
        if (apiService == null) {
            Log.e("RetrofitClient", "LỖI: Retrofit chưa được khởi tạo!");
            return;
        }

        if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!CheckEmail.emailCheck(email)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }

        ContactRequest request = new ContactRequest(hoTen, email, sdt, diaChi, content);
        apiService.sendContact(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ContactController.this, "Gửi dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                    Log.d("RetrofitClient", "Gửi liên hệ thành công!");
                } else {
                    Toast.makeText(ContactController.this, "Lỗi gửi liên hệ! Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("RetrofitClient", "Lỗi gửi liên hệ! Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ContactController.this, "Lỗi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RetrofitClient", "Lỗi API: " + t.getMessage());
            }
        });

        clearTextBox();
    }

    private void clearTextBox() {
        txtCTHoTen.setText("");
        txtCTEmail.setText("");
        txtCTSDT.setText("");
        txtCTContent.setText("");
        txtCTDiaChi.setText("");
        txtCTHoTen.requestFocus();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        try {
            if(id == R.id.nav_sanpham){
                SwitchScreen.switchScreen(ContactController.this, ProductController.class);
            }else if(id == R.id.nav_gioithieu){
                SwitchScreen.switchScreen(ContactController.this, IntroduceController.class);
            }else if(id == R.id.nav_trangchu){
                SwitchScreen.switchScreen(ContactController.this, MainActivity.class);
            } else if (id == R.id.nav_dangnhap) {
                SwitchScreen.switchScreen(ContactController.this, LoginController.class);
            }else if(id == R.id.nav_cart){
                SwitchScreen.switchScreen(ContactController.this, CartController.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Lỗi khi chuyển sang màn hình: " + e.getMessage());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}