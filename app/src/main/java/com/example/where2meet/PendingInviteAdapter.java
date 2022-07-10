package com.example.where2meet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.where2meet.databinding.ItemPendingInviteBinding;
import com.parse.ParseFile;

import java.util.List;

public class PendingInviteAdapter extends RecyclerView.Adapter<PendingInviteAdapter.ViewHolder> {
    private Context context;
    private List<Invite> inviteList;


    public PendingInviteAdapter(Context context, List<Invite> inviteList){
        this.context = context;
        this.inviteList = inviteList;
    }

    @NonNull
    @Override
    public PendingInviteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPendingInviteBinding itemPendingInviteBinding = ItemPendingInviteBinding.inflate(layoutInflater,parent,false);
        return  new ViewHolder(itemPendingInviteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingInviteAdapter.ViewHolder holder, int position) {
        Invite invite = inviteList.get(position);
        holder.bind(invite);

    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemPendingInviteBinding itemPendingInviteBinding;
        public ViewHolder(@NonNull ItemPendingInviteBinding itemPendingInviteBinding) {
            super(itemPendingInviteBinding.getRoot());
            this.itemPendingInviteBinding = itemPendingInviteBinding;
        }

        public void bind(Invite invite) {
            Log.i("Title","location: " + invite.getTitle());
            itemPendingInviteBinding.tvPendingInviteTitle.setText(invite.getTitle());
            itemPendingInviteBinding.tvPendingInviteSendersName.setText(invite.getSender().getUsername());
            itemPendingInviteBinding.tvPendingUsersAddress.setText(invite.getAddress());

            ParseFile image = invite.getSender().getParseFile("profileImage");

            if(image == null){
                Glide.with(context).load(R.drawable.ic_baseline_person_24).override(100,200).centerCrop().into(itemPendingInviteBinding.ivPendingInviteSendersProfileImage);
            }
            else{
                Glide.with(context).load(image.getUrl()).override(100,200).centerCrop().into(itemPendingInviteBinding.ivPendingInviteSendersProfileImage);
            }


        }
    }
}
