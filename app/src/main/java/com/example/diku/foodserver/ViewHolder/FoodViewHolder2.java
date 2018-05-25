package com.example.diku.foodserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.R;

/**
 * Created by Diku on 25-05-2018.
 */

public class FoodViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

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
    }
    public void setItemClickListener(ItemClickListener itemclickListener) {
        this.itemclickListener = itemclickListener;
    }

    @Override
    public void onClick(View view) {

        itemclickListener.onclick(view,getAdapterPosition(),false); //...here the three parameters have been used

    }
}
