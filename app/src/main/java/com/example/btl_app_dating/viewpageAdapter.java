package com.example.btl_app_dating;

import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class viewpageAdapter extends RecyclerView.Adapter<viewpageAdapter.viewholder> {

    private List<Viewpage> view_list ;
    int avt = R.drawable.avatar1;
    public viewpageAdapter(List<Viewpage> view_list) {
        this.view_list = view_list;
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
//        Log.d("uribirth:",  String.valueOf(viewpage.getbirth()));
//        Log.d("urigender:",  String.valueOf(viewpage.getgender()));
//        Log.d("urirel:",  String.valueOf(viewpage.getrelationship()));
//        Log.d("uriword:",  String.valueOf(viewpage.getword()));
//        Log.d("uriimg:",  String.valueOf(viewpage.getimg_view()));
        if(viewpage == null) return;
        Picasso picasso = Picasso.get();
        picasso.load(viewpage.getimg_view()).error(R.drawable.avatar1).fit().into(holder.img_view);
        holder.txt_username.setText(viewpage.getname());
        holder.avt_user.setImageResource(viewpage.getresourceID());
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
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_view = itemView.findViewById(R.id.img_view);
            avt_user = itemView.findViewById(R.id.avt_user);
            txt_username = itemView.findViewById(R.id.txt_username);
        }
    }
}
