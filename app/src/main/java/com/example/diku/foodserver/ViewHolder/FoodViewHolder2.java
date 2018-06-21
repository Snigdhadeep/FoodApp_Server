package com.example.diku.foodserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.R;

/**
 * Created by Diku on 25-05-2018.
 */

public class FoodViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txt_item_food;
    public ImageView img_item_food;

    //created a package "ItemclickListener"
    //created an interface into that package
    //onclick method created into that interface nd the mehod onclick holds three parameters ...
    private ItemClickListener itemclickListener;
    public FoodViewHolder2(View itemView) {
        super(itemView);
        //created a layout .xml file "menuitem"
        txt_item_food=(TextView)itemView.findViewById(R.id.txt_fooditem);
        img_item_food=(ImageView) itemView.findViewById(R.id.image_fooditem);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }
    public void setItemClickListener(ItemClickListener itemclickListener) {
        this.itemclickListener = itemclickListener;
    }

    @Override
    public void onClick(View view) {

        itemclickListener.onclick(view,getAdapterPosition(),false); //...here the three parameters have been used

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select the Action");
        contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(),Common.DELETE);
    }
}
