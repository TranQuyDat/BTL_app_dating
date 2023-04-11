package com.example.btl_app_dating;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
    FirebaseAuth auth;
    private String uid ;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        uid = getIntent().getStringExtra("key_userId");

        setdataprf();
        auth = FirebaseAuth.getInstance();
        TextView btnedit= findViewById(R.id.btn_edit_prf);
        TextView btn_logout =findViewById(R.id.btn_logout);
        TextView txt_email = findViewById(R.id.email_prf);
        user = auth.getCurrentUser();
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), loginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            txt_email.setText(user.getEmail());
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Profile_editActivity.class);
                intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        });


    }

    private void setdataprf(){
        db_user.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CircularImageView avt = findViewById(R.id.avt_prf);
                TextView name = findViewById(R.id.txt_name_prf);
                TextView username = findViewById(R.id.username_prf);
                TextView age = findViewById(R.id.age_prf);
                TextView birth = findViewById(R.id.birth_prf);
                TextView gender = findViewById(R.id.gender_prf);
                TextView reship = findViewById(R.id.reship_prf);
                TextView work = findViewById(R.id.work_prf);

                User user = snapshot.getValue(User.class);

                Picasso picasso = Picasso.get();
                Log.d("debug!!!!!!!!",user.getimg_uri());
                picasso.load(Uri.parse(user.getimg_uri())).error(R.drawable.avatar1).fit().into(avt);

                name.setText(user.getname());
                username.setText(user.getname());
                age.setText((String.valueOf(user.getage())));
                birth.setText(user.getbirth());
                gender.setText(user.getgender());
                reship.setText(user.getrelationship());
                work.setText(user.getword());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),trangchuActivity.class);
        intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }
}
