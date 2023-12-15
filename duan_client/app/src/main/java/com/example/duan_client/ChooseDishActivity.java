 package com.example.duan_client;

 import android.content.Intent;
 import android.os.Bundle;
 import android.os.Parcelable;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.widget.Button;
 import android.widget.EditText;

 import androidx.annotation.NonNull;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.google.firebase.database.DataSnapshot;
 import com.google.firebase.database.DatabaseError;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.database.ValueEventListener;

 import java.util.ArrayList;
 import java.util.List;

 import com.example.duan_client.adapter.ChooseDishAdapter;
 import com.example.duan_client.model.Dish;
 import com.example.duan_client.model.OrderDish;

public class ChooseDishActivity extends AppCompatActivity {
    ChooseDishAdapter adapter;
    RecyclerView recyclerView;
    List<Dish> list= new ArrayList<>();
    List<OrderDish> listOrderDish2 = new ArrayList<>();
    List<OrderDish> listOrderDish3 = new ArrayList<>();
    Button btn_ok;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dish);

        recyclerView = findViewById(R.id.recycleView_chooseDish);
        edtSearch = findViewById(R.id.edtSearch);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(edtSearch.getText().toString().trim());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btn_ok= findViewById(R.id.btn_ok);
        getListDishFromFireBase();
        listOrderDish3 = (List<OrderDish>) getIntent().getExtras().getSerializable("list");

        adapter = new ChooseDishAdapter(ChooseDishActivity.this,listOrderDish3, new ChooseDishAdapter.IClickListener() {
            @Override
            public void OnClickItem(List<OrderDish> listOrderDish) {
                double temp=0;
                for (OrderDish orderDish : listOrderDish) {
                    temp = temp + (orderDish.getDish().getGia()*orderDish.getQuantity());
                }
                btn_ok.setText(String.valueOf(temp));
                listOrderDish2 = listOrderDish;
            }
        });

        btn_ok.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseDishActivity.this,OrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("listOrderDish", (ArrayList<? extends Parcelable>) listOrderDish2);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseDishActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void getListDishFromFireBase(){
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Dish");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Dish dish = dataSnapshot.getValue(Dish.class);
                    list.add(dish);
                }
                adapter.setData(list);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}