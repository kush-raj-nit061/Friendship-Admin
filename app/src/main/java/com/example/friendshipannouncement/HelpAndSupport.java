package com.example.friendshipannouncement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendshipannouncement.Adapter.HelpAdapter;
import com.example.friendshipannouncement.Model.Help;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HelpAndSupport extends AppCompatActivity {
    HelpAdapter userAdapter;
    LinearLayoutManager manager;
    RecyclerView rechelp;
    FloatingActionButton add;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);

        rechelp = findViewById(R.id.recHelp);
        add = findViewById(R.id.add);

        rechelp.setLayoutManager(new LinearLayoutManager(this));
//        rechelp.setHasFixedSize(true);
        rechelp.setItemAnimator(null);

        FirebaseRecyclerOptions<Help> options =
                new FirebaseRecyclerOptions.Builder<Help>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Help"), Help.class)
                        .build();
        userAdapter=new HelpAdapter(options);
        rechelp.setAdapter(userAdapter);
        userAdapter.startListening();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeBottomSheet();

            }
        });

    }

    private void makeBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_help, null);
        Button btnClose = view.findViewById(R.id.btn_dismiss);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button save = view.findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Help");
                EditText id = view.findViewById(R.id.idss);
                EditText name = view.findViewById(R.id.namess);
                EditText email = view.findViewById(R.id.emailss);
                String ids = id.getText().toString();
                if(!ids.isEmpty()){

                    Map<String,Object> map = new HashMap<>();
                    map.put("email",email.getText().toString());
                    map.put("name",name.getText().toString());
                    map.put("key",ids);


                    db.child(id.getText().toString().trim()).setValue(map);
                    bottomSheetDialog.dismiss();
                }else{
                    Toast.makeText(HelpAndSupport.this, "Add All details", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnClose.setOnClickListener(v1 -> bottomSheetDialog.dismiss());
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}