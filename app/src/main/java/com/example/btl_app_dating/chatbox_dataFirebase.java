package com.example.btl_app_dating;

import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar;
import com.google.firebase.ktx.Firebase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatbox_dataFirebase {
    private RecyclerView rcv_chatbox;
    private DatabaseReference db_messenger = FirebaseDatabase.getInstance().getReference("mess");
    private Timestamp time = new Timestamp(System.currentTimeMillis());
    private EditText input_text;

    private List<ChatMessage> list_chatobj = new ArrayList<>();
    private ChatAdapter adapter;

    public chatbox_dataFirebase(EditText input_text,RecyclerView rcv_chatbox) {
        this.rcv_chatbox =rcv_chatbox;
        this.input_text = input_text;
        adapter = new ChatAdapter(list_chatobj);
        rcv_chatbox.setAdapter(adapter);
    }
    int id =0;
    public void update_chat(){
        String mes = this.input_text.getText().toString();
        if (mes.isEmpty()) return;
        ChatMessage chat = new ChatMessage(R.drawable.avatar1,"me",mes,this.time.toString());
        String ids = String.valueOf(id+=1);
        db_messenger.child(ids).setValue(chat);
        list_chatobj.add(chat);
        adapter.notifyItemChanged(list_chatobj.size()-1);
        input_text.getText().clear();
        rcv_chatbox.smoothScrollToPosition(adapter.getItemCount());
    }

    public void listener_firebase(){
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChatMessage chat =  snapshot.getValue(ChatMessage.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        this.db_messenger.addValueEventListener(listener);
    }

    public void getdata_firebase(){

        db_messenger.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                    list_chatobj.add(chat);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
