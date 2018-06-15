package com.example.diku.foodserver;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.diku.foodserver.Model.OfferModel;
import com.example.diku.foodserver.ViewHolder.OfferViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Offers extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    public DatabaseReference ref_offers;
    FirebaseStorage storage;
    StorageReference storageReference;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<OfferModel,OfferViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        firebaseDatabase=FirebaseDatabase.getInstance();
        ref_offers=firebaseDatabase.getReference("carousel");
        recyclerView=(RecyclerView)findViewById(R.id.recycler_offerlist);




        loadOfferItems();


    }

    private void loadOfferItems() {


      ref_offers.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              OfferModel post = dataSnapshot.getValue(OfferModel.class);


          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

    }


}
