package com.example.diku.foodserver.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.diku.foodserver.Model.Order;
import com.example.diku.foodserver.R;

import java.util.List;

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView name,quantity,price, discount;
    public MyViewHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tvproduct_name);
        quantity=itemView.findViewById(R.id.tvproductQuantity);
        price=itemView.findViewById(R.id.tvproductprice);
        discount= itemView.findViewById(R.id.tvproduct_discount);
    }
}
public class OrderDetailAdapter extends RecyclerView.Adapter<MyViewHolder>{
List<Order> myorders;

    public OrderDetailAdapter(List<Order> myorders) {
        this.myorders = myorders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_layout,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Order order=myorders.get(position);
        holder.name.setText(String.format("Name : %s",order.getProductName()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));

    }

    @Override
    public int getItemCount() {
        return myorders.size();
    }
}
