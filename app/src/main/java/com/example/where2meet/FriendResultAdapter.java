package com.example.where2meet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.databinding.ItemFriendBinding;
import com.example.where2meet.databinding.ItemSearchresultBinding;
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemFriendBinding itemFriendBinding;
        public ViewHolder(@NonNull ItemFriendBinding itemFriendBinding) {
            super(itemFriendBinding.getRoot());
            this.itemFriendBinding = itemFriendBinding;
        }


        public void bind(ParseUser parseUser) {
            itemFriendBinding.tvFriendName.setText(parseUser.getUsername());
            itemFriendBinding.tvFriendEmail.setText(parseUser.getEmail());
            ParseFile image = parseUser.getParseFile("profileImage");
            if(image != null){
                Glide.with(context).load(image.getUrl()).override(100,200).centerCrop().into(itemFriendBinding.ivFriendImage);
            }
            else{
                Glide.with(context).load(R.drawable.ic_baseline_person_24).override(100,200).centerCrop().into(itemFriendBinding.ivFriendImage);
            }


        }
    }

    public void clear() {
         parseUserList.clear();
        notifyDataSetChanged();
    }
}
