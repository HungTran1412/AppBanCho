package dev.mhung.ltmobile.petapplication.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

import dev.mhung.ltmobile.petapplication.AdministrationController;
import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.UpdateProductController;
import dev.mhung.ltmobile.petapplication.request.ProductRequest;
import dev.mhung.ltmobile.petapplication.response.ProductResponse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.ProductApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter1 extends ArrayAdapter<ProductResponse> {
    private Context context;

    private ProductResponse product;
    private List<ProductResponse> products;
    ProductApiService api = RetrofitClient.product();
    public ProductAdapter1(@NonNull Context context, List<ProductResponse> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductResponse product = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item_administraion, parent, false);
        }

        // Ánh xạ các View
        TextView tvName = convertView.findViewById(R.id.tvProductName);
        TextView tvAge = convertView.findViewById(R.id.tvProductAge);
        TextView tvPrice = convertView.findViewById(R.id.tvProductPrice);
        TextView tvGender = convertView.findViewById(R.id.tvProductGender);
        Button a=convertView.findViewById(R.id.Update);
        Button b=convertView.findViewById(R.id.Delete);
//        TextView tvDescription = convertView.findViewById(R.id.tvProductDescription);
        //   ImageView imgProduct = convertView.findViewById(R.id.imgProduct);

        EditText editText = convertView.findViewById(R.id.editName);


        //Tách chuỗi tên sản phẩm
        String name = product.getName();

        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '-') {
                name = name.substring(i + 1);
                break;
            }
        }
        // Gán dữ liệu
        tvName.setText("Tên: " + product.getName());
        tvAge.setText("Tuổi: " + product.getAge());
        tvPrice.setText("Giá: " + product.getPrice() + " VNĐ");
        tvGender.setText("Giống: " + (product.getBreed() != null ? product.getBreed() : "Chưa rõ"));
//        tvDescription.setText(product.getDescription());
        View finalConvertView = convertView;



        //Dialog của nút đây
        a.setOnClickListener(v -> {

            UpdateProductController.state=0;
            final Dialog g = new Dialog(finalConvertView.getContext());
            g.requestWindowFeature(Window.FEATURE_NO_TITLE);
            g.setContentView(R.layout.dialog_layout);

            Window window=g.getWindow();
            if(window==null)return;
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //  window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            WindowManager.LayoutParams win=window.getAttributes();
            win.gravity= Gravity.CENTER;
            window.setAttributes(win);
            g.show();
            //AdministrationController.state=2;
            //Add nút
            Button close=g.findViewById(R.id.btnClose);
            Button update=g.findViewById(R.id.btnUpdate);
            EditText Name=g.findViewById(R.id.editName);
            EditText Id=g.findViewById(R.id.editId);
            EditText Age=g.findViewById(R.id.editAge);
            EditText Breed=g.findViewById(R.id.editBreed);
            EditText Color=g.findViewById(R.id.editColor);
            EditText Size=g.findViewById(R.id.editSize);
            EditText Img=g.findViewById(R.id.editImg);
            EditText Description=g.findViewById(R.id.editDescription);
            EditText Quantity=g.findViewById(R.id.editQuantity);
            EditText Price=g.findViewById(R.id.editPrice);
            update.setText("Sửa");


            Id.setText(""+product.getId());
            Name.setText(product.getName());
            Age.setText(""+product.getAge());
            Breed.setText(product.getBreed());
            Color.setText(product.getColor());
            Size.setText(product.getSize());
            Img.setText(product.getImg() != null ? product.getImg() : "Chưa có");
            Description.setText(product.getDescription());
            Quantity.setText(""+product.getQuantity());
            Price.setText(""+product.getPrice());



            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    g.dismiss();
                }
            });


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(UpdateProductController.state==0) {
                        int id = Integer.parseInt(Id.getText().toString());
                        String name = Name.getText().toString();
                        int age = Integer.parseInt(Age.getText().toString());
                        int price = Integer.parseInt(Price.getText().toString());

                        int quantity = Integer.parseInt(Quantity.getText().toString());

                        String size = Size.getText().toString();

                        String color = Color.getText().toString();
                        String img = Img.getText().toString();
                        String description = Description.getText().toString();
                        String breed = Breed.getText().toString();
                        ProductRequest a = new ProductRequest(id, name, age, price, quantity, size, color, img, description, breed);
                        api.updateProduct(id, a).enqueue(new Callback<ProductResponse>() {
                            @Override
                            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Sửa dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                                    Log.d("RetrofitClient", "sửa thành công!");
                                    notifyDataSetChanged();

                                } else {
                                    Toast.makeText(context, " Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                                    Log.e("RetrofitClient", "Lỗi  " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<ProductResponse> call, Throwable t) {
                                System.out.print(t.getMessage());
                                //Toast.makeText(ProductController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });
                    }}
            });
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa không?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                api.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Xoá dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                                            Log.d("RetrofitClient", "Xoá thành công!");

                                            products.remove(position);// Cập nhật
                                            notifyDataSetChanged();



                                        }else{
                                            Toast.makeText(context, "Mã lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                                            Log.e("RetrofitClient", "Lỗi  " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.print(t.getMessage());
                                        //Toast.makeText(ProductController.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });


                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Đóng hộp thoại
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        // Load ảnh bằng Glide

//       Glide.with(context)
//                .load(product.getImg()) // URL hình ảnh
//                .placeholder(R.drawable.loading) // hình tạm khi đang tải
//                .error(R.drawable.error) // hình khi lỗi
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imgProduct);
////        Toast.makeText(context, "img: "+ product.getImg(), Toast.LENGTH_SHORT).show();

        return convertView;
    }
}
