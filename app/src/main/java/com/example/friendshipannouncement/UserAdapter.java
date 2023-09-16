package com.example.friendshipannouncement;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.friendshipannouncement.Model.UserModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zyp.cardview.YcCardView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends FirebaseRecyclerAdapter<UserModel,UserAdapter.userAdapterHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference();
    String myPurl;



    public UserAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userAdapterHolder holder, int position, @NonNull UserModel model) {

        holder.name.setText(model.getName());
        holder.branch.setText(model.getBranch());
        holder.year.setText(model.getYear());
        holder.id.setText(model.getUserId());
        try {
            holder.premium.setVisibility(Integer.parseInt(model.getPremium()));
            holder.premium.setAnimationFromUrl(model.getPremiumres());
        }catch (Exception e){}

        holder.tvCollege.setText(model.getCollege());
        Glide.with(holder.profileImage.getContext()).load(model.getPurl()).into(holder.profileImage);

        holder.userCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context cont = v.getContext();
                Intent i = new Intent(cont,UserProfileDetailsUpdate.class);
                i.putExtra("purl",model.getPurl());
                i.putExtra("branch",model.getBranch());
                i.putExtra("birthday",model.getBirthday());
                i.putExtra("hobbies",model.getHobbies());
                i.putExtra("premium",model.getPremium());
                i.putExtra("premiumres",model.getPremiumres());
                i.putExtra("id",model.getUserId());
                i.putExtra("name",model.getName());
                cont.startActivity(i);


            }
        });

    }

    @NonNull
    @Override
    public userAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userprofile_item,parent,false);
        return new userAdapterHolder(view);
    }

    public class userAdapterHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView name;
        TextView branch;
        TextView year;
        LottieAnimationView premium;
        TextView tvCollege,id;
        YcCardView userCard;



        public userAdapterHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            branch=itemView.findViewById(R.id.branch);
            year = itemView.findViewById(R.id.year);
            premium =itemView.findViewById(R.id.premium);
            tvCollege = itemView.findViewById(R.id.college);
            id = itemView.findViewById(R.id.id);
            profileImage = itemView.findViewById(R.id.image);
            userCard = itemView.findViewById(R.id.userCard);

        }
    }
}
