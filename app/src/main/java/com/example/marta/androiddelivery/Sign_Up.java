package com.example.marta.androiddelivery;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marta.androiddelivery.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Sign_Up extends AppCompatActivity {

    MaterialEditText editPhone, editName, editPassword;
    Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        editPassword =(MaterialEditText)findViewById(R.id.editPassword);
        editPhone =(MaterialEditText)findViewById(R.id.editPhone);
        editName =(MaterialEditText)findViewById(R.id.editName);

        btnSignUp = (Button)findViewById(R.id.btnSignUp);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_User = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog nDialog = new ProgressDialog(Sign_Up.this);
                nDialog.setMessage("Please waiting...");
                nDialog.show();

                table_User.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                            nDialog.dismiss();
                            Toast.makeText(Sign_Up.this, "Phone number already exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            nDialog.dismiss();
                            User User = new User(editName.getText().toString(), editPassword.getText().toString());
                            table_User.child(editPhone.getText().toString()).setValue(User);
                            Toast.makeText(Sign_Up.this, "Sign Up successfully", Toast.LENGTH_SHORT).show();
                            finish();
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
