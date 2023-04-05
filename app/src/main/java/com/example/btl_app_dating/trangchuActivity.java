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
    private Intent intent ;
    private String uid ;
    private String useridtopcard;
    private boolean ischat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);

        intent = getIntent();
        uid = intent.getStringExtra("uid");

        CardStackView card_view = findViewById(R.id.card_viewpage_main);
        adapter = new viewpageAdapter(list_viewpage);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollVertical(false);
        card_view.setLayoutManager(cardStackLayoutManager);
        card_view.setAdapter(adapter);
        if (list_viewpage.size()<=0)
            get_data();
        //getiduser top card
        useridtopcard = getiduTopcard(cardStackLayoutManager);

        ImageButton btn_chat = findViewById(R.id.btn_chat);
        ImageButton btn_heart = findViewById(R.id.btn_heart);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_data();
                Log.d("",String.valueOf(list_viewpage.size()));
                checkischat(uid,useridtopcard);
                if (ischat == true) return;
                 db_user.child(useridtopcard).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         User user = snapshot.getValue(User.class);
                          String  name_receive = user.getname();
                          int avt_user = user.getresourceID();
                         updatedataconv_new(name_receive,useridtopcard,uid,avt_user);
                     }@Override public void onCancelled(@NonNull DatabaseError error) { }
                 });

            }
        });
    }

    private  void get_data(){
        autoid();
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("",String.valueOf(snapshot.getChildrenCount()));
                list_viewpage.clear();
                if(snapshot.getChildrenCount()<=0) return;
                Log.d("","trueaaaaaaaaaaaaaaaaaaaaaaaaa");
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Viewpage viewpage = dataSnapshot.getValue(Viewpage.class);
                    viewpage.setIdu(dataSnapshot.getKey());
//                    if(uid != useridtopcard)
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

    private void updatedataconv_new(String name_receiver,String userid1,String userid2,int avt_receive){
        conversation conversation = new conversation("","",name_receiver,userid1,userid2,avt_receive);
        db_conv.child("conv"+id).setValue(conversation);
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
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    conversation con = dataSnapshot.getValue(conversation.class);
                    if((uid1==con.getuserid1()&&uid2==con.getuserid2())||(uid1==con.getuserid2()&&uid2==con.getuserid1())){
                        ischat =true;
                    }
                    else ischat =false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
private String getiduTopcard(CardStackLayoutManager cardStackLayoutManager){
    if(cardStackLayoutManager.getTopPosition()==list_viewpage.size()) return "";
    return list_viewpage.get(cardStackLayoutManager.getTopPosition()).getidu();
}

}
