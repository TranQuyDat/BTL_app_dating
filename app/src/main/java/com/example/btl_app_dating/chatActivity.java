package com.example.btl_app_dating;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class chatActivity extends AppCompatActivity {
    private RecyclerView rcv_mes;
    private Button sendButton;
    private ChatAdapter adapter;
    private List<ChatMessage> lismes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);

        rcv_mes = findViewById(R.id.RecycleView_mes);
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv_mes.setLayoutManager(lnm);
        sendButton = findViewById(R.id.btn_send);
        adapter = new ChatAdapter(lismes);
        rcv_mes.setAdapter(adapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input_mes = findViewById(R.id.txt_mes_input);
                String mes = input_mes.getText().toString();

                if(!mes.isEmpty()){
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    ChatMessage chat = new ChatMessage("me",mes,time.toString());

                    lismes.add(chat);
                    adapter.notifyItemChanged(lismes.size()-1);

                    input_mes.getText().clear();
                    rcv_mes.smoothScrollToPosition(adapter.getItemCount());
                }
                else{

                }
            }
        });
    }

}
