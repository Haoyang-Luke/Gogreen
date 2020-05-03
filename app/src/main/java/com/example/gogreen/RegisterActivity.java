package com.example.gogreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmailEt, mPasswordEt,mNameEt;
    Button mRegisterBtn;
    TextView metAlready;

    private FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        mEmailEt = findViewById(R.id.etEmail);
        mNameEt = findViewById(R.id.etUsername);
        mPasswordEt = findViewById(R.id.etPassword);
        mRegisterBtn = findViewById(R.id.bRegister);
        metAlready = findViewById(R.id.etAlready);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering user...");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oder=Integer.toString(100000);
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                String name=mNameEt.getText().toString().trim();//get the username
                String point=Integer.toString(0);
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){

                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);//The meaning of the pop-up text edit box
                }
                else if (password.length()<6){
                    mPasswordEt.setError("Password length at least 6 characters");
                    mPasswordEt.setFocusable(true);

                }

                else {
                    registerUser(email,password,name,point,oder);
                }

            }
        });

        //handle login click
        metAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String email, String password, String name, String point, final String oder) {

        progressDialog.show();
        final String username=name;
        final String userpoint=point;
        final String useroder=oder;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            //get use mail and uid from auth
                            String email= user.getEmail();
                            //String name=user.getDisplayName();
                            String uid= user.getUid();
                            String item="Please add item";
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("email", email);
                            hashMap.put("uid", uid);
                            hashMap.put("name", username);
                            hashMap.put("phone", "");
                            hashMap.put("image", "");
                            hashMap.put("cover", "");
                            hashMap.put("points",userpoint);
                            hashMap.put("oder",oder);
                            hashMap.put("rItem1",item);
                            hashMap.put("rItem2",item);
                            hashMap.put("rItem3",item);

                            //firebase database instance
                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference =database.getReference("Users");

                            reference.child(uid).setValue(hashMap);




                            Toast.makeText(RegisterActivity.this, "Registered.....\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}


/*TESTING
 1.Run project;
 2.Enter invalid email pattern e.g. without @,.com ;
 3.Enter empty password or less than 6 characters;
 4.Enter valid email/password;
 5. Enter existing(registered) email (it should not be accepted);
 6.Check Registered user in Firebase;

 */

