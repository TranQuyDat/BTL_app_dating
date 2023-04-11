package com.example.btl_app_dating;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class viewpageAdapter extends RecyclerView.Adapter<viewpageAdapter.viewholder> {

    private List<Viewpage> view_list ;
    int avt = R.drawable.avatar1;
    private int pos_topcard;
    private Context context;
    private String uid;
    public viewpageAdapter(List<Viewpage> view_list,int pos_topcard,Context context,String uid) {

        this.view_list = view_list;
        this.pos_topcard =pos_topcard;
        this.context=context;
        this.uid=uid;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewcard,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
       Viewpage viewpage = view_list.get(position);

        if(viewpage == null) return;
        Picasso picasso = Picasso.get();
        if (viewpage.getimg_view().isEmpty()) viewpage.setImg_view("https://firebasestorage.googleapis.com/v0/b/btlappdating.appspot.com/o/t%E1%BA%A3i%20xu%E1%BB%91ng.png?alt=media&token=f803d01c-15c0-4253-8f71-12ce24239d53");
        picasso.load(viewpage.getimg_view()).error(R.drawable.avatar1).fit().into(holder.img_view);
        holder.txt_username.setText(viewpage.getname());
        if(viewpage.getimg_uri().isEmpty()) viewpage.setimg_uri("https://firebasestorage.googleapis.com/v0/b/btlappdating.appspot.com/o/avatar1.webp?alt=media&token=c7c52246-8602-4a67-89d6-00cab564519f");
        picasso.load(Uri.parse(viewpage.getimg_uri())).error(R.drawable.avatar1).fit().into(holder.avt_user);

        holder.cardView.setTag(viewpage.getidu());

        holder.avt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useridtopcard = view_list.get(pos_topcard).getidu();
                Intent intent = new Intent(context,Profile_fr_infoActivity.class);
                intent.putExtra("key_userIdtopcard",useridtopcard);
                intent.putExtra("key_userId",uid);
                context.startActivity(intent);
                ((Activity)context).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                ((Activity)context).finish();

            }
        });
    }



    @Override
    public int getItemCount() {
        if(view_list !=null){
            return view_list.size();
        }
        return 0;
    }

    class viewholder extends RecyclerView.ViewHolder{
        private ImageView img_view;
        private ImageView avt_user;
        private TextView txt_username;

        private CardView cardView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            img_view = itemView.findViewById(R.id.img_view);
            avt_user = itemView.findViewById(R.id.avt_user);
            txt_username = itemView.findViewById(R.id.txt_username);
        }
    }
}
