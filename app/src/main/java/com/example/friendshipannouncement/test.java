package com.example.friendshipannouncement;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    TextView tvForgot,tvSignup;
    ProgressBar progressbar;

    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);



        mAuth = FirebaseAuth.getInstance();




        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.password);
        Btn = findViewById(R.id.buttonSignIn);
//        tvForgot = findViewById(R.id.tvForgot);



        progressbar = findViewById(R.id.progressBarSignIn);
        progressbar.setVisibility(View.GONE);


//
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });

    }





    private void loginUserAccount()
    {


        String email, password;
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {




                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        fStore.collection("Managers").document(mAuth.getCurrentUser().getUid())
                                                .get().addOnCompleteListener(tasks -> {
                                                    if (tasks.isSuccessful()) {
                                                        DocumentSnapshot document = tasks.getResult();
                                                        if (document.exists()) {
                                                            String detailsGiven = document.getString("Admin");
                                                            if(detailsGiven.equals("2")){
                                                                Intent i = new Intent(test.this, MainActivity.class);
                                                                startActivity(i);
                                                            }else {
                                                                Toast.makeText(test.this, "You Don't have permission to manage data", Toast.LENGTH_SHORT).show();

                                                            }



                                                        }
                                                    }else {
                                                        progressbar.setVisibility(View.GONE);

                                                    }
                                                });

                                    }else {
                                        Toast.makeText(getApplicationContext(),"Please verify your Email id", Toast.LENGTH_LONG).show();

                                    }

                                    progressbar.setVisibility(View.GONE);



                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressbar.setVisibility(View.GONE);
                                }
                            }
                        });


    }
    @Override
    protected void onStart() {

        super.onStart();
        if(mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()){
            progressbar.setVisibility(View.VISIBLE);
            if(mAuth.getCurrentUser().isEmailVerified()){
                fStore.collection("Managers").document(mAuth.getCurrentUser().getUid())
                        .get().addOnCompleteListener(tasks -> {
                            if (tasks.isSuccessful()) {
                                DocumentSnapshot document = tasks.getResult();
                                if (document.exists()) {
                                    String detailsGiven = document.getString("Admin");
                                    if(detailsGiven.equals("2")){
                                        Intent i = new Intent(test.this, MainActivity.class);
                                        startActivity(i);
                                    }else {
                                        Toast.makeText(this, "You Don't have permission to manage data", Toast.LENGTH_SHORT).show();

                                    }



                                }
                            }else {
                                progressbar.setVisibility(View.GONE);

                            }
                        });

            }else {
                Toast.makeText(getApplicationContext(),"Please verify your Email id", Toast.LENGTH_LONG).show();

            }

        }



    }
}
