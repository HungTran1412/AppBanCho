package dev.mhung.ltmobile.petapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.model.CurrencyUtils;
import dev.mhung.ltmobile.petapplication.response.AllOrderRespsonse;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<AllOrderRespsonse> orderList;

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

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvFullName, tvEmail, tvPhone, tvAddress, tvTotalAmount, tvStatus,tvNote,tvDate;

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
        }
    }
}
