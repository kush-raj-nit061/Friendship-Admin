package com.example.friendshipannouncement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.friendshipannouncement.Model.Collaboration;
import com.example.friendshipannouncement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CollaborationAdapter extends FirebaseRecyclerAdapter<Collaboration,CollaborationAdapter.userAdapterHolder> {




    public CollaborationAdapter(@NonNull FirebaseRecyclerOptions<Collaboration> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userAdapterHolder holder, int position, @NonNull Collaboration model) {

        Glide.with(holder.image.getContext()).load(model.getPurl()).into(holder.image);
        holder.projectName.setText(model.getProjectname());
        holder.projectType.setText(model.getProjecttype());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Collab");
                db.child(model.getId()).removeValue();
            }
        });





    }

    @NonNull
    @Override
    public userAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collab_item,parent,false);
        return new userAdapterHolder(view);
    }

    public class userAdapterHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView projectName,projectType;
        Button delete;




        public userAdapterHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            projectName = itemView.findViewById(R.id.projectName);
            projectType = itemView.findViewById(R.id.projectType);
            delete = itemView.findViewById(R.id.delete);


        }
    }
}
