package com.example.btl_app_dating;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.net.Uri;
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

    private DatabaseReference db_messenger = FirebaseDatabase.getInstance().getReference("mess");
    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
    private DatabaseReference db_conv = FirebaseDatabase.getInstance().getReference("conversations");

    public Timestamp time = new Timestamp(System.currentTimeMillis());
    private List<ChatMessage> list_chatobj = new ArrayList<>();
    private ChatAdapter adapter;
    private long id =0;

    private String keyconv="";


    private String avtid;



    private String name_Sender="";

    private String uid ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);
        uid = getIntent().getStringExtra("key_userId");
        keyconv = getIntent().getStringExtra("Key_conv") ;
        get_name_avt();
        rcv_chatbox = findViewById(R.id.RecycleView_mes);
        LinearLayoutManager lnm = new LinearLayoutManager(this);
        rcv_chatbox.setLayoutManager(lnm);
        sendButton = findViewById(R.id.btn_send);
        EditText inputtxt = findViewById(R.id.txt_mes_input);
        adapter = new ChatAdapter(list_chatobj,uid);
        rcv_chatbox.setAdapter(adapter);

            getdata_firebase(keyconv);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug!!!!!!!!!!!: ",keyconv);
                autoid();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
        intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
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
        autoid();
        String mes = input_text.getText().toString();
        if (mes.isEmpty()) return;
        ChatMessage chat = new ChatMessage(uid,avtid,name_Sender,mes,time.toString());
        db_messenger.child(keyconv).child("mes_"+padNumber((int) id,3)).setValue(chat);
        list_chatobj.add(chat);
        adapter.notifyItemChanged(list_chatobj.size()-1);
        input_text.getText().clear();
        rcv_chatbox.smoothScrollToPosition(adapter.getItemCount());
    }
    String padNumber(int number, int padding) {
        String format = "%0" + padding + "d";
        return String.format(format, number);
    }
    private void getdata_firebase(String conv){
        autoid();
        db_messenger.child(conv).addValueEventListener(new ValueEventListener() {
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
        db_messenger.child(keyconv).addValueEventListener(new ValueEventListener() {

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





    private void get_name_avt(){
        db_user.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               User user = snapshot.getValue(User.class);
               name_Sender = user.getname();
               avtid = user.getimg_uri();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
