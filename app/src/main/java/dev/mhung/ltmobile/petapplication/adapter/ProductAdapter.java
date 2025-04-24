package dev.mhung.ltmobile.petapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import dev.mhung.ltmobile.petapplication.DetailController;
import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;

public class ProductAdapter extends ArrayAdapter<ProductResponse> {
    private Context context;
    private List<ProductResponse> allProducts;

    private List<ProductResponse> products;
    private ProductFilter productFilter;

    public ProductAdapter(@NonNull Context context, List<ProductResponse> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
        this.allProducts = new ArrayList<>(products); // lưu bản sao danh sách gốc

    }

    @Override
    public Filter getFilter() {
        if (productFilter == null) {
            productFilter = new ProductFilter(); // đúng tên biến
        }
        return productFilter;
    }

    private String removeAccents(String s) {
        s = s.toLowerCase()
                .replaceAll("[áàảãạăắằẳẵặâấầẩẫậ]", "a")
                .replaceAll("[éèẻẽẹêếềểễệ]", "e")
                .replaceAll("[íìỉĩị]", "i")
                .replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o")
                .replaceAll("[úùủũụưứừửữự]", "u")
                .replaceAll("[ýỳỷỹỵ]", "y")
                .replaceAll("đ", "d");
        return s;
    }

    private class ProductFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductResponse> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(allProducts);
            } else {
                String filterPattern = removeAccents(constraint.toString().toLowerCase().trim());

                for (ProductResponse product : allProducts) {
                    String name = removeAccents(product.getName());
                    String breed = product.getBreed() != null ? removeAccents(product.getBreed()) : "";

                    if (name.contains(filterPattern) || breed.contains(filterPattern)) {
                        filtered.add(product);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            products.clear();
            products.addAll((List<ProductResponse>) results.values);
            notifyDataSetChanged();
        }
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

        String finalName = name;
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailController.class);
                intent.putExtra("id", product.getId());
                intent.putExtra("image_url", product.getImg());
                intent.putExtra("name", finalName);
                intent.putExtra("price", product.getPrice());
                intent.putExtra("age", product.getAge());
                intent.putExtra("breed", product.getBreed());
                intent.putExtra("des", product.getDescription() != null ? product.getDescription() : "Không có mô tả");

                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
