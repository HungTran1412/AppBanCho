package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.CartAdapter;
import dev.mhung.ltmobile.petapplication.database.CartDAO;
import dev.mhung.ltmobile.petapplication.model.CartItem;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;

public class CartController extends AppCompatActivity {
    TextView txtTongTien;
    RecyclerView recyclerViewGioHang;
    Button btnDatHang;
    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private CartDAO cartDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addViews();
        addEvent();
    }

    private void addEvent() {
        cartDao = new CartDAO(this);
        cartItems = cartDao.getAllItems();

        if (cartItems == null || cartItems.isEmpty()) {
            Log.e("CartActivity", "Cart items is null or empty");
            cartItems = new ArrayList<>();
        }

        cartAdapter = new CartAdapter(this, cartDao);
        recyclerViewGioHang.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        updateTotalMoney();

        btnDatHang.setOnClickListener(v -> {
            if (cartItems != null && !cartItems.isEmpty()) {
                cartDao.updateAllItems(cartItems);
            } else {
                Log.e("CartController", "Giỏ hàng trống, không thể cập nhật");
            }

            SwitchScreen.switchScreen(CartController.this, OrdersController.class);
        });
    }


    public void updateTotalMoney() {
        int totalMoney = 0;
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                totalMoney += item.getPrice() * item.getQuantity();
            }
        }
        txtTongTien.setText(totalMoney + " VNĐ");
    }

    private void addViews() {
        txtTongTien = findViewById(R.id.txtTongTien);
        recyclerViewGioHang = findViewById(R.id.recyclerViewGioHang);
        btnDatHang = findViewById(R.id.btnDatHang);

        recyclerViewGioHang.setLayoutManager(new LinearLayoutManager(this));
    }
}
