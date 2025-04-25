package dev.mhung.ltmobile.petapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.model.CurrencyUtils;
import dev.mhung.ltmobile.petapplication.response.AllOrderRespsonse;
import dev.mhung.ltmobile.petapplication.retrofit.RetrofitClient;
import dev.mhung.ltmobile.petapplication.service.OrderApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<AllOrderRespsonse> orderList;
    private Context context;

    public OrderAdapter(Context context, List<AllOrderRespsonse> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public OrderAdapter(List<AllOrderRespsonse> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        AllOrderRespsonse payment = orderList.get(position);
        holder.tvOrderId.setText("Mã đơn: " + payment.getOrderId());
        holder.tvFullName.setText("Tên khách: " + payment.getFullName());
        holder.tvEmail.setText("Email: " + payment.getEmail());
        holder.tvPhone.setText("Số điện thoại: " + payment.getPhone());
        holder.tvAddress.setText("Địa chỉ: " + payment.getAddress());
        holder.tvTotalAmount.setText("Tổng tiền: " + CurrencyUtils.formatVND(payment.getTotalAmount()));
        holder.tvStatus.setText("Trạng thái: " + payment.getStatus());
        holder.tvDate.setText("Ngày: " + payment.getOrderDate());
        holder.tvNote.setText("Ghi chú: " + (payment.getNote() != null ? payment.getNote() : "Không có ghi chú")); // Thêm ghi chú

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá đơn hàng này không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {

                        // Create API service
                        OrderApiService apiService = RetrofitClient.placeOrder();

                        // Call deleteOrder API
                        Call<Void> call = apiService.deleteOrder(payment.getOrderId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                                    orderList.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(), orderList.size());
                                } else {
                                    Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvFullName, tvEmail, tvPhone, tvAddress, tvTotalAmount, tvStatus, tvNote, tvDate;
        Button btnDelete;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvNote = itemView.findViewById(R.id.tvNote);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}
