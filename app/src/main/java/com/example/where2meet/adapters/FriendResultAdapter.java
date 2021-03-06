package com.example.where2meet.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.R;
import com.example.where2meet.databinding.ItemFriendBinding;
import com.example.where2meet.utils.GlideUtil;
import com.parse.ParseFile;
import com.parse.ParseUser;

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
            GlideUtil.getImage(96,96, itemFriendBinding.ivFriendImage, image,context);
        }
        @Override
        public void onClick(View v) {
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
