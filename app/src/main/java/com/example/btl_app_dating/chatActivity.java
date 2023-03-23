package com.example.btl_app_dating;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
public class chatActivity extends AppCompatActivity {
    private RecyclerView rcv_chatbox;
    private Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);

        rcv_chatbox = findViewById(R.id.RecycleView_mes);
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv_chatbox.setLayoutManager(lnm);
        sendButton = findViewById(R.id.btn_send);
        EditText inputtxt = findViewById(R.id.txt_mes_input);
        chatbox_dataFirebase chatbox_act= new chatbox_dataFirebase(inputtxt,rcv_chatbox);

            chatbox_act.getdata_firebase();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatbox_act.update_chat();
            }
        });
    }



}
