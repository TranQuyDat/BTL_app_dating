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

import java.util.ArrayList;
import java.util.List;

public class trangchuActivity extends AppCompatActivity {

    private FirebaseStorage storage  = FirebaseStorage.getInstance("gs://btlappdating.appspot.com");
    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
    private List<Viewpage> list_viewpage = new ArrayList<>();
    private viewpageAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        CardStackView card_view = findViewById(R.id.card_viewpage_main);
        adapter = new viewpageAdapter(list_viewpage);

        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(this);
        cardStackLayoutManager.setCanScrollVertical(false);
        card_view.setLayoutManager(cardStackLayoutManager);

        get_data();
        card_view.setAdapter(adapter);

        ImageButton btn_chat = findViewById(R.id.btn_chat);
        ImageButton btn_heart = findViewById(R.id.btn_heart);
        ImageButton btn_delete = findViewById(R.id.btn_delete);

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ChatListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private  void get_data(){
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_viewpage.clear();
                if(snapshot.getChildrenCount()<=0) return;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Viewpage viewpage = dataSnapshot.getValue(Viewpage.class);
                    list_viewpage.add(viewpage);
                }

                adapter.notifyItemChanged(list_viewpage.size()-1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
