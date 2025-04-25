package dev.mhung.ltmobile.petapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mhung.ltmobile.petapplication.R;
import dev.mhung.ltmobile.petapplication.model.Contact;
import dev.mhung.ltmobile.petapplication.request.ContactRequest;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.tvId.setText("Mã liên hệ: "+ contact.getId());
        holder.tvFullName.setText("Tên khách: "+ (contact.getFullName() != null ? contact.getFullName() : "Không có tên"));
        holder.tvEmail.setText("Email: "+ (contact.getEmail() != null ? contact.getEmail() : "Không có email"));
        holder.tvPhone.setText("Số điện thoại: "+(contact.getPhone() != null ? contact.getPhone() : "Không có điện thoại"));
        holder.tvContent.setText("Nội dung: "+(contact.getContent() != null ? contact.getContent() : "Không có nội dung"));
        holder.tvAddress.setText("Địa chỉ: "+(contact.getAddress() != null ? contact.getAddress() : "Không có ghi chú"));
        holder.tvDate.setText("Ngày: "+contact.getDate());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvFullName, tvEmail, tvPhone, tvContent, tvAddress, tvDate;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
