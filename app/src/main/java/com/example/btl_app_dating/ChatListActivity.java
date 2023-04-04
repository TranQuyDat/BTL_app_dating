package com.example.btl_app_dating;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    private DatabaseReference db_conv = FirebaseDatabase.getInstance().getReference("conversations");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist);

        rcvUser= findViewById(R.id.rcv_user);
        userAdapter= new UserAdapter(this);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

    }


}