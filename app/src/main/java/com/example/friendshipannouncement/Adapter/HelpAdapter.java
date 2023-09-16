package com.example.friendshipannouncement.Adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendshipannouncement.Model.Help;
import com.example.friendshipannouncement.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelpAdapter extends FirebaseRecyclerAdapter<Help,HelpAdapter.userAdapterHolder> {
    AlertDialog.Builder builder;




    public HelpAdapter(@NonNull FirebaseRecyclerOptions<Help> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userAdapterHolder holder, int position, @NonNull Help model) {

        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {builder= new AlertDialog.Builder(holder.delete.getContext());
                builder.setTitle("Sign Out").setMessage("Do you really want to Delete from team?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Help");
                                db.child(model.getKey()).removeValue();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });


    }

    @NonNull
    @Override
    public userAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item,parent,false);
        return new userAdapterHolder(view);
    }

    public class userAdapterHolder extends RecyclerView.ViewHolder {

        TextView name,email;
        ImageView delete;

        public userAdapterHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.personname);
            email = itemView.findViewById(R.id.email);
            delete = itemView.findViewById(R.id.delete);



        }
    }
}
