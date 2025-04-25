package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.mhung.ltmobile.petapplication.adapter.ContactAdapter;
import dev.mhung.ltmobile.petapplication.adapter.OrderAdapter;
import dev.mhung.ltmobile.petapplication.model.Contact;
import dev.mhung.ltmobile.petapplication.model.CurrencyUtils;
import dev.mhung.ltmobile.petapplication.response.AllOrderRespsonse;
import dev.mhung.ltmobile.petapplication.response.OrderResponse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import dev.mhung.ltmobile.petapplication.service.OrderApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdministrationController extends AppCompatActivity {

    private static final String TAG = "hehehe";
    private TextView dailyOrderText, monthlyOrderText, yearlyOrderText;
    private TextView avgOrderAmountText, orderChangeRateText, revenueChangeRateText;
    private TextView yearlyRevenueText, dailyRevenueText, monthlyRevenueText;
    private long todayRevenue = 0, monthRevenue = 0, yearRevenue = 0;
    private int todayOrders = 0, monthOrders = 0, yearOrders = 0;

    private RecyclerView recyclerOrder;
    private OrderAdapter adapter;
    private List<AllOrderRespsonse> orderList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ TextView
        mapping();


        // Gọi API
        fetchData();

        loadAllOrders();

        fetchContacts();
    }
    private void mapping(){
        dailyRevenueText = findViewById(R.id.dailyRevenueText);
        monthlyRevenueText = findViewById(R.id.monthlyRevenueText);
        yearlyRevenueText = findViewById(R.id.yearlyRevenueText);
        dailyOrderText = findViewById(R.id.dailyOrderText);
        monthlyOrderText = findViewById(R.id.monthlyOrderText);
        yearlyOrderText = findViewById(R.id.yearlyOrderText);
        avgOrderAmountText = findViewById(R.id.avgOrderAmountText);
        orderChangeRateText = findViewById(R.id.orderChangeRateText);
        revenueChangeRateText = findViewById(R.id.revenueChangeRateText);
        recyclerOrder = findViewById(R.id.recyclerPayments);
        recyclerOrder.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(orderList);
        recyclerOrder.setAdapter(adapter);

        recyclerView = findViewById(R.id.recyclerContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList);
        recyclerView.setAdapter(contactAdapter);


    }

    private void fetchData() {

        OrderApiService service = RetrofitClient.placeOrder();

        // Today
        service.getTodayOrder().enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    todayOrders = response.body().getSoDon();
                    todayRevenue = response.body().getTongTien();
                    dailyOrderText.setText("Số đơn trong ngày: " + todayOrders);
                    dailyRevenueText.setText("Doanh thu trong ngày: "+ CurrencyUtils.formatVND(todayRevenue));
                    updateSummary();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("API", "Lỗi Today: " + t.getMessage());
            }
        });

        // Month
        service.getMonthOrder().enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    monthOrders = response.body().getSoDon();
                    monthRevenue = response.body().getTongTien();
                    monthlyOrderText.setText("Số đơn trong tháng: " + monthOrders);
                    monthlyRevenueText.setText("Doanh thu trong tháng: "+ CurrencyUtils.formatVND(monthRevenue));
                    updateSummary();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("API", "Lỗi Month: " + t.getMessage());
            }
        });

        // Year
        service.getYearOrder().enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    yearOrders = response.body().getSoDon();
                    yearRevenue = response.body().getTongTien();
                    yearlyOrderText.setText("Số đơn trong năm: " + yearOrders);
                    yearlyRevenueText.setText("Doanh thu trong năm: "+ CurrencyUtils.formatVND(yearRevenue));
                    updateSummary();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("API", "Lỗi Year: " + t.getMessage());
            }
        });
    }

    // Tính toán thêm nếu có đủ dữ liệu
    private void updateSummary() {
        if (monthOrders > 0) {
            long avg = monthRevenue / monthOrders;
            avgOrderAmountText.setText("Trung bình mỗi đơn: " + CurrencyUtils.formatVND(avg));
        }

        if (monthOrders > 0 && todayOrders > 0) {
            float orderRate = ((float) todayOrders / (monthOrders / 30f)) * 100f;
            orderChangeRateText.setText(String.format("Tỷ lệ thay đổi đơn hàng: %.2f%%", orderRate));
        }

        if (monthRevenue > 0 && todayRevenue > 0) {
            float revenueRate = ((float) todayRevenue / (monthRevenue / 30f)) * 100f;
            revenueChangeRateText.setText(String.format("Tỷ lệ thay đổi doanh thu: %.2f%%", revenueRate));
        }
    }

    private void loadAllOrders() {
 OrderApiService api = RetrofitClient.placeOrder();
        api.getAllOrder().enqueue(new Callback<List<AllOrderRespsonse>>() {
            @Override
            public void onResponse(Call<List<AllOrderRespsonse>> call, Response<List<AllOrderRespsonse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orderList.clear();
                    orderList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<AllOrderRespsonse>> call, Throwable t) {
                Toast.makeText(AdministrationController.this, "Lỗi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchContacts() {

        ContactApiService apiService = RetrofitClient.getInstance();
        Call<List<Contact>> call = apiService.getContacts();

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactList.clear();
                    contactList.addAll(response.body());
                    contactAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(AdministrationController.this, "Lỗi gọi API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}