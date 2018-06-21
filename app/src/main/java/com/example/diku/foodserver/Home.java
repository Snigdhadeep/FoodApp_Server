package com.example.diku.foodserver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.diku.foodserver.AutogridLayoutManeger.AutogridLayoutManeger;
import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Interface.ItemClickListener;
import com.example.diku.foodserver.Model.Category;
import com.example.diku.foodserver.Model.Order;
import com.example.diku.foodserver.ViewHolder.MenuViewHolder;
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
import java.util.zip.Inflater;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;


    TextView txt_fullname;
    RecyclerView recyclerMenu;
    RecyclerView.LayoutManager layoutManager;

   EditText et_addnewmenu;
   Button btn_select;
   Button btn_upload;

    Category newcategory;
    Uri saveuri;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firebaseDatabase=FirebaseDatabase.getInstance();
        ref=firebaseDatabase.getReference("category");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        View viewheader=navigationView.getHeaderView(0);

        txt_fullname=(TextView)viewheader.findViewById(R.id.txt_fullname);
        txt_fullname.setText(Common.currentUser.getName());

        recyclerMenu=(RecyclerView)findViewById(R.id.recycler_menuhome);

        AutogridLayoutManeger layoutManager = new AutogridLayoutManeger(this, 200);
        recyclerMenu.setHasFixedSize(true);
        recyclerMenu.setLayoutManager(layoutManager);
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerMenu.setLayoutManager(manager);


        loadmenu();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


    }

    private void showDialog() {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Home.this);
        alertdialog.setTitle("Add New Category");
        alertdialog.setMessage("Please fill all information");

     LayoutInflater inflater=this.getLayoutInflater();
     View view=inflater.inflate(R.layout.addnew_menu,null);

        et_addnewmenu=view.findViewById(R.id.et_addnewmenu);
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
                ref.push().setValue(newcategory);

            }
        });



        alertdialog.show();




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
                           Toast.makeText(Home.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                           imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   //set va;lue for new category & take downloaded link
                                   newcategory=new Category(et_addnewmenu.getText().toString(),uri.toString());
                               }
                           });

                       }
                   }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   mDialog.dismiss();
                   Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
        startActivityForResult(intent.createChooser(intent,"Select Pictures"),Common.PICK_IMAGE_REQUEST);
    }


    // press  ctrl+o
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

    private void loadmenu() {

            adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
                    Category.class,
                    R.layout.menu_item,
                    MenuViewHolder.class,
                    ref
            ) {
                @Override
                protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                    viewHolder.txt_item_menu.setText(model.getName().toString());
                    Picasso.with(Home.this).load(model.getImg()).into(viewHolder.img_item_menu);


                   viewHolder.setItemclickListener(new ItemClickListener() {
                       @Override
                       public void onclick(View view, int position, boolean isLongClick) {



                           Intent intent =new Intent(Home.this,FoodListclass.class);
                           intent.putExtra("foodkey",adapter.getRef(position).getKey());
                           Log.i("foodkey",adapter.getRef(position).getKey().toString());
                           startActivity(intent);




                       }
                   });



                }
            };


            adapter.notifyDataSetChanged();
            recyclerMenu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_offer) {
            Intent intent=new Intent(getApplicationContext(),Offers.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {

          /*  Intent intent=new Intent(this,Cart.class);
            startActivity(intent);*/

        } else if (id == R.id.nav_orders) {
            Intent intent=new Intent(getApplicationContext(),OrderStatus.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {

            Intent intent=new Intent(this,SigninActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //UPDATE & DELETE
//press ctrl+o

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE)){


            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
//adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder())
        }else if(item.getTitle().equals(Common.DELETE)){


            deleteCategory(adapter.getRef(item.getOrder()).getKey());
//adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder())
        }

        return super.onContextItemSelected(item);


    }

    private void deleteCategory(String key) {
        ref.child(key).removeValue();
    }


    private void showUpdateDialog(final String key, final Category item) {

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(Home.this);
        alertdialog.setTitle("Update Category");
        alertdialog.setMessage("Please fill all information");

        LayoutInflater inflater=this.getLayoutInflater();
        View view=inflater.inflate(R.layout.addnew_menu,null);

        et_addnewmenu=view.findViewById(R.id.et_addnewmenu);
        btn_select=view.findViewById(R.id.btn_left);
        btn_upload=view.findViewById(R.id.btn_right);

        //
        et_addnewmenu.setText(item.getName());


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

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

            item.setName(et_addnewmenu.getText().toString());
            ref.child(key).setValue(item);
        }
    });



        alertdialog.show();


}


    private void changeImage(final Category item) {

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
                            Toast.makeText(Home.this, "uploaded successfully", Toast.LENGTH_SHORT).show();
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set va;lue for new category & take downloaded link
                                   // newcategory=new Category(et_addnewmenu.getText().toString(),uri.toString());
                                    item.setImg(uri.toString());
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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



