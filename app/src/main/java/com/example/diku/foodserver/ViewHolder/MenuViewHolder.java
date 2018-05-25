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
 * Created by Diku on 24-05-2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txt_item_menu;
    public ImageView img_item_menu;

    //created a package "ItemclickListener"
    //created an interface into that package
    //onclick method created into that interface nd the mehod onclick holds three parameters ...
    private ItemClickListener itemclickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        //created a layout .xml file "menuitem"
        txt_item_menu=(TextView)itemView.findViewById(R.id.txt2);
        img_item_menu=(ImageView) itemView.findViewById(R.id.image2);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);


    }

  public void setItemclickListener(ItemClickListener itemclickListener){

        this.itemclickListener=itemclickListener;
  }

    @Override
    public void onClick(View view) {

      itemclickListener.onclick(view,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

      contextMenu.setHeaderTitle("Select the Action");
      contextMenu.add(0,0,getAdapterPosition(), Common.UPDATE);
        contextMenu.add(0,1,getAdapterPosition(),Common.DELETE);
    }
}
