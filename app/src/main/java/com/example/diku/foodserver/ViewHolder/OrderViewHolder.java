package com.example.diku.foodserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.R;

/**
 * Created by Diku on 27-05-2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder  {
    public TextView tvOrder_id,tvOrder_status,tvOrder_phone,tvOrder_address;
    public TextView txt_edit,txt_remove,txt_detail,txt_direction;

    public OrderViewHolder(View itemView) {
        super(itemView);
        tvOrder_id=(TextView)itemView.findViewById(R.id.tvOrder_id);
        tvOrder_status=(TextView)itemView.findViewById(R.id.tvOrder_status);
        tvOrder_phone=(TextView)itemView.findViewById(R.id.tvOrder_phone);
        tvOrder_address=(TextView)itemView.findViewById(R.id.tvOrder_address);
        txt_edit=(TextView)itemView.findViewById(R.id.txt_edit);
        txt_remove=(TextView)itemView.findViewById(R.id.txt_remove);
        txt_detail=(TextView)itemView.findViewById(R.id.txt_detail);
        txt_direction=(TextView)itemView.findViewById(R.id.txt_direction);


    }


}