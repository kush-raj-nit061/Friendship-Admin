package com.example.friendshipannouncement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.alimuzaffar.lib.widgets.AnimatedEditText;
import com.example.friendshipannouncement.Adapter.CollaborationAdapter;
import com.example.friendshipannouncement.Model.Collaboration;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;

public class CollaborationActivity extends AppCompatActivity {

    private RecyclerView recCollab;
    private CollaborationAdapter collabAdapter;
    AnimatedEditText searchEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaboration);
        recCollab = findViewById(R.id.collab);
        searchEditText = findViewById(R.id.etSearch);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        recCollab.setLayoutManager(manager);
        recCollab.setItemAnimator(null);
        recCollab.setItemViewCacheSize(20);
        recCollab.setDrawingCacheEnabled(true);
        recCollab.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Celebration");
        String userId = fAuth.getCurrentUser().getUid();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat datePatternFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        recCollab.setVisibility(View.GONE);


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
                    Query query = FirebaseDatabase.getInstance().getReference().child("Collab")
                            .orderByChild("projectname")
                            .startAt(searchText)
                            .endAt(searchText + "\uf8ff");

                    FirebaseRecyclerOptions<Collaboration> options = new FirebaseRecyclerOptions.Builder<Collaboration>()
                            .setQuery(query, Collaboration.class)
                            .build();

                    collabAdapter.updateOptions(options);

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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Collab");

        try {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int count = Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));
                    try {
                        Query query = reference.orderByChild("date").limitToLast(count);

                        FirebaseRecyclerOptions<Collaboration> options =
                                new FirebaseRecyclerOptions.Builder<Collaboration>()
                                        .setQuery(query, Collaboration.class)
                                        .build();

                        collabAdapter=new CollaborationAdapter(options);
                        recCollab.setAdapter(collabAdapter);
                        recCollab.setVisibility(View.VISIBLE);
                        collabAdapter.startListening();

                    }catch (Exception  e){}

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){

        }
    }
}