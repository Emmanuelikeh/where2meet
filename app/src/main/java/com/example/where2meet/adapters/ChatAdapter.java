package com.example.where2meet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.where2meet.R;
import com.example.where2meet.databinding.IncomingMessagesBinding;
import com.example.where2meet.databinding.OutgoingMessagesBinding;
import com.example.where2meet.models.Messages;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<Messages> messagesList;
    private static final int MESSAGE_OUTGOING = 0;
    private static final int MESSAGE_INCOMING = 1;

    public ChatAdapter(Context context,List<Messages> messagesList ){
        this.context = context;
        this.messagesList = messagesList;
    }

    // differentiate between the current user and other users,
    @Override
    public int getItemViewType(int position) {
        if(isCurrentUser(position)){
            return MESSAGE_OUTGOING;
        }
        else{
            return MESSAGE_INCOMING;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // checking the value of the viewTypes to proceed
        if(viewType == MESSAGE_OUTGOING){
            // inflating the binding class
            OutgoingMessagesBinding outgoingMessagesBinding = OutgoingMessagesBinding.inflate(inflater,parent,false);
            return new MessageOutgoingHolder(outgoingMessagesBinding);
        }
        else{
            IncomingMessagesBinding incomingMessagesBinding = IncomingMessagesBinding.inflate(inflater, parent, false);
            return new MessageIncomingHolder(incomingMessagesBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Messages messages = messagesList.get(position);
        holder.bind(messages);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    private boolean isCurrentUser(int position) {
        Messages message = messagesList.get(position);
        ParseUser currentUser = ParseUser.getCurrentUser();
        return Objects.equals(message.getMessageSender().getObjectId(), currentUser.getObjectId());
    }


    public abstract class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        // abstract method declaration (subclasser responsibility)
        abstract void bind(Messages messages);
    }
      // special holder for incoming messages
    public class MessageIncomingHolder extends ViewHolder{
        private IncomingMessagesBinding incomingMessagesBinding;
        public MessageIncomingHolder(@NonNull IncomingMessagesBinding incomingMessagesBinding) {
            super(incomingMessagesBinding.getRoot());
            this.incomingMessagesBinding = incomingMessagesBinding;
        }
        //specific method definition
        @Override
        public void bind(Messages messages) {
            incomingMessagesBinding.tvIMBody.setText(messages.getMessageBody());
            try {
               String name = messages.getMessageSender().fetchIfNeeded().getUsername();
                incomingMessagesBinding.tvIMUserName.setText(name);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
     // special holder for outgoing messages
    public class MessageOutgoingHolder extends ViewHolder{
        private  OutgoingMessagesBinding outgoingMessagesBinding;
        public MessageOutgoingHolder(@NonNull OutgoingMessagesBinding outgoingMessagesBinding) {
            super(outgoingMessagesBinding.getRoot());
            this.outgoingMessagesBinding = outgoingMessagesBinding;
        }

        //specific method definition
        @Override
        public void bind(Messages messages) {
            outgoingMessagesBinding.tvOMBody.setText(messages.getMessageBody());
            outgoingMessagesBinding.tvOMUserName.setText(R.string.current_user_chat_name);
        }
    }



}
