package com.example.btl_app_dating;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private UserAdapter userAdapter;

    private List<ChatMessage> list_chatobj= new ArrayList<>();

    private DatabaseReference db_conv = FirebaseDatabase.getInstance().getReference("conversations");

    private long id =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist);

        rcvUser= findViewById(R.id.rcv_user);
        userAdapter= new UserAdapter(list_chatobj)
        rcvUser.setAdapter(userAdapter);
        if(list_chatobj.size()<=0)
            getdata_firebase();

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    private void getdata_firebase(){
        autoid();
        db_conv.child("conv_0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_chatobj.clear();
                if (snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                    list_chatobj.add(chat);
                }
                userAdapter.notifyDataSetChanged();
                rcvUser.smoothScrollToPosition(userAdapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int autoid(){
        db_conv.child("conv_0").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()!=0){
                    id = snapshot.getChildrenCount();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return (int) id;
    }


}