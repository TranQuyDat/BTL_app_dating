package com.example.btl_app_dating;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class Profile_editActivity extends AppCompatActivity {

    private DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
    private StorageReference storage_avt  = FirebaseStorage.getInstance().getReference();
    private String uid ;

    private Uri imgavt;
    private Uri imgview;

    private boolean active =false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);
        uid = getIntent().getStringExtra("key_userId");


        setdataprf();

        Button btn_save = findViewById(R.id.btn_save_prf);
        Button btn_sl_img = findViewById(R.id.btn_select_image);
        ImageButton btn_shareimg = findViewById(R.id.btn_share_img);
//btn_shate-img
        btn_shareimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        });

//btn_select_avt
        btn_sl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });
//btn_save
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                while (true) {
                    update_profile();
                    if (active) return;
                }
            }
        });

    }






    //*********************************************************************************************
//chay ham setdataprf xong moi chay cac ham con lai
    private class Loaddata extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }

        @Override
        protected void onPreExecute() {

            setdataprf();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // The user has selected an image from the gallery
            Uri selectedImageUri = data.getData();
            CircularImageView avt = findViewById(R.id.avt_prf_edit);
            imgavt = selectedImageUri;

            avt.setImageURI(imgavt);
            // Do something with the selected image

        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            // The user has selected an image from the gallery
            Uri selectedImageUri = data.getData();
            Uri cur_imgview=imgview;
            imgview = selectedImageUri;
            Log.d("debug!!!!!!:",String.valueOf(cur_imgview==imgview));
            // Do something with the selected image
            active =true;
        }


    }

    private void uploadImgview(Uri url){
        storage_avt.child("view/"+uid).putFile(url);
    }

    private void imgView_storagetorealine(){
        storage_avt.child("view/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                db_user.child(uid).child("img_view").setValue(url);
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

        uploadimg(imgavt);
        uploadImgview(imgview);

        Viewpage user = new Viewpage(Uri.EMPTY,
                username.getText().toString(),
                Integer.valueOf(age.getText().toString()),
                birth.getText().toString(),
                reship.getText().toString(),
                work.getText().toString(),
                gender.getText().toString(),
                "");

        db_user.child(uid).setValue(user);
        img_storagetorealine();
        imgView_storagetorealine();
    }



    private void img_storagetorealine(){
        storage_avt.child("avt/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                db_user.child(uid).child("img_uri").setValue(url);
            }
        });
    }

private void uploadimg(Uri url){
    storage_avt.child("avt/"+uid).putFile(url);
}


    //laydata tu reaLine ve
    private void setdataprf(){
        db_user.child(uid).addValueEventListener(new ValueEventListener() {
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

                Viewpage user = snapshot.getValue(Viewpage.class);
                avt.setTag(user.getimg_uri());

                imgavt = Uri.parse(user.getimg_uri());
                imgview = Uri.parse(user.getimg_view());
                        Picasso picasso = Picasso.get();
                        picasso.load(Uri.parse(user.getimg_uri())).fit().into(avt);

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
        Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
        intent.putExtra("key_userId",getIntent().getStringExtra("key_userId"));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }
}

