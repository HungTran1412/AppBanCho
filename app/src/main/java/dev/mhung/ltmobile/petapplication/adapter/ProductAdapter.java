package dev.mhung.ltmobile.petapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;

public class ProductAdapter extends ArrayAdapter<ProductResponse> {
    private Context context;
    private List<ProductResponse> products;

    public ProductAdapter(@NonNull Context context, List<ProductResponse> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductResponse product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        // Ánh xạ các View
        TextView tvName = convertView.findViewById(R.id.tvProductName);
        TextView tvAge = convertView.findViewById(R.id.tvProductAge);
        TextView tvPrice = convertView.findViewById(R.id.tvProductPrice);
        TextView tvGender = convertView.findViewById(R.id.tvProductGender);
        TextView tvDescription = convertView.findViewById(R.id.tvProductDescription);
        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);

        // Gán dữ liệu
        tvName.setText("Tên: " + product.getName());
        tvAge.setText("Tuổi: " + product.getAge());
        tvPrice.setText("Giá: " + product.getPrice() + " VNĐ");
        tvGender.setText("Giống: " + product.getGender());
        tvDescription.setText(product.getDescription());

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(product.getImg()) // URL hoặc đường dẫn ảnh
                .placeholder(R.drawable.ic_launcher_foreground) // ảnh mặc định khi loading
                .into(imgProduct);

        return convertView;
    }
}
