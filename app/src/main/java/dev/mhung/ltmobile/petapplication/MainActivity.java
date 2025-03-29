package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import dev.mhung.ltmobile.petapplication.model.SwicthScreen;

public class MainActivity extends AppCompatActivity {
    Toolbar tbrManHinhChinh;
    ViewFlipper vflManHinhChinh;
    RecyclerView rcvDanhSach;
    NavigationView navMenu;
    ListView lsvManHinhChinh;
    Button btnGioiThieu, btnKhamPha;

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
    }

    private void addEvents() {
        btnGioiThieu.setOnClickListener(v -> SwicthScreen.switchScreen(MainActivity.this, Introduce.class));
        btnKhamPha.setOnClickListener(v -> SwicthScreen.switchScreen(MainActivity.this, Product.class));
    }

    private void addViews() {
        tbrManHinhChinh = findViewById(R.id.tbrManHinhChinh);
        vflManHinhChinh = findViewById(R.id.vflManHinhChinh);
        rcvDanhSach = findViewById(R.id.rcvDanhSach);
        navMenu = findViewById(R.id.navMenu);
        lsvManHinhChinh = findViewById(R.id.lsvManHinhChinh);
        btnGioiThieu = findViewById(R.id.btnGioiThieu);
        btnKhamPha = findViewById(R.id.btnKhamPha);
    }
}