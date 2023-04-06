package com.example.btl_app_dating;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private DatabaseReference db_conv = FirebaseDatabase.getInstance().getReference("conversations");
    private DatabaseReference db_messenger = FirebaseDatabase.getInstance().getReference("mess");
    private RecyclerView rcv_conv;
    private ConvAdapter convAdapter;

    private List<conversation> list_conv = new ArrayList<>();
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist);

        uid = getIntent().getStringExtra("key_userId");

        rcv_conv= findViewById(R.id.rcv_conv);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        rcv_conv.setLayoutManager(linearLayoutManager);
        convAdapter = new ConvAdapter(list_conv,this,uid);

        rcv_conv.setAdapter(convAdapter);
        get_dataconv();

    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),trangchuActivity.class);
        intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    private void get_dataconv(){
        db_conv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_conv.clear();
                if (snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    conversation conv = dataSnapshot.getValue(conversation.class);
                    conv.setKey_conv(dataSnapshot.getKey());
                    update_data_last(dataSnapshot.getKey(),conv);


                    if(uid.equalsIgnoreCase(conv.getuserid1())||uid.equalsIgnoreCase(conv.getuserid2()))
                        list_conv.add(conv);
                }
                convAdapter.notifyDataSetChanged();
                rcv_conv.smoothScrollToPosition(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void update_data_last(String keyconv ,conversation conv){
        db_messenger.child(keyconv).limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                    conv.setLast_message(chat.getMestxt());
                    conv.setLast_message_timestamp(chat.getTime());
                }
                convAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}