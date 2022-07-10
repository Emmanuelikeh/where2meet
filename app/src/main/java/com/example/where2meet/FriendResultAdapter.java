package com.example.where2meet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.databinding.ItemFriendBinding;
import com.example.where2meet.databinding.ItemSearchresultBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendResultAdapter extends RecyclerView.Adapter<FriendResultAdapter.ViewHolder>{
     private List<ParseUser> parseUserList;
     private Context context;


     public FriendResultAdapter(Context context,List<ParseUser> parseUserList){
         this.context = context;
         this.parseUserList = parseUserList;
     }


    @NonNull
    @Override
    public FriendResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFriendBinding itemFriendBinding = ItemFriendBinding.inflate(inflater,parent,false);
        return new ViewHolder(itemFriendBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendResultAdapter.ViewHolder holder, int position) {
         ParseUser parseUser = parseUserList.get(position);
         holder.bind(parseUser);

    }

    @Override
    public int getItemCount() {
        return parseUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private ItemFriendBinding itemFriendBinding;
        public ViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemFriendBinding = itemFriendBinding;
            itemView.setOnClickListener(this);
        }

        public void bind(ParseUser parseUser) {
            itemFriendBinding.tvFriendName.setText(parseUser.getUsername());
            itemFriendBinding.tvFriendEmail.setText(parseUser.getEmail());
            ParseFile image = parseUser.getParseFile("profileImage");
            // check if user has a profile image and sets a default image if not
            if(image != null){
                Glide.with(context).load(image.getUrl()).override(100,200).centerCrop().into(itemFriendBinding.ivFriendImage);
            }
            else{
                Glide.with(context).load(R.drawable.ic_baseline_person_24).override(100,200).centerCrop().into(itemFriendBinding.ivFriendImage);
            }
        }

        @Override
        public void onClick(View v) {
           Toast.makeText(context, "finished", Toast.LENGTH_SHORT).show();
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                //transfer selected users information to the parent fragment to continue due process
                ParseUser parseUser = parseUserList.get(position);
                Intent i = new Intent();
                i.putExtra("parseUser", parseUser);
                ((Activity) context).setResult(Activity.RESULT_OK,i);
                ((Activity) context).finish();

            }
        }
    }

    public void clear() {
         parseUserList.clear();
        notifyDataSetChanged();
    }
}
