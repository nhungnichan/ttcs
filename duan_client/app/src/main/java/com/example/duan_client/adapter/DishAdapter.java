package com.example.duan_client.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.duan_client.R;
import com.example.duan_client.model.Dish;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    private Context context;
    private List<Dish> listDish;
    public DishAdapter(Context context, List<Dish> listDish) {
        this.context = context;
        this.listDish = listDish;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycle_chonmon,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.txtTenMon.setText(listDish.get(position).getTen());
            holder.txtGia.setText(String.valueOf(listDish.get(position).getTen()));
            holder.txtSoLuongMon.setText(String.valueOf(listDish.get(position).getSoLuong()));
    }

    @Override
    public int getItemCount() {
        return listDish.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
            CheckBox cboChon;
            ImageView ivAnh;
            TextView txtTenMon, txtGia, txtSoLuongMon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cboChon = itemView.findViewById(R.id.cboChon);
            ivAnh = itemView.findViewById(R.id.ivAnh);
            txtTenMon = itemView.findViewById(R.id.txtTenMon);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoLuongMon = itemView.findViewById(R.id.txtSoLuongMon);
        }
    }
}
