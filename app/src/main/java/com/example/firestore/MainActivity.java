package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2;
    Button blog,breg;
    FirebaseAuth fauth;
    FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        blog = findViewById(R.id.blog);
        breg = findViewById(R.id.breg);
        fauth = FirebaseAuth.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);

            }
        });

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String semail = et1.getText().toString();
                String spass = et2.getText().toString();

                if(TextUtils.isEmpty(semail)){
                    et1.setError("Enetr your email");
                    return;
                }
                if(TextUtils.isEmpty(spass)){
                    et2.setError("Enter your password");
                    return;
                }
                if(spass.length()<6){
                    et2.setError("Password should have atleast 7 characters");
                    return;
                }

                fauth.signInWithEmailAndPassword(semail,spass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {


                        if(fuser.isEmailVerified()){
                            Toast.makeText(MainActivity.this, "Login succesfull", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(MainActivity.this,Home.class);
                            startActivity(i1);
                        }else{
                            Toast.makeText(MainActivity.this, "Email not verified try again..", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Login Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}