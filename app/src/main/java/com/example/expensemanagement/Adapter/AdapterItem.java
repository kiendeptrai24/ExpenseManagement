package com.example.expensemanagement.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.expensemanagement.Entity.Expenses;
import com.example.expensemanagement.R;
import java.util.ArrayList;

public class AdapterItem extends ArrayAdapter<Expenses> {
    Context context;
    ArrayList<Expenses> list;

    public AdapterItem(@NonNull Context context, ArrayList<Expenses> list) {
        super(context, R.layout.item, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.tvDate = convertView.findViewById(R.id.tvDate);
            holder.tvAmount = convertView.findViewById(R.id.tvAmount);
            holder.tvDescription = convertView.findViewById(R.id.tvDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expenses obj = list.get(position);
        holder.tvDate.setText(obj.getDate());
        holder.tvAmount.setText(formatCurrency(obj.getAmount()));
        holder.tvDescription.setText(obj.getDescription());

        // Set color based on category
        if ("Thu".equals(obj.getCategory())) {
            holder.tvAmount.setTextColor(Color.parseColor("#5ACA8D"));
        } else {
            holder.tvAmount.setTextColor(Color.RED);
        }

        return convertView;
    }

    // ViewHolder pattern for smoother scrolling
    static class ViewHolder {
        TextView tvDescription;
        TextView tvAmount;
        TextView tvDate;
    }

    // Format currency based on amount value
    public static String formatCurrency(int amount) {
        if (amount >= 1000000) {
            // Định dạng cho đơn vị triệu
            return (amount / 1000000) + "tr" +((amount % 1000000)/1000) ;
        } else if (amount >= 1000) {
            // Định dạng cho đơn vị nghìn
            return (amount / 1000) + "k";
        } else {
            // Định dạng cho đơn vị đồng
            return amount + "đ";
        }
    }
}
