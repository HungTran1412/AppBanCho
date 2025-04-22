package dev.mhung.ltmobile.petapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import dev.mhung.ltmobile.petapplication.database.CartDAO;
import dev.mhung.ltmobile.petapplication.model.CartItem;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;

public class DetailController extends AppCompatActivity {

    TextView txtTenThuCung,txtGiaChiTiet,txtMoTaTC,txtSoLuong;
    Button btnThemVaoGioHang;

    ImageView imgChiTiet;
    Spinner spnItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvent();

    }

    private void addEvent() {

        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price", 0); // 0 là giá trị mặc định nếu không có
        int age = getIntent().getIntExtra("age",0);
        String breed = getIntent().getStringExtra("breed");
        String imageUrl = getIntent().getStringExtra("image_url");
        String des = getIntent().getStringExtra("des");

        txtTenThuCung.setText("Tên: " + name);
        txtGiaChiTiet.setText("Giá: " + price + " VNĐ");
        txtMoTaTC.setText("Tuổi: " + age
                + "\nGiống: " + (breed != null ? breed : "Chưa rõ")
                + "\n Mô tả: " + des );

        // Load ảnh bằng Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imgChiTiet);


        btnThemVaoGioHang.setOnClickListener(v -> {
            try {
                String quantityString = txtSoLuong.getText().toString();
                int quantity = 0;
                if (!quantityString.isEmpty()) {
                    quantity = Integer.parseInt(quantityString);  // Tăng số lượng lên 1 khi người dùng nhấn nút
                }
                quantity++;
                txtSoLuong.setText(String.valueOf(quantity));

                CartItem item = new CartItem(name, price, quantity, imageUrl);

                CartDAO cartDao = new CartDAO(DetailController.this);
                CartItem existingItem = cartDao.getItemByName(name);

                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    cartDao.updateItem(existingItem);
                } else {
                    cartDao.addItem(item);
                }
                Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });



        FrameLayout frameLayout = findViewById(R.id.cartFrameLayout);

        frameLayout.setOnClickListener(v -> {
            SwitchScreen.switchScreen(DetailController.this, CartController.class);
        });

    }

    private void addViews() {
        txtSoLuong =(TextView) findViewById(R.id.txtSoLuong);
        txtTenThuCung = (TextView) findViewById(R.id.txtTenThuCung);
        txtMoTaTC = (TextView) findViewById(R.id.txtMoTaTC);
        txtGiaChiTiet = (TextView) findViewById(R.id.txtGiaChiTiet);
        btnThemVaoGioHang = (Button) findViewById(R.id.btnThemVaoGioHang);
        imgChiTiet = (ImageView) findViewById(R.id.imgChiTiet);
        spnItem = (Spinner) findViewById(R.id.spnItem);
    }

}