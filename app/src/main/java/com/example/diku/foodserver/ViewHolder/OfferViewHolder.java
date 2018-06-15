package com.example.diku.foodserver.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diku.foodserver.R;

/**
 * Created by Diku on 14-06-2018.
 */

public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView iv_offeritem1,iv_offeritem2,iv_offeritem3,iv_offeritem4;
    public TextView   txt_offeritem1,txt_offeritem2,txt_offeritem3,txt_offeritem4;

    public OfferViewHolder(View itemView) {
        super(itemView);

        iv_offeritem1=(ImageView)itemView.findViewById(R.id.iv_offeritem1);
        txt_offeritem1=(TextView)itemView.findViewById(R.id.txt_offeritem1);

        iv_offeritem2=(ImageView)itemView.findViewById(R.id.iv_offeritem2);
        txt_offeritem2=(TextView)itemView.findViewById(R.id.txt_offeritem2);

        iv_offeritem3=(ImageView)itemView.findViewById(R.id.iv_offeritem3);
        txt_offeritem3=(TextView)itemView.findViewById(R.id.txt_offeritem3);

        iv_offeritem4=(ImageView)itemView.findViewById(R.id.iv_offeritem4);
        txt_offeritem4=(TextView)itemView.findViewById(R.id.txt_offeritem4);
    }

    @Override
    public void onClick(View view) {

    }
}
