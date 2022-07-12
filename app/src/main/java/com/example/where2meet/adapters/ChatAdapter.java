package com.example.where2meet.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where2meet.models.Messages;
import com.parse.ParseUser;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<Messages> messagesList;
    private static final int MESSAGE_OUTGOING = 216;
    private static final int MESSAGE_INCOMING = 612;


    public ChatAdapter(Context context,List<Messages> messagesList ){
        this.context = context;
        this.messagesList = messagesList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isMe(position)){
            return  MESSAGE_OUTGOING;
        }
        else{
            return MESSAGE_INCOMING;
        }
    }

    private boolean isMe(int position) {
        Messages message = messagesList.get(position);
        ParseUser currentUser = ParseUser.getCurrentUser();
        return Objects.equals(message.getMessageSender().getObjectId(), currentUser.getObjectId());
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
