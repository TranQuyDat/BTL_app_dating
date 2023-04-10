package com.example.btl_app_dating;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

public class Profile_editActivity extends AppCompatActivity {

    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
    private String uid ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        uid = getIntent().getStringExtra("key_userId");


        setdataprf();

        Button btn_save = findViewById(R.id.btn_save_prf);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_profile();
            }
        });

    }

    private void update_profile(){
        CircularImageView avt = findViewById(R.id.avt_prf_edit);
        TextView name = findViewById(R.id.txt_name_prf_edit);
        EditText username = findViewById(R.id.username_prf_edit);
        EditText age = findViewById(R.id.age_prf_edit);
        EditText birth = findViewById(R.id.birth_prf_edit);
        EditText gender = findViewById(R.id.gender_prf_edit);
        EditText reship = findViewById(R.id.reship_prf_edit);
        EditText work = findViewById(R.id.work_prf_edit);
        avt.setTag(R.drawable.avatar1);
        int resourceId = (int) avt.getTag();

        Viewpage user = new Viewpage(resourceId,
                username.getText().toString(),
                Integer.valueOf(age.getText().toString()),
                birth.getText().toString(),
                reship.getText().toString(),
                work.getText().toString(),
                gender.getText().toString(),
                "");

        db_user.child(uid).setValue(user);

    }
    private void setdataprf(){
        db_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CircularImageView avt = findViewById(R.id.avt_prf_edit);
                TextView name = findViewById(R.id.txt_name_prf_edit);
                EditText username = findViewById(R.id.username_prf_edit);
                EditText age = findViewById(R.id.age_prf_edit);
                EditText birth = findViewById(R.id.birth_prf_edit);
                EditText gender = findViewById(R.id.gender_prf_edit);
                EditText reship = findViewById(R.id.reship_prf_edit);
                EditText work = findViewById(R.id.work_prf_edit);
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);

                    avt.setImageResource(user.getresourceID());
                    name.setText(user.getname());
                    username.setText(user.getname());
                    age.setText((String.valueOf(user.getage())));
                    birth.setText(user.getbirth());
                    gender.setText(user.getgender());
                    reship.setText(user.getrelationship());
                    work.setText(user.getword());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }
}

