package com.example.btl_app_dating;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class trangchuActivity extends AppCompatActivity {

    private FirebaseStorage storage  = FirebaseStorage.getInstance("gs://btlappdating.appspot.com");
    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");

    private DatabaseReference db_conv = FirebaseDatabase.getInstance().getReference("conversations");
    private DatabaseReference db_messenger = FirebaseDatabase.getInstance().getReference("mess");
    private List<Viewpage> list_viewpage = new ArrayList<>();
    private viewpageAdapter adapter;
    private int id =0;
    private String uid="";
    private String useridtopcard="";
    private boolean ischat = true ;

    private String new_conv="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        uid = getIntent().getStringExtra("key_userId");



        CardStackView card_view = findViewById(R.id.card_viewpage_main);
        adapter = new viewpageAdapter(list_viewpage);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollVertical(false);
        card_view.setLayoutManager(cardStackLayoutManager);
        card_view.setAdapter(adapter);
        if (list_viewpage.size()<=0)
            get_data();


        ImageButton btn_chat = findViewById(R.id.btn_chat);
        ImageButton btn_heart = findViewById(R.id.btn_heart);
        ImageButton btn_prf = findViewById(R.id.btn_prf);

//btn_prf
        btn_prf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug: ",String.valueOf(uid));
            }
        });

//btn_chat
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });
//btn_heart
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useridtopcard = list_viewpage.get(cardStackLayoutManager.getTopPosition()).getidu();
                checkischat(uid,useridtopcard);
                if (ischat) return;
                    updatedataconv_new();
                    Intent intent = new Intent(getApplicationContext(),chatActivity.class);
                    intent.putExtra("Key_conv",new_conv);
                    startActivity(intent);
                    finish();
                    Log.d("debug: ",String.valueOf(ischat));



            }
        });
    }

    private  void get_data(){
        autoid();
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_viewpage.clear();
                if(snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Viewpage viewpage = dataSnapshot.getValue(Viewpage.class);
                    viewpage.setIdu(dataSnapshot.getKey());
                    if(!uid.equalsIgnoreCase(viewpage.getidu()))
                        list_viewpage.add(viewpage);
                }
                adapter.notifyItemChanged(list_viewpage.size()-1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error",error.getMessage());
            }
        });
    }

    private void updatedataconv_new(){
        autoid();
        db_user.child(useridtopcard).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String  name_receive = user.getname();
                int avt_receive = user.getresourceID();
                conversation conversation = new conversation("","",name_receive,useridtopcard,uid,avt_receive);
                db_conv.child("conv"+id).setValue(conversation);
                new_conv = "conv"+id;
            }@Override public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private int autoid(){
        db_conv.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount()!=0){
                    id = (int) snapshot.getChildrenCount();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {} });
        return (int) id;
    }

    private void checkischat(String uid1,String uid2){
            db_conv.addValueEventListener(new ValueEventListener() {
                boolean ic;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getChildrenCount()<=0) {ischat =false; return;}
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        conversation con = dataSnapshot.getValue(conversation.class);
                        if((uid1.equalsIgnoreCase(con.getuserid1())&&uid2.equalsIgnoreCase(con.getuserid2()))
                                ||(uid1.equalsIgnoreCase(con.getuserid2())&&uid2.equalsIgnoreCase(con.getuserid1()))){
                            ischat =true;
                            Intent intent = new Intent(getApplicationContext(),chatActivity.class);
                            intent.putExtra("Key_conv",dataSnapshot.getKey());
                            intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
                            startActivity(intent);
                            finish();
                            Log.d("debug: ",String.valueOf(ischat));
                            return;
                        }
                        else {
                            ischat = false;
                            Log.d("debug: ",String.valueOf(ischat));

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }


}
