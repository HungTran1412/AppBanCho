package dev.mhung.ltmobile.petapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.mhung.ltmobile.petapplication.model.CheckEmail;
import dev.mhung.ltmobile.petapplication.request.ContactRequest;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ContactApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactController extends AppCompatActivity{
    private Button btnCTTrangChu, btnCTGui;
    private EditText txtCTHoTen, txtCTEmail, txtCTSDT, txtCTContent, txtCTDiaChi;
    
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

        btnCTTrangChu.setOnClickListener(v -> {
            finish();
        });
    }

    private void addViews() {
        btnCTTrangChu = findViewById(R.id.btnCTTrangChu);
        btnCTGui = findViewById(R.id.btnCTGui);

        txtCTHoTen = findViewById(R.id.txtCTHoTen);
        txtCTEmail = findViewById(R.id.txtCTEmail);
        txtCTSDT = findViewById(R.id.txtCTSDT);
        txtCTContent = findViewById(R.id.txtCTContent);
        txtCTDiaChi = findViewById(R.id.txtCTDiaChi);
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


}