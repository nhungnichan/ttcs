package com.example.duan_client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.duan_client.R;
import com.example.duan_client.model.FeedBack;
import com.example.duan_client.model.Table;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>{
    Context context;
    List<FeedBack> feedBacks;

    public FeedbackAdapter(Context context, List<FeedBack> feedBacks) {
        this.context = context;
        this.feedBacks = feedBacks;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedbackViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        FeedBack feedBack = feedBacks.get(position);
        holder.tvUserName.setText(feedBack.getUser().getName());

        if (feedBack.getOrder() != null) {
            String tableStr = "Bàn: ";
            for (Table table : feedBack.getOrder().getTables()) {
                tableStr += table.getNumber();
                if (feedBack.getOrder().getTables().indexOf(table) == feedBack.getOrder().getTables().size()-1)
                    break;
                tableStr +=  " , ";
            }
            holder.tvTable.setText(tableStr);
            holder.tvNoP.setText(feedBack.getOrder().getNumberOfPeople() + " người");
        }
        holder.tvContent.setText(feedBack.getContent());
        holder.tvDate.setText(feedBack.getDate());
    }

    @Override
    public int getItemCount() {
        return feedBacks.size();
    }

    class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvContent, tvDate, tvTable, tvNoP;
        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTable = itemView.findViewById(R.id.tvTable);
            tvNoP = itemView.findViewById(R.id.tvNoP);
        }
    }
}
