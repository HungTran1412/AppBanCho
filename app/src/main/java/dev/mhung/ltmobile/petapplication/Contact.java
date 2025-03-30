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

public class Contact extends AppCompatActivity {
    Button btnCTTrangChu, btnCTGui;
    EditText txtCTHoTen, txtCTEmail, txtCTSDT, txtCTContent;

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
        btnCTGui.setOnClickListener(v -> {
            try {
                String hoTen = txtCTHoTen.getText().toString();
                String email = txtCTEmail.getText().toString();
                String sdt = txtCTSDT.getText().toString();
                String content = txtCTContent.getText().toString();

                if (hoTen.isEmpty() || email.isEmpty() || sdt.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (!emailCheck(email)) {
                    Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Gửi dữ liệu thành công!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("Error","Lỗi: " + e.getMessage());
            }
        });
        
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
    }

    private boolean emailCheck(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}