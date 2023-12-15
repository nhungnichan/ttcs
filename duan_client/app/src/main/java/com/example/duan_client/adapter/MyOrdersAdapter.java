package com.example.duan_client.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.example.duan_client.MainActivity;
import com.example.duan_client.R;
import com.example.duan_client.model.FeedBack;
import com.example.duan_client.model.Order;
import com.example.duan_client.model.Table;
import com.example.duan_client.model.User;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder>{
    Context context;
    List<Order> orders;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    public MyOrdersAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyOrdersViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_orders, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        Order order = orders.get(position);
        String tablesStr = "Bàn: ";
        List<Table> mTables = order.getTables();
        if (mTables != null) {
            for (Table table : mTables) {
                tablesStr += table.getNumber();
                if (mTables.indexOf(table) == mTables.size()-1)
                    break;
                tablesStr +=  " , ";
            }
            holder.tvOrderTable.setText(tablesStr);
        }
        holder.tvOrderDate.setText(order.getDate());
        holder.tvOrderTime.setText(order.getStartTime() + " - " + order.getEndTime());
        switch (order.getStatus()) {
            case 0:
                holder.tvOrderStatus.setText("Đang chờ");
                holder.tvOrderStatus.setTextColor(Color.YELLOW);
                holder.tvHuy.setVisibility(View.VISIBLE);
                holder.tvHuy.setEnabled(true);
                holder.tvHuy.setOnClickListener(v -> {
                    myRef.child("orders").child(order.getId()).child("status").setValue(3);
                });
                break;
            case 1:
                holder.tvOrderStatus.setText("Đang dùng");
                holder.tvHuy.setVisibility(View.INVISIBLE);
                holder.tvHuy.setEnabled(false);
                holder.tvOrderStatus.setTextColor(Color.BLUE);
                break;
            case 2:
                holder.tvHuy.setVisibility(View.INVISIBLE);
                holder.tvHuy.setEnabled(false);
                holder.tvOrderStatus.setText("Đã xong");
                holder.tvOrderStatus.setTextColor(Color.GREEN);
                holder.tvOrderStatus.setVisibility(View.VISIBLE);
                holder.tvOrderTotal.setText("Tổng tiền: " + formatCurrency(order.getTotal()));
                holder.btnFeedback.setVisibility(View.VISIBLE);
                holder.btnFeedback.setEnabled(true);
                holder.btnFeedback.setOnClickListener(v -> {openFeedbackDialog(order);});
                break;
            case 3:
                holder.tvHuy.setVisibility(View.INVISIBLE);
                holder.tvHuy.setEnabled(false);
                holder.tvOrderStatus.setText("Đã Hủy");
                holder.tvOrderStatus.setTextColor(Color.RED);
                holder.itemView.setEnabled(false);
                break;
        }
    }

    private void openFeedbackDialog(Order order) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edtContent = dialog.findViewById(R.id.edtContent);

        dialog.findViewById(R.id.btnSend).setOnClickListener(v -> {
            if (edtContent.getText().toString().trim().length() == 0) {
                Toast.makeText(context, "Bạn chưa nhập gì cả !!!", Toast.LENGTH_SHORT).show();
                return;
            }

            myRef.child("users").child(MainActivity.userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user == null) {
                        Toast.makeText(context, "Không lấy được user", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FeedBack feedBack = new FeedBack();
                    feedBack.setId("feedback" + Calendar.getInstance().getTimeInMillis());
                    feedBack.setContent(edtContent.getText().toString().trim());
                    String date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                    feedBack.setDate(date);
                    feedBack.setUser(user);
                    feedBack.setOrder(order);
                    myRef.child("feedbacks").child(feedBack.getId()).setValue(feedBack).addOnSuccessListener(unused
                            -> openSuccessDialog("Nhận xét của bạn đã được gửi đi, cảm ơn đã góp ý."));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            dialog.dismiss();
        });
        dialog.findViewById(R.id.btnCancel).setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
    }

    public void openSuccessDialog (String text) {
        Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_success_notification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvNotifyContent = dialog.findViewById(R.id.tvNotifyContent);
        tvNotifyContent.setText(text);
        dialog.findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
    }

    public String formatCurrency(double money) {
        String pattern="###,###.### VNĐ";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(money);
    }

    @Override
    public int getItemCount() {
        if (orders != null)
            return orders.size();
        return 0;
    }

    class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTable, tvOrderDate, tvOrderTime, tvOrderStatus, tvHuy, tvOrderTotal;
        Button btnFeedback;
        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderTable = itemView.findViewById(R.id.tvOrderTable);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvHuy = itemView.findViewById(R.id.tvHuy);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            btnFeedback = itemView.findViewById(R.id.btnFeedback);
        }
    }
}
