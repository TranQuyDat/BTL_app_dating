package com.example.btl_app_dating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chat_viewholder> {


    private List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public chat_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_mes_you,parent,false);
        return new chat_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_viewholder holder, int position) {
        ChatMessage mes = messages.get(position);
        if(mes ==null) return;
        holder.txt_mes.setText(mes.getMestxt());
        holder.sender.setText(mes.getSender());
        holder.time.setText(mes.getTime().toString());
    }

    @Override
    public int getItemCount() {
        if(messages !=null){
            return messages.size();
        }
        return 0;
    }

    class chat_viewholder extends RecyclerView.ViewHolder {
        private TextView sender;
        private TextView txt_mes;
        private TextView time;
        public chat_viewholder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            txt_mes =itemView.findViewById(R.id.txt_mes_view);
            time = itemView.findViewById(R.id.time_mes);
        }
    }


}
