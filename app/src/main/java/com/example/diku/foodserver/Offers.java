package com.example.diku.foodserver;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Model.Category;
import com.example.diku.foodserver.Model.OfferModel;
import com.example.diku.foodserver.ViewHolder.OfferViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class Offers extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    public DatabaseReference ref_offers;
    FirebaseStorage storage;
    StorageReference storageReference;
    OfferModel post;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView iv_offeritem1,iv_offeritem2,iv_offeritem3,iv_offeritem4 ;
    TextView txt_offeritem1,txt_offeritem2,txt_offeritem3,txt_offeritem4;
    EditText et_addnewmenu;
    Button btn_select,btn_upload;

    Uri saveuri;

    FirebaseRecyclerAdapter<OfferModel,OfferViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        iv_offeritem1=(ImageView)findViewById(R.id.iv_offeritem1);
        txt_offeritem1=(TextView)findViewById(R.id.txt_offeritem1);

        iv_offeritem2=(ImageView)findViewById(R.id.iv_offeritem2);
        txt_offeritem2=(TextView)findViewById(R.id.txt_offeritem2);

        iv_offeritem3=(ImageView)findViewById(R.id.iv_offeritem3);
        txt_offeritem3=(TextView)findViewById(R.id.txt_offeritem3);

        iv_offeritem4=(ImageView)findViewById(R.id.iv_offeritem4);
        txt_offeritem4=(TextView)findViewById(R.id.txt_offeritem4);

        iv_offeritem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showUpdateDialog("1",post);

            }
        });
        iv_offeritem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog("2",post);
            }
        });
        iv_offeritem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog("3",post);

            }
        });
        iv_offeritem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog("4",post);

            }
        });


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

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        recyclerView=(RecyclerView)findViewById(R.id.recycler_offerlist);




        loadOfferItems();


    }

    private void loadOfferItems() {


      ref_offers.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              post = dataSnapshot.child("img").getValue(OfferModel.class);
              Picasso.with(getApplicationContext()).load(post.getImage1()).into(iv_offeritem1);
              txt_offeritem1.setText(post.getName1());
              Picasso.with(getApplicationContext()).load(post.getImage2()).into(iv_offeritem2);
              txt_offeritem2.setText(post.getName2());
              Picasso.with(getApplicationContext()).load(post.getImage3()).into(iv_offeritem3);
              txt_offeritem3.setText(post.getName3());
              Picasso.with(getApplicationContext()).load(post.getImage4()).into(iv_offeritem4);
              txt_offeritem4.setText(post.getName4());

              Log.i("image458",post.getImage1().toString());



          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

    }

    private void showUpdateDialog(final String key, final OfferModel item) {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Offers.this);
        alertdialog.setTitle("Update Category");
        alertdialog.setMessage("Please fill all information");

        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.addnew_menu,null);

        et_addnewmenu=view.findViewById(R.id.et_addnewmenu);
        btn_select=view.findViewById(R.id.btn_left);
        btn_upload=view.findViewById(R.id.btn_right);

        //
        if (key.equals("1")) {
            et_addnewmenu.setText(post.getName1());
        }else if (key.equals("2")){
            et_addnewmenu.setText(post.getName2());
        }else if (key.equals("3")){
            et_addnewmenu.setText(post.getName3());
        }else if (key.equals("4")){
            et_addnewmenu.setText(post.getName4());
        }

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(key,item);
            }
        });


        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });


        alertdialog.setView(view);
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();



                if (key.equals("1")) {
                    item.setName1(et_addnewmenu.getText().toString());
                }else if (key.equals("2")){
                    item.setName2(et_addnewmenu.getText().toString());
                }else if (key.equals("3")){
                    item.setName3(et_addnewmenu.getText().toString());
                }else if (key.equals("4")){
                    item.setName4(et_addnewmenu.getText().toString());
                }

                ref_offers.child("img").setValue(item);
              /* // item.setName(et_addnewmenu.getText().toString());

*/




            }
        });



        alertdialog.show();


    }

    private void chooseImage() {

        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Select Pictures"), Common.PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Common.PICK_IMAGE_REQUEST && resultCode == RESULT_OK

                && data != null && data.getData() != null

                ) {

            saveuri=data.getData();
            btn_select.setText("Image selected");

        }


    }

    private void changeImage(final String key, final OfferModel item) {

        if(saveuri!=null){

            final ProgressDialog mDialog=new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();

            String imageName= UUID.randomUUID().toString();
            final StorageReference imageFolder=storageReference.child("image/"+imageName);


            imageFolder.putFile(saveuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            mDialog.dismiss();
                            Toast.makeText(Offers.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set va;lue for new category & take downloaded link
                                    // newcategory=new Category(et_addnewmenu.getText().toString(),uri.toString());

                                    if (key.equals("1")) {
                                       item.setImage1(uri.toString());
                                    }else if (key.equals("2")){
                                        item.setImage2(uri.toString());;
                                    }else if (key.equals("3")){
                                        item.setImage3(uri.toString());;
                                    }else if (key.equals("4")){
                                        item.setImage4(uri.toString());;
                                    }

                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(Offers.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        //Dont worry about the error
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("uploaded"+progress+"%");
                        }
                    });





        }
    }


}
