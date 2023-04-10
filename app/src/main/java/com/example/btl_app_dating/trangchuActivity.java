package com.example.btl_app_dating;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private  List<conversation> conversationList = new ArrayList<>();
    private viewpageAdapter adapter;
    private int id =0;
    private String uid="";
    private String useridtopcard="";
    private boolean ischat = true ;

    private String new_conv="";
    private String lasttime="";

    private String lastmes="";

    private String nameuid="";

    private int avt_uid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        uid = getIntent().getStringExtra("key_userId");
        get_name_img_uid();

        CardStackView card_view = findViewById(R.id.card_viewpage_main);
        adapter = new viewpageAdapter(list_viewpage);
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollVertical(false);
        card_view.setLayoutManager(cardStackLayoutManager);
        card_view.setAdapter(adapter);
        loadconv();
        if (list_viewpage.size()<=0)
            get_data();

        ImageButton btn_chat = findViewById(R.id.btn_chat);
        ImageButton btn_heart = findViewById(R.id.btn_heart);
        ImageButton btn_prf = findViewById(R.id.btn_prf);

//btn_prf
        btn_prf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();

            }
        });

//btn_chat
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
                intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });
//btn_heart
        btn_heart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View view) {
                if(list_viewpage.isEmpty()) return;
                loadconv();
                useridtopcard = list_viewpage.get(cardStackLayoutManager.getTopPosition()).getidu();
                checkischat(uid,useridtopcard);
                new LoadConvTask().execute();

            }
        });
    }

    public void onImageButtonClick(View view) {
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        alphaAnimation.setDuration(1000);
        view.startAnimation(alphaAnimation);
    }

    private class LoadConvTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Thực hiện công việc trong hàm loadconv() ở đây
            loadconv();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Thực hiện các dòng lệnh tiếp theo ở đây
            checkischat(uid,useridtopcard);
            if (ischat) return;
            new_conv = "conv"+id;
            updatedataconv_new();
            Intent intent = new Intent(getApplicationContext(),chatActivity.class);
            intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
            intent.putExtra("Key_conv",new_conv);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        }
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

    private void get_name_img_uid(){
        db_user.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                nameuid = user.getname();
                avt_uid = user.getresourceID();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updatedataconv_new(){
        autoid();
        db_user.child(useridtopcard).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String  name_uidtopcard = user.getname();
                int avt_uidtopcard = user.getresourceID();
                conversation conversation = new conversation(lasttime,lastmes,name_uidtopcard,nameuid,useridtopcard,uid);
                conversation.setImg_user1(avt_uidtopcard);
                conversation.setImg_user2(avt_uid);
                db_conv.child("conv"+id).setValue(conversation);
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

    private void loadconv(){
        db_conv.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                conversationList.clear();
                if (snapshot.getChildrenCount()<=0){
                    ischat =false;
                    return;
                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    conversation con = dataSnapshot.getValue(conversation.class);
                    con.setKey_conv(dataSnapshot.getKey());
                    conversationList.add(con);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }

    private void checkischat(String uid1, String uid2) {
        boolean isChatFound = false;
        if (conversationList.isEmpty()) {
            loadconv();
        } else {
            for (conversation con : conversationList) {
                if ((uid1.equalsIgnoreCase(con.getuserid1()) && uid2.equalsIgnoreCase(con.getuserid2()))
                        || (uid1.equalsIgnoreCase(con.getuserid2()) && uid2.equalsIgnoreCase(con.getuserid1()))) {
                    ischat = true;

                    db_conv.child(con.getKey_conv()).setValue(con);
                    Intent intent = new Intent(getApplicationContext(), chatActivity.class);
                    intent.putExtra("Key_conv", con.getKey_conv());
                    intent.putExtra("key_userId", getIntent().getStringExtra("key_userId"));
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    finish();
                    return;
                }
            }
            ischat = isChatFound;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

}
