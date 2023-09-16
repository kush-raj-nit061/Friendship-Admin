package com.example.friendshipannouncement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.alimuzaffar.lib.widgets.AnimatedEditText;
import com.example.friendshipannouncement.Adapter.StatusAdapter;
import com.example.friendshipannouncement.Model.Status;
import com.example.friendshipannouncement.Model.UserModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class StatusActivity extends AppCompatActivity {
    RecyclerView recView;
    StatusAdapter userAdapter;
    LottieAnimationView progress;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    AnimatedEditText searchEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        recView = findViewById(R.id.statuses);
        searchEditText = findViewById(R.id.etSearch);
        progress = findViewById(R.id.progress);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recView.setLayoutManager(manager);
        recView.setItemAnimator(null);
        recView.setItemViewCacheSize(20);
        recView.setDrawingCacheEnabled(true);
        recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

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
                    Query query = FirebaseDatabase.getInstance().getReference().child("Status")
                            .orderByChild("name")
                            .startAt(searchText)
                            .endAt(searchText + "\uf8ff");

                    FirebaseRecyclerOptions<Status> options = new FirebaseRecyclerOptions.Builder<Status>()
                            .setQuery(query, Status.class)
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
            FirebaseRecyclerOptions<Status> options =
                    new FirebaseRecyclerOptions.Builder<Status>()
                            .setQuery(FirebaseDatabase.getInstance().getReference().child("Status")
                                    .orderByChild("likes"), Status.class)
                            .build();
            userAdapter = new StatusAdapter(options);
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