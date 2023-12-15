package com.example.duan_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.duan_client.R;
import com.example.duan_client.model.Dish;
import com.example.duan_client.model.OrderDish;

public class ChooseDishAdapter extends RecyclerView.Adapter<ChooseDishAdapter.userViewHolder> implements Filterable {
    Context context;
    OrderDish orderDish;
    IClickListener iClickListener;
    List<Dish> list;
    List<Dish> listDish;
    List<OrderDish> orderDishes = new ArrayList<>();
    List<OrderDish> listOrderDish ;

    //list luu tam
    List<OrderDish> listOrderTam = new ArrayList<>();



    public interface IClickListener{
        void OnClickItem(List<OrderDish> list);
    }
    public ChooseDishAdapter(Context context,List<OrderDish> listOrderDish,IClickListener listener) {
        this.context = context;
        this.listOrderDish = listOrderDish;
        this.iClickListener = listener;
    }

    public void setData(List<Dish> list){
        this.listDish = list;
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_dish,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Dish dish = listDish.get(position);
        Glide.with(context).load(dish.getImg()).into(holder.img_monAn);
        ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
        holder.img_monAn.setScaleType(scaleType);
        holder.tv_tenMonAn.setText(dish.getTen());
        holder.tv_gia.setText(String.valueOf(dish.getGia()));
        AtomicInteger soLuong = new AtomicInteger();

        for(OrderDish dish1 : listOrderDish){
            if(dish1.getDish().getId().equals(dish.getId())){
                soLuong.set(dish1.getQuantity());
                holder.tv_soLuong.setText(String.valueOf(soLuong.get()));
                if(soLuong.get()>0){
                    orderDish = new OrderDish();
                    orderDish.setDish(dish);
                    orderDish.setQuantity(soLuong.get());
                    orderDishes.add(orderDish);
                    for(int i =0; i<orderDishes.size()-1;i++){
                        for(int x =i+1;x<orderDishes.size();x++){
                            if(orderDishes.get(i).getDish().getId().equals(orderDishes.get(x).getDish().getId())){
                                orderDishes.remove(x);
                            }
                        }
                    }
                    iClickListener.OnClickItem(orderDishes);
                }
            }
        }
        if(soLuong.get()==0){
            holder.btn_giam.setVisibility(View.INVISIBLE);
            holder.tv_soLuong.setVisibility(View.INVISIBLE);
        }else{
            holder.btn_giam.setVisibility(View.VISIBLE);
            holder.tv_soLuong.setVisibility(View.VISIBLE);
        }

        //list tam
        listOrderTam = listOrderDish;
        for (OrderDish orderDish1 : listOrderTam) {
            if (orderDish1.getDish().getId().equals(dish.getId())) {
                soLuong.set(orderDish1.getQuantity());
                holder.tv_soLuong.setText(String.valueOf(soLuong.get()));
            }
        }//


        holder.btn_tang.setOnClickListener(v -> {
            orderDish = new OrderDish();
            soLuong.getAndIncrement();
            holder.tv_soLuong.setText(String.valueOf(soLuong.get()));
            holder.btn_giam.setVisibility(View.VISIBLE);
            holder.tv_soLuong.setVisibility(View.VISIBLE);
            orderDish.setDish(dish);
            orderDish.setQuantity(soLuong.get());

            //list tam
            for (OrderDish orderDish1 : listOrderTam) {
                if (orderDish1.getDish().getId().equals(orderDish.getDish().getId())) {
                    orderDish1.setQuantity(soLuong.get());
                }
            }//

            for (OrderDish orderDish1 : orderDishes) {
                if (orderDish1.getDish().getId().equals(orderDish.getDish().getId())) {
                    orderDish1.setQuantity(soLuong.get());
                    iClickListener.OnClickItem(orderDishes);
                    return;
                }
            }
            listOrderTam.add(orderDish);
            orderDishes.add(orderDish);
            iClickListener.OnClickItem(orderDishes);
        });

        holder.btn_giam.setOnClickListener(v -> {
            if(soLuong.get()<2){
                holder.btn_giam.setVisibility(View.INVISIBLE);
                holder.tv_soLuong.setVisibility(View.INVISIBLE);
            }
            soLuong.getAndDecrement();
            holder.tv_soLuong.setText(String.valueOf(soLuong.get()));

            //list tam
            for (OrderDish orderDish1 : listOrderTam) {
                if (orderDish1.getDish().getId().equals(dish.getId())) {
                    orderDish1.setQuantity(soLuong.get());
                }
            }//

            for (OrderDish orderDish1 : orderDishes) {
                if (orderDish1.getDish().getId().equals(dish.getId())) {
                    orderDish1.setQuantity(soLuong.get());
                    if(orderDish1.getQuantity()==0){
                        orderDishes.remove(orderDish1);
                    }
                    iClickListener.OnClickItem(orderDishes);
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listDish != null){
            return listDish.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder {
        ImageView img_monAn;
        TextView tv_tenMonAn,tv_gia,tv_soLuong;
        Button btn_tang,btn_giam;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            img_monAn = itemView.findViewById(R.id.img_chooseDish);
            tv_tenMonAn = itemView.findViewById(R.id.tv_chooseDish_ten);
            tv_gia = itemView.findViewById(R.id.tv_chooseDish_gia);
            tv_soLuong = itemView.findViewById(R.id.tv_soLuong);
            btn_tang = itemView.findViewById(R.id.btn_tang);
            btn_giam = itemView.findViewById(R.id.btn_giam);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if(search.isEmpty()){
                    listDish = list;
                }else{
                    List<Dish> listSearch = new ArrayList<>();
                    for(Dish dish: list){
                        if(dish.getTen().toLowerCase().contains(search)){
                            listSearch.add(dish);
                        }
                    }
                    listDish = listSearch;
                }
                FilterResults results = new FilterResults();
                results.values = listDish;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    listDish = (List<Dish>) results.values;
                    notifyDataSetChanged();
            }
        };
    }
}
