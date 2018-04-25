package com.example.marta.androiddelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marta.androiddelivery.Common.Common;
import com.example.marta.androiddelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText editPassword,editPhone;
    Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword =(MaterialEditText)findViewById(R.id.editPassword);
        editPhone =(MaterialEditText)findViewById(R.id.editPhone);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_User = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog nDialog = new ProgressDialog(SignIn.this);
                nDialog.setMessage("Please waiting...");
                nDialog.show();

                table_User.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(editPhone.getText().toString()).exists()) {


                            nDialog.dismiss();
                            User User = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
                            if (User.getPassword().equals(editPassword.getText().toString())) {
                                Intent homeIntent = new Intent(SignIn.this,Home.class);
                                Common.currentUser = User;
                                startActivity(homeIntent);
                                finish();
                            } else {
                                Toast.makeText(SignIn.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            nDialog.dismiss();
                            Toast.makeText(SignIn.this, "User not exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
