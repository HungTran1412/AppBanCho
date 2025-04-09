package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import dev.mhung.ltmobile.petapplication.model.SwitchScreen;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar tbrManHinhChinh;
    ViewFlipper vflManHinhChinh;
    RecyclerView rcvDanhSach;
    NavigationView navMenu;
    ListView lsvManHinhChinh;
    Button btnGioiThieu, btnKhamPha;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        addEvents();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            Log.d("DEBUG", "ActionBar đã tồn tại trước khi setSupportActionBar");
        } else {
            Log.d("DEBUG", "Không có ActionBar trước khi setSupportActionBar");
        }

    }

    private void addEvents() {
        btnGioiThieu.setOnClickListener(v -> SwitchScreen.switchScreen(MainActivity.this, IntroduceController.class));
        btnKhamPha.setOnClickListener(v -> SwitchScreen.switchScreen(MainActivity.this, ProductController.class));

        // Xử lý khi bấm vào các mục trong NavigationView
        navMenu.setNavigationItemSelectedListener(this);
    }

    private void addViews() {
        tbrManHinhChinh = findViewById(R.id.tbrManHinhChinh);
        setSupportActionBar(tbrManHinhChinh); // Đặt Toolbar làm ActionBar

        vflManHinhChinh = findViewById(R.id.vflManHinhChinh);
        rcvDanhSach = findViewById(R.id.rcvDanhSach);
        navMenu = findViewById(R.id.navMenu);
        btnGioiThieu = findViewById(R.id.btnGioiThieu);
        btnKhamPha = findViewById(R.id.btnKhamPha);
        drawerLayout = findViewById(R.id.main);

        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, tbrManHinhChinh,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navMenu.setNavigationItemSelectedListener(this);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        try {
            if(id == R.id.nav_sanpham){
                SwitchScreen.switchScreen(MainActivity.this, ProductController.class);
            }else if(id == R.id.nav_gioithieu){
                SwitchScreen.switchScreen(MainActivity.this, IntroduceController.class);
            }else if(id == R.id.nav_lienhe){
                SwitchScreen.switchScreen(MainActivity.this, ContactController.class);
            } else if (id == R.id.nav_dangnhap) {
                SwitchScreen.switchScreen(MainActivity.this, LoginController.class);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Lỗi khi chuyển sang màn hình: " + e.getMessage());
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}