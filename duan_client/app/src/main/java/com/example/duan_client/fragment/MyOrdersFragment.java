package com.example.duan_client.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.example.duan_client.MainActivity;
import com.example.duan_client.R;
import com.example.duan_client.adapter.MyOrdersAdapter;
import com.example.duan_client.model.Order;


public class MyOrdersFragment extends Fragment {
    RecyclerView recyclerViewMyOrders;
    MyOrdersAdapter adapter;
    List<Order> myOrders = new ArrayList<>();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewMyOrders = view.findViewById(R.id.recyclerViewMyOrders);
        recyclerViewMyOrders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        getOrdersFromDb();
        adapter = new MyOrdersAdapter(getContext(), myOrders);
        recyclerViewMyOrders.setAdapter(adapter);
    }

    public void getOrdersFromDb() {
        myRef.child("orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if (order != null && order.getUser().getId().equals(MainActivity.userId)) {
                    myOrders.add(order);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Order order = snapshot.getValue(Order.class);
                if (order == null || myOrders == null || myOrders.isEmpty() || !order.getUser().getId().equals(MainActivity.userId)) {
                    return;
                }
                for (int i = 0; i < myOrders.size(); i++) {
                    if (order.getId().equals(myOrders.get(i).getId())) {
                        myOrders.set(i, order);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Order order = snapshot.getValue(Order.class);
                if (order == null || myOrders == null || myOrders.isEmpty() || !order.getUser().getId().equals(MainActivity.userId)) {
                    return;
                }
                for (int i = 0; i < myOrders.size(); i++) {
                    if (order.getId().equals(myOrders.get(i).getId())) {
                        myOrders.remove(myOrders.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}