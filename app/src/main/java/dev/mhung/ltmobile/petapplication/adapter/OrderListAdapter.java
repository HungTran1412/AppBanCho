package dev.mhung.ltmobile.petapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.model.CartItem;

public class OrderListAdapter extends BaseAdapter {
    private Context context;
    private List<CartItem> cartItems;
    private LayoutInflater inflater;


    public OrderListAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.product_item, parent, false);
            holder = new ViewHolder();

            holder.imgProduct = convertView.findViewById(R.id.imgProduct);
            holder.txtProductName = convertView.findViewById(R.id.tvProductName);
            holder.txtProductPrice = convertView.findViewById(R.id.tvProductPrice);
            holder.txtProductGender = convertView.findViewById(R.id.tvProductGender);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        CartItem cartItem = cartItems.get(position);

        holder.txtProductName.setText("Tên: " + cartItem.getName());
        holder.txtProductGender.setText("Giống: " + cartItem.getBreed());
        holder.txtProductPrice.setText("Giá: " + cartItem.getPrice() + " VNĐ");
        Glide.with(context)
                .load(cartItem.getImageUrl())
                .placeholder(R.drawable.loading)
                .into(holder.imgProduct);

        return convertView;
    }

    private static class ViewHolder{
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductPrice;
        TextView txtProductGender;
    }
}
