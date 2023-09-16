package com.example.friendshipannouncement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.alimuzaffar.lib.widgets.AnimatedEditText;
import com.example.friendshipannouncement.Model.UserModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    NavigationView navView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    LottieAnimationView progress;
    ActionBarDrawerToggle toggle;
    TextView drawerName;

    CardView help,collab,maintenance,coolFeatured,featured,status;
    RecyclerView recView;
    UserAdapter userAdapter;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    AnimatedEditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = findViewById(R.id.userProfile);
        searchEditText = findViewById(R.id.etSearch);
        progress = findViewById(R.id.progress);
        maintenance = findViewById(R.id.cvMaintenance);
        coolFeatured = findViewById(R.id.cvCoolFeatured);
        featured = findViewById(R.id.cvFeatured);
        status = findViewById(R.id.cvStatus);
        drawerName = findViewById(R.id.drawerName);
        reference.child("students").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserModel model = snapshot.getValue(UserModel.class);
                    drawerName.setText(model.getName());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        recView.setLayoutManager(manager);
        recView.setItemAnimator(null);
        recView.setItemViewCacheSize(20);
        recView.setDrawingCacheEnabled(true);
        recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        collab = findViewById(R.id.cvCollab);
        navView = findViewById(R.id.navmenu);
        drawerLayout=findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.syncState();
        help = findViewById(R.id.cvHelp);

        collab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CollaborationActivity.class);
                startActivity(i);

            }
        });
        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, StatusActivity.class);
                startActivity(i);

            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HelpAndSupport.class);
                startActivity(i);
            }
        });
        maintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MaintainanceActivity.class);
                startActivity(i);
            }
        });
        coolFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CoolFeaturedActivity.class);
                startActivity(i);
            }
        });
        featured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, FeaturedActivity.class);
                startActivity(i);
            }
        });

        initializeRecyclerView();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nothing to do here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().trim();

                // Update your adapter with the new options
                try {
                    // Create a query based on the entered text
                    Query query = FirebaseDatabase.getInstance().getReference().child("students")
                            .orderByChild("name")
                            .startAt(searchText)
                            .endAt(searchText + "\uf8ff");

                    FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                            .setQuery(query, UserModel.class)
                            .build();

                    userAdapter.updateOptions(options);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Orientation changed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Nothing to do here
            }
        });

    }
    private void initializeRecyclerView() {
        reference = FirebaseDatabase.getInstance().getReference();
        try {
            FirebaseRecyclerOptions<UserModel> options =
                    new FirebaseRecyclerOptions.Builder<UserModel>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("students")
                                    .orderByChild("likes"), UserModel.class)
                            .build();
            userAdapter = new UserAdapter(options);
            recView.setAdapter(userAdapter);
            userAdapter.startListening();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);


                }
            },1500);



        }catch (Exception e){

            Toast.makeText(getApplicationContext(), "Something error in students data", Toast.LENGTH_SHORT).show();
        }





    }

}