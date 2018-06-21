package com.example.diku.foodserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.ViewHolder.OrderDetailAdapter;

public class OrderDetails extends AppCompatActivity {


    TextView order_id,order_ph,order_address,order_total;
    String order_id_value="";
    RecyclerView listfoods;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

   order_id=(TextView)findViewById(R.id.tvOrder_id);
        order_ph=(TextView)findViewById(R.id.tvOrder_phone);

        order_address=(TextView)findViewById(R.id.tvOrder_address);
        order_total=(TextView)findViewById(R.id.tvOrder_total);
        listfoods=(RecyclerView)findViewById(R.id.listOrdersdetail);

        listfoods.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        listfoods.hasFixedSize();
        listfoods.setLayoutManager(layoutManager);


        if (getIntent()!=null) {
            order_id_value = getIntent().getStringExtra("OrderId");
            }
        order_id.setText(order_id_value);
        order_ph.setText(Common.currentRequest.getPhone());
        order_address.setText(Common.currentRequest.getAddress());
        order_total.setText(Common.currentRequest.getTotal());

        OrderDetailAdapter orderDetailAdapter=new OrderDetailAdapter(Common.currentRequest.getOrders());
        orderDetailAdapter.notifyDataSetChanged();
        listfoods.setAdapter(orderDetailAdapter);



    }
}
