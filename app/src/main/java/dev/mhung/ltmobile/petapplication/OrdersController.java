package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.OrderListAdapter;
import dev.mhung.ltmobile.petapplication.database.CartDAO;
import dev.mhung.ltmobile.petapplication.model.CartItem;

public class OrdersController extends AppCompatActivity {

    ListView lsvCartItems;
    EditText txtOrderFullname, txtOrderPhone, txtOrderAdress, txtOrderEmail, txtOrderNote;
    RadioButton cod, vnpay;
    TextView lblTotalPrice;
    Button btnPlaceOrder;
    OrderListAdapter adapter;
    List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_order), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
        addEvents();
    }
    private void addViews() {
        lsvCartItems = findViewById(R.id.lsvCartItems);

        txtOrderFullname = findViewById(R.id.txtOrderFullname);
        txtOrderPhone = findViewById(R.id.txtOrderPhone);
        txtOrderAdress = findViewById(R.id.txtOrderAdress);
        txtOrderEmail = findViewById(R.id.txtOrderEmail);
        txtOrderNote = findViewById(R.id.txtOrderNote);

        lblTotalPrice = findViewById(R.id.lblTotalPrice);

        cod = findViewById(R.id.cod);
        vnpay = findViewById(R.id.vnpay);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
    }
    private void addEvents() {
        CartDAO cartDAO = new CartDAO(this);
        cartItems = cartDAO.getAllItems();

        adapter = new OrderListAdapter(this, cartItems);
        lsvCartItems.setAdapter(adapter);
    }


}