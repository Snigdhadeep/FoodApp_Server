package com.example.diku.foodserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diku.foodserver.Common.Common;
import com.example.diku.foodserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {


    EditText et_ph,et_pass;
    Button bt_signin;

    FirebaseDatabase database;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        et_ph=(EditText)findViewById(R.id.et_ph);
        et_pass=(EditText)findViewById(R.id.et_pass);
        bt_signin=(Button)findViewById(R.id.bt_signin2);

//connect with firebase
         database=FirebaseDatabase.getInstance();
         table_user=database.getReference("User");
//click the button sign in

        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signinUser(et_ph.getText().toString(),et_pass.getText().toString());


            }
        });



    }

    private void signinUser(String phone, String password) {

        final String localph=phone;
        final String localpassword=password;

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(localph).exists()){

                    User user=dataSnapshot.child(localph).getValue(User.class);
                    user.setPhone(localph);
                   if(Boolean.parseBoolean(user.getIsstaff())){
                       
                       if (user.getPassword().equals(localpassword)){
                           
                           Intent intent=new Intent(SigninActivity.this,Home.class);
                           Common.currentUser=user;
                           startActivity(intent);
                           finish();
                           
                       }
                       
                       else {

                           Toast.makeText(SigninActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                       }
                   }

                   else{

                       Toast.makeText(SigninActivity.this, "User is not staff", Toast.LENGTH_SHORT).show();

                   }

                }
                else{

                    Toast.makeText(SigninActivity.this, "user is not there in database", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
