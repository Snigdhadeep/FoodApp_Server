package com.example.diku.foodserver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.Model.Order;
import com.example.diku.foodserver.Model.Request;
import com.example.diku.foodserver.Model.User;
import com.example.diku.foodserver.Model.User2;
import com.example.diku.foodserver.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderStatus extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DatabaseReference ref_users;
    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;
    MaterialSpinner statusspinner;
    String currentUserphno;
    User2 user;
    String currentUserTotal;
    String currenruserAdress;
    List<Order> cart=new ArrayList<>();
    int cartSize;

    String[] productName,productId,productPrice,productquantity ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
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

        firebaseDatabase =FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("Requests");
        ref_users=firebaseDatabase.getReference("User");

        recyclerView=(RecyclerView)findViewById(R.id.listorders);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadOrders();
<<<<<<< HEAD


=======
        
        
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e






    }

    private void loadOrders() {

        adapter=new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orderlayout,
                OrderViewHolder.class,
                reference


        ) {
            @Override
<<<<<<< HEAD
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {
=======
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e

                viewHolder.tvOrder_id.setText(adapter.getRef(position).getKey());
                viewHolder.tvOrder_status.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.tvOrder_address.setText(model.getAddress());
                viewHolder.tvOrder_phone.setText(model.getPhone());

<<<<<<< HEAD

                //new views
                viewHolder.txt_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        showUpdateDialog(adapter.getRef(position).getKey(),adapter.getItem(position));
                    }
                });

                viewHolder.txt_remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteOrder(adapter.getRef(position).getKey());
                    }
                });
                viewHolder.txt_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent orderdetailintent=new Intent(OrderStatus.this,OrderDetails.class);
                        Common.currentRequest=model;
                        orderdetailintent.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderdetailintent);

                    }
                });
=======
              /*  cart=model.getOrders();

                for(Order order:cart){

                    Log.i("productName",order.getProductName());
                    Log.i("productquanti",order.getQuantity());



                }*/
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e




<<<<<<< HEAD
                /* viewHolder.setItemclickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean isLongClick) {
                    }
                });*/
=======
                 viewHolder.setItemclickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean isLongClick) {

                    }
                });
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e

            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

<<<<<<< HEAD


    private void deleteOrder(String key) {
        reference.child(key).removeValue();
        adapter.notifyDataSetChanged();
=======
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.UPDATE))
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if (item.getTitle().equals(Common.DELETE))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(String key) {
        reference.child(key).removeValue();
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e
    }


    private void showUpdateDialog(String key, final Request item) {
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(this);
        alertdialog.setTitle("Update Order");
        alertdialog.setMessage("Please Choose Status");
        LayoutInflater inflater=this.getLayoutInflater();
        final View view =inflater.inflate(R.layout.update_order_layout,null);
        statusspinner=(MaterialSpinner)view.findViewById(R.id.status_spinner);
        statusspinner.setItems("Placed","ON my way","Shipped");
        alertdialog.setView(view);



        final String localkey=key;

        alertdialog.setView(view);
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

                item.setStatus(String.valueOf(statusspinner.getSelectedIndex()));
                Log.i("status54",""+String.valueOf(statusspinner.getSelectedIndex()));
                reference.child(localkey).setValue(item);
                Log.i("key147",""+localkey);
<<<<<<< HEAD
                adapter.notifyDataSetChanged();
=======
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e
                currentUserphno=item.getPhone();
                Log.i("currentUserphno",currentUserphno);
                currentUserTotal=item.getTotal();
                currenruserAdress=item.getAddress();

                String statusshipped=String.valueOf(statusspinner.getSelectedIndex());



                if(statusshipped=="2"){



                    cart=item.getOrders();
                    cartSize=cart.size();
                    productName=new String[cartSize];
                    productPrice=new String[cartSize];
                    productId=new String[cartSize];
                    productquantity=new String[cartSize];


                    for (int num = 0; num < cartSize; num++) {


                        productName[num]=cart.get(num).getProductName();
                        productPrice[num]=cart.get(num).getPrice();
                        productId[num]=cart.get(num).getProductId();
                        productquantity[num]=cart.get(num).getQuantity();





                    }

                    loadUserDaetails(currentUserphno,productName,productPrice,productId,localkey,currenruserAdress,productquantity,currentUserTotal );





                }




            }
        });



        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });





        alertdialog.show();






<<<<<<< HEAD
    }
=======
}
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e










    private void loadUserDaetails(String currentUserphno, final String[] productName, String[] productPrice,
                                  String[] productId, String key, String currenruserAdress,String[] productquantity,String currentUserTotal) {
        final String localph=currentUserphno;
        final String localAdress=currenruserAdress;
        final String localkey2=key;
        final String[] localproductName=productName;
        final String[] localproductPrice=productPrice;
        final String[] localproductId=productId;
        final String[] localproductquantity=productquantity;
        final String localcurrentUserTotal=currentUserTotal;

        for (int num = 0; num < cartSize; num++) {
            Log.i(" localproductName" + num, localproductName[num]);
            Log.i(" localproductPrice" + num, localproductPrice[num]);
            Log.i("localproductId" + num, localproductId[num]);
        }

        ref_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(dataSnapshot.child(localph).exists()){

                    User user=dataSnapshot.child(localph).getValue(User.class);

                    Log.i("localph123",localph);
                    Log.i("email123",user.getEmail());

                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    String[] to={""+user.getEmail()};

                    intent.putExtra(Intent.EXTRA_EMAIL,to);

                    intent.putExtra(Intent.EXTRA_SUBJECT,"Shipment of items in order "+localkey2+" by e-cart");

<<<<<<< HEAD
                    String name="Hi "+user.getName();
=======
                     String name="Hi "+user.getName();
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e

                    String s1="আপনার অর্ডারটি প্রেরণ করা হয়েছে। ";

                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Date dateobj = new Date();
                    System.out.println(df.format(dateobj));
                    String time="তারিখ এবং সময় "+String.valueOf(df.format(dateobj));
                    String bill="BILL TO : "+user.getName();
                    String address="ঠিকানা :"+localAdress;
                    String bill_ph="ফোন :"+localph;



                    String line=".....................................";
                    String productdetails=("");
                    int total_items=0;

<<<<<<< HEAD
                    for ( int num = 0; num < cartSize ; num++){
=======
                   for ( int num = 0; num < cartSize ; num++){
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e
                        productdetails = productdetails + "\n" + ("product:"+localproductName[num]+"\n"+"quantity-:"+localproductquantity[num]+"      "+"price:"+localproductPrice[num]+"\n");
                        total_items=total_items+Integer.parseInt(localproductquantity[num]);
                    }
                    String totalitems="Toatal Items :"+String.valueOf(total_items);
                    String total="Total Price:"+localcurrentUserTotal;



                    intent.putExtra(Intent.EXTRA_TEXT,","+name+"\n"+s1+"\n"+time+"\n"+line+"\n"+bill+"\n"+address+"\n"+bill_ph+"\n"+line+"\n"+productdetails+"\n"+line+"\n"+totalitems+"      "+total);

                    intent.setType("message/rfc822");
                    Intent chooser=Intent.createChooser(intent,"Send Email");
                    startActivity(chooser);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadCurrentOrders() {




    }


<<<<<<< HEAD
}
=======
}
>>>>>>> bebd0393869c8bce7d1603ebab67d15c2e966a7e
