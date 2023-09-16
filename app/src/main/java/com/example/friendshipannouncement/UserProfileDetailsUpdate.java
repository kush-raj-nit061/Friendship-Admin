package com.example.friendshipannouncement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileDetailsUpdate extends AppCompatActivity {
    TextView name,userId;
    EditText premRes,prem,birth,purl,branch,hobbies;
    CircleImageView image;
    Button save;
    String id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_details_update);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        userId = findViewById(R.id.userId);
        premRes = findViewById(R.id.premiumRes);
        prem = findViewById(R.id.premium);
        birth = findViewById(R.id.birthday);
        purl = findViewById(R.id.purl);
        branch = findViewById(R.id.branch);
        hobbies = findViewById(R.id.hobbies);
        save =  findViewById(R.id.save);
        Intent i = getIntent();
        Glide.with(getApplicationContext()).load(i.getStringExtra("purl")).into(image);
        name.setText(i.getStringExtra("name"));
        userId.setText(i.getStringExtra("id"));
        prem.setText(i.getStringExtra("premium"));
        premRes.setText(i.getStringExtra("premiumres"));
        birth.setText(i.getStringExtra("birth"));
        purl.setText(i.getStringExtra("purl"));
        branch.setText(i.getStringExtra("branch"));
        hobbies.setText(i.getStringExtra("hobbies"));
        birth.setText(i.getStringExtra("birthday"));
        id = i.getStringExtra("id");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("students");
                Map<String,Object> map = new HashMap<>();
                map.put("birthday",birth.getText().toString().trim());
                map.put("branch",branch.getText().toString().trim());
                map.put("hobbies",hobbies.getText().toString().trim());
                map.put("premium",prem.getText().toString().trim());
                map.put("premiumres",premRes.getText().toString().trim());
                map.put("purl",purl.getText().toString().trim());



                db.child(id).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(UserProfileDetailsUpdate.this,MainActivity.class);
                        Toast.makeText(UserProfileDetailsUpdate.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(i);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(UserProfileDetailsUpdate.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}