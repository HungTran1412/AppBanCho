package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.OrderListAdapter;
import dev.mhung.ltmobile.petapplication.database.CartDAO;
import dev.mhung.ltmobile.petapplication.model.CartItem;
import dev.mhung.ltmobile.petapplication.model.CheckEmail;
import dev.mhung.ltmobile.petapplication.model.SwitchScreen;
import dev.mhung.ltmobile.petapplication.request.OrderDetailRequest;
import dev.mhung.ltmobile.petapplication.request.OrderRequest;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import dev.mhung.ltmobile.petapplication.service.OrderApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersController extends AppCompatActivity {

    ListView lsvCartItems;
    EditText txtOrderFullname, txtOrderPhone, txtOrderAdress, txtOrderEmail, txtOrderNote;
    RadioButton cod, vnpay;
    TextView lblTotalPrice;
    Button btnPlaceOrder;
    OrderListAdapter adapter;
    List<CartItem> cartItems;
    long totalPrice = 0;
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
        //Hiển thị sản phẩm lên listview
        CartDAO cartDAO = new CartDAO(this);
        cartItems = cartDAO.getAllItems();

        adapter = new OrderListAdapter(this, cartItems);
        lsvCartItems.setAdapter(adapter);

        //tính tổng tiền
        for (CartItem item : cartItems) {
            totalPrice += item.getPrice();
        }

        //Thêm . vào giá
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');

        DecimalFormat format = new DecimalFormat("#,###", symbols);

        String price = format.format(totalPrice);
        lblTotalPrice.setText(price + " VNĐ");

        //đặt hàng
        btnPlaceOrder.setOnClickListener(v -> {
            placeOrder();
        });
    }

    private void placeOrder() {
        Log.d("RetrofitClient", "Bắt đầu gọi API");

        String fullName = txtOrderFullname.getText().toString();
        String phone = txtOrderPhone.getText().toString();
        String email = txtOrderEmail.getText().toString();
        String address = txtOrderAdress.getText().toString();
        String note = txtOrderNote.getText().toString();

        if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        } else if (!CheckEmail.emailCheck(email)) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        OrderRequest order = new OrderRequest();
        order.setFullName(fullName);
        order.setPhone(phone);
        order.setEmail(email);
        order.setAddress(address);
        order.setNote(note);
        order.setTotalAmount(totalPrice);

        List<OrderDetailRequest> orderDetails = new ArrayList<>();
        for (CartItem item : cartItems) {
            OrderDetailRequest orderDetail = new OrderDetailRequest();
            orderDetail.setProductId(item.getId());
            orderDetails.add(orderDetail);
        }
        order.setOrderDetail(orderDetails);

        Log.d("DEBUG_ORDER_JSON", new Gson().toJson(order));

        OrderApiService apiService = RetrofitClient.placeOrder();
        if (apiService == null) {
            Log.e("RetrofitClient", "LỖI: Retrofit chưa được khởi tạo!");
            return;
        }

        apiService.placeOrder(order).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OrdersController.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    new CartDAO(OrdersController.this).clearCart();
                    SwitchScreen.switchScreen(OrdersController.this, MainActivity.class);
                } else {
                    Toast.makeText(OrdersController.this, "Đặt hàng thất bại! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    Log.e("OrderAPI", "Lỗi từ server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(OrdersController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("OrderAPI", "onFailure: ", t);
            }
        });
    }

}