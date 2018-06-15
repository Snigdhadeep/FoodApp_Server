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

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {
    public TextView tvOrder_id,tvOrder_status,tvOrder_phone,tvOrder_address;
    private ItemClickListener itemclickListener;
    public OrderViewHolder(View itemView) {
        super(itemView);
        tvOrder_id=(TextView)itemView.findViewById(R.id.tvOrder_id);
        tvOrder_status=(TextView)itemView.findViewById(R.id.tvOrder_status);
        tvOrder_phone=(TextView)itemView.findViewById(R.id.tvOrder_phone);
        tvOrder_address=(TextView)itemView.findViewById(R.id.tvOrder_address);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }

    public void setItemclickListener(ItemClickListener itemclickListener) {
        this.itemclickListener = itemclickListener;
    }

    @Override
    public void onClick(View view) {

        itemclickListener.onclick(view,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        contextMenu.setHeaderTitle("Select the action");
        contextMenu.add(0,0,getAdapterPosition(),"Update");
        contextMenu.add(0,1,getAdapterPosition(),"Delete");
    }
}
