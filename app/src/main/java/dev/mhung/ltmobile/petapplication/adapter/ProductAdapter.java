package dev.mhung.ltmobile.petapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
//        TextView tvDescription = convertView.findViewById(R.id.tvProductDescription);
        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);

        //Tách chuỗi tên sản phẩm
        String name = product.getName();

        for(int i = 0; i < name.length(); i++){
            if(name.charAt(i) == '-'){
                name = name.substring(i + 1);
                break;
            }
        }
        // Gán dữ liệu
        tvName.setText("Tên: " + name);
        tvAge.setText("Tuổi: " + product.getAge());
        tvPrice.setText("Giá: " + product.getPrice() + " VNĐ");
        tvGender.setText("Giống: " + (product.getBreed() != null ? product.getBreed() : "Chưa rõ"));
//        tvDescription.setText(product.getDescription());

        // Load ảnh bằng Glide

        Glide.with(context)
                .load(product.getImg()) // URL hình ảnh
                .placeholder(R.drawable.loading) // hình tạm khi đang tải
                .error(R.drawable.error) // hình khi lỗi
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProduct);
//        Toast.makeText(context, "img: "+ product.getImg(), Toast.LENGTH_SHORT).show();

        return convertView;
    }
}
