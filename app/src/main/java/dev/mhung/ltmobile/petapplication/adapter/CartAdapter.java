package dev.mhung.ltmobile.petapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.database.CartDAO;
import dev.mhung.ltmobile.petapplication.model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private CartDAO cartDao;
    private TextView txtTongTienToanBo;
    public CartAdapter(Context context, CartDAO cartDao) {
        this.context = context;
        this.cartDao = cartDao;
        this.cartItems = cartDao.getAllItems();
    }
    public CartAdapter(Context context, CartDAO cartDao, TextView txtTongTienToanBo) {
        this.context = context;
        this.cartDao = cartDao;
        this.cartItems = cartDao.getAllItems();
        this.txtTongTienToanBo = txtTongTienToanBo;
    }
    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        if (item == null) {
            Log.e("CartAdapter", "Item at position " + position + " is null");
            return;
        }
        holder.txtTenThuCung.setText(item.getName());
        holder.txtMoney.setText(String.valueOf(item.getPrice()));
        holder.txtSoLuong.setText(String.valueOf(item.getQuantity()));

        final int[] totalMoney = {item.getQuantity() * item.getPrice()};
        holder.txtTongMoney.setText(totalMoney[0] + " VNĐ");

        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrl())
                    .into(holder.imgThuCung);
        } else {
            Glide.with(context)
                    .load(R.drawable.loading)
                    .into(holder.imgThuCung);
        }

        holder.imgTru.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if (quantity > 1) {
                item.setQuantity(quantity - 1);
                cartDao.updateAllItems(cartItems);
                holder.txtSoLuong.setText(String.valueOf(item.getQuantity()));
//                cartDao.updateItem(item);
                notifyItemChanged(position);
                updateTotalMoney();
            }
        });

        holder.imgCong.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            item.setQuantity(quantity + 1);
            cartDao.updateAllItems(cartItems);
            holder.txtSoLuong.setText(String.valueOf(item.getQuantity()));
//            cartDao.updateItem(item);
            notifyItemChanged(position);
            updateTotalMoney();
        });

        holder.imgXoaCart.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION || pos >= cartItems.size()) return;

            CartItem currentItem = cartItems.get(pos);
            if (currentItem == null) return;

            cartDao.deleteItem(currentItem.getId());
            cartItems.remove(pos);

            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, cartItems.size());
            updateTotalMoney();

            Toast.makeText(context, "Đã xoá sản phẩm", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateTotalMoney() {
        int totalMoney = 0;
        for (CartItem item : cartItems) {
            totalMoney += item.getPrice() * item.getQuantity();
        }
        txtTongTienToanBo.setText(totalMoney + " VNĐ");
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenThuCung, txtMoney, txtSoLuong, txtTongMoney;
        ImageView imgThuCung, imgTru, imgCong, imgXoaCart;

        public CartViewHolder(View itemView) {
            super(itemView);
            txtTenThuCung = itemView.findViewById(R.id.txtTenThuCung);
            txtMoney = itemView.findViewById(R.id.txtMoney);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtTongMoney = itemView.findViewById(R.id.txtTongMoney);
            imgThuCung = itemView.findViewById(R.id.imgThuCung);
            imgTru = itemView.findViewById(R.id.imgTru);
            imgCong = itemView.findViewById(R.id.imgCong);
            imgXoaCart = itemView.findViewById(R.id.imgXoaCart);
        }
    }
}
