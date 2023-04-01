package com.example.btl_app_dating;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class trangchuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        ImageButton btn_chat = findViewById(R.id.btn_chat);
        ImageButton btn_heart = findViewById(R.id.btn_heart);
        ImageButton btn_delete = findViewById(R.id.btn_delete);

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
                startActivity(intent);
            }
        });
    }
}
