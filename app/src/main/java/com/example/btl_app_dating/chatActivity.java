package com.example.btl_app_dating;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
public class chatActivity extends AppCompatActivity {
    private RecyclerView rcv_chatbox;
    private Button sendButton;

    private DatabaseReference db_messenger = FirebaseDatabase.getInstance().getReference("messdemo");
    private Timestamp time = new Timestamp(System.currentTimeMillis());
    private List<ChatMessage> list_chatobj = new ArrayList<>();
    private ChatAdapter adapter;
    private long id =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);
        rcv_chatbox = findViewById(R.id.RecycleView_mes);
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv_chatbox.setLayoutManager(lnm);
        sendButton = findViewById(R.id.btn_send);
        EditText inputtxt = findViewById(R.id.txt_mes_input);

        adapter = new ChatAdapter(list_chatobj);
        rcv_chatbox.setAdapter(adapter);
        if(list_chatobj.size()<=0)
            getdata_firebase();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_chat(inputtxt);
            }
        });

        inputtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkkeyboard();
            }
        });

    }
    private void checkkeyboard(){
        final View actvityrootview = findViewById(R.id.lable_mes);
        actvityrootview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r =new Rect();
                actvityrootview.getWindowVisibleDisplayFrame(r);
                int heightDiff = actvityrootview.getRootView().getHeight() -r.height();
                if (heightDiff> 0.25*actvityrootview.getRootView().getHeight()){
                    if(list_chatobj.size()>0){
                        rcv_chatbox.smoothScrollToPosition(adapter.getItemCount());
                        actvityrootview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    private void update_chat(EditText input_text){
        String mes = input_text.getText().toString();
        if (mes.isEmpty()) return;
        ChatMessage chat = new ChatMessage("senderId1",R.drawable.avatar1,"me",mes,time.toString());
        String id_mes = String.valueOf(id);
        db_messenger.child("conv_0").child("mes_"+id_mes).setValue(chat);
        list_chatobj.add(chat);
        adapter.notifyItemChanged(list_chatobj.size()-1);
        input_text.getText().clear();
        rcv_chatbox.smoothScrollToPosition(adapter.getItemCount());
    }

    private void getdata_firebase(){
        autoid();
        db_messenger.child("conv_0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_chatobj.clear();
                if (snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ChatMessage chat = dataSnapshot.getValue(ChatMessage.class);
                    list_chatobj.add(chat);
                }
                adapter.notifyDataSetChanged();
                rcv_chatbox.smoothScrollToPosition(adapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private int autoid(){
        db_messenger.child("conv_0").addValueEventListener(new ValueEventListener() {

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
