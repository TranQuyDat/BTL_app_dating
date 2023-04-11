package com.example.btl_app_dating;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.chat_viewholder> {


    private List<ChatMessage> messages;

    private int mes_right = 1;
    private int mes_left = 0;

    private String uid;

    public ChatAdapter(List<ChatMessage> messages, String uid) {

        this.messages = messages;
        this.uid = uid;
    }

    @NonNull
    @Override
    public chat_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int item_mes = 0;
        if (viewType == mes_left) {
            item_mes = R.layout.item_chat_mes_friend;
        } else {
            item_mes = R.layout.item_chat_mes_you;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(item_mes, parent, false);
        return new chat_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_viewholder holder, int position) {
        ChatMessage mes = messages.get(position);
        if (mes == null) return;
        holder.txt_mes.setText(mes.getMestxt());
        holder.sender.setText(mes.getSender());
        holder.time.setText(mes.getTime());
        Picasso picasso = Picasso.get();
        picasso.load(Uri.parse(mes.getAvt_mes_Id())).error(R.drawable.avatar1).into(holder.avt_mes_View);
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    class chat_viewholder extends RecyclerView.ViewHolder {
        private TextView sender;
        private TextView txt_mes;
        private TextView time;

        private ImageView avt_mes_View;

        public chat_viewholder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.sender);
            txt_mes = itemView.findViewById(R.id.txt_mes_view);
            time = itemView.findViewById(R.id.time_mes);
            avt_mes_View = itemView.findViewById(R.id.avt_mes);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (messages != null && messages.get(position) != null && messages.get(position).getSender_id() != null && messages.get(position).getSender_id().equalsIgnoreCase(uid)) {
            return mes_right;
        } else {
            return mes_left;
        }
    }

}