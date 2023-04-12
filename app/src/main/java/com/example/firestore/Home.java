package com.example.firestore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Document;

public class Home extends AppCompatActivity {

    TextView tv1,tv2;
    Button logout;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        logout = findViewById(R.id.logout);
        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = fauth.getCurrentUser().getUid();
        DocumentReference reference = fstore.collection("users").document(userId);

        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
              tv1.setText(documentSnapshot.getString("name"));
              tv2.setText(documentSnapshot.getString("email Id"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fauth.signOut();
                startActivity(new Intent(Home.this,MainActivity.class));
                finish();
            }
        });




    }
}