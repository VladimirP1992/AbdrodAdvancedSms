package ru.geekbrains.androidbase.smsapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageListAdapter  extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{
    private ArrayList<Message> messages;
    private static MessageListAdapter instance = null;

    private MessageListAdapter(){
       messages = new ArrayList<>();
    }

    public static MessageListAdapter getInstace(){
        return instance = (instance == null) ? (new MessageListAdapter()) : (instance);
    }

    public void addMessage(Message message){
        messages.add(message);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fillItemView(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView phoneValue;
        TextView messageValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phoneValue = itemView.findViewById(R.id.mi_phoneValue);
            messageValue = itemView.findViewById(R.id.mi_messageValue);
        }

        void fillItemView(Message message){
            phoneValue.setText(message.getPhoneNumber());
            messageValue.setText(message.getMessageBody());
        }
    }
}
