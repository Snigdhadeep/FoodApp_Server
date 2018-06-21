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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.Model.Category;
import com.example.diku.foodserver.Model.FoodList;
import com.example.diku.foodserver.ViewHolder.FoodViewHolder2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class FoodListclass extends AppCompatActivity {



    String categoryid="";

    FirebaseDatabase firebaseDatabase;
    public DatabaseReference fooditems;
    FirebaseStorage storage;
    StorageReference storageReference;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<FoodList,FoodViewHolder2> adapter;

    EditText et_additemName,et_additemdescription,et_additemPrice,et_additemDiscount,et_additem_unit;
    Button btn_select,btn_upload;

    FoodList foodList;
    Uri saveuri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_listclass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showItemDialog();
            }
        });


        firebaseDatabase=FirebaseDatabase.getInstance();
        fooditems=firebaseDatabase.getReference("Foods");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        recyclerView=(RecyclerView)findViewById(R.id.rv_foodlist);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!=null) {

            categoryid = getIntent().getStringExtra("foodkey");
        }

        if(!categoryid.isEmpty() && categoryid!= null){

            loadlist(categoryid);
            Toast.makeText(this, "function worked"+categoryid, Toast.LENGTH_SHORT).show();
        }

    }

    private void showItemDialog() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(FoodListclass.this);
        alertdialog.setTitle("Add New Food items");
        alertdialog.setMessage("Please fill all information");

        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.addnew_fooditem,null);

        et_additemName=view.findViewById(R.id.et_additemName);
        et_additemdescription=view.findViewById(R.id.et_additemdescription);
        et_additemPrice=view.findViewById(R.id.et_additemPrice);
        et_additemDiscount=view.findViewById(R.id.et_additemDiscount);
        et_additem_unit=view.findViewById(R.id.et_additem_unit);




        btn_select=view.findViewById(R.id.btn_left);
        btn_upload=view.findViewById(R.id.btn_right);


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               uploadImage();
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
                fooditems.push().setValue(foodList);

            }
        });



        alertdialog.show();




    }

    private void loadlist(String categoryid) {


        adapter=new FirebaseRecyclerAdapter<FoodList, FoodViewHolder2>(
                FoodList.class,
                R.layout.fooditems,
                FoodViewHolder2.class,
                fooditems.orderByChild("menuid").equalTo(categoryid)

        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder2 viewHolder, FoodList model, int position) {
                viewHolder.txt_item_food.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.img_item_food);


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onclick(View view, int position, boolean isLongClick) {

                        
                        
                    }
                });

            }
        };



        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);


    }



    private void uploadImage() {

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
                            Toast.makeText(FoodListclass.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set va;lue for new fooditems & take downloaded link
                              foodList=new FoodList(et_additemdescription.getText().toString(),
                                      et_additemDiscount.getText().toString(),
                                      uri.toString(),
                                      categoryid,
                                      et_additemName.getText().toString(),
                                      et_additemPrice.getText().toString(),
                                      et_additem_unit.getText().toString()
                              );

                                    Toast.makeText(FoodListclass.this, ""+categoryid, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(FoodListclass.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

                ){

            saveuri=data.getData();
            btn_select.setText("Image selected");

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE)){

            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));

        }
        else if(item.getTitle().equals(Common.DELETE)) {


        }

       /* if(item.getTitle().equals(Common.UPDATE)){


            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
//adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder())
        }else if(item.getTitle().equals(Common.DELETE)){


            deleteCategory(adapter.getRef(item.getOrder()).getKey());
//adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder())
        }

     */
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(final String key, final FoodList item) {
        AlertDialog.Builder alertdialog=new AlertDialog.Builder(FoodListclass.this);
        alertdialog.setTitle("Update Category");
        alertdialog.setMessage("Please fill all information");

        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.addnew_fooditem,null);

        et_additemName=view.findViewById(R.id.et_additemName);
        et_additemdescription=view.findViewById(R.id.et_additemdescription);
        et_additemPrice=view.findViewById(R.id.et_additemPrice);
        et_additemDiscount=view.findViewById(R.id.et_additemDiscount);
        et_additem_unit=view.findViewById(R.id.et_additem_unit);

        btn_select=view.findViewById(R.id.btn_left);
        btn_upload=view.findViewById(R.id.btn_right);

        //
        et_additemName.setText(item.getName());
        et_additemdescription.setText(item.getDescription());
        et_additemPrice.setText(item.getPrice());
        et_additemDiscount.setText(item.getDiscount());
        et_additem_unit.setText(item.getUnit());


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

      /*  btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage(item);
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
                // ref.push().setValue(newcategory);

                item.setName(et_additemName.getText().toString());
                item.setDiscount(et_additemDiscount.getText().toString());
                item.setDescription(et_additemdescription.getText().toString());
                item.setPrice(et_additemPrice.getText().toString());
                item.setUnit(et_additem_unit.getText().toString());

                fooditems.child(key).setValue(item);
            }
        });



        alertdialog.show();

    }




    private void changeImage(final FoodList item) {

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
                            Toast.makeText(FoodListclass.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set va;lue for new category & take downloaded link
                                    // newcategory=new Category(et_addnewmenu.getText().toString(),uri.toString());
                                   item.setImage(uri.toString());
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(FoodListclass.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
