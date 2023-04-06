package com.example.btl_app_dating;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ConvAdapter extends RecyclerView.Adapter<ConvAdapter.UserViewHolder> {

    private Context mContext;
    private List<conversation> mListConv;

    private String uid;



    public  ConvAdapter(List<conversation> mListConv ,Context mContext, String uid ){
        this.mListConv= mListConv;
        this.mContext = mContext;
        this.uid = uid;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conv, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        conversation conv= mListConv.get(position);
        if (mListConv == null){
            return;
        }


        if(uid.equalsIgnoreCase(conv.getuserid1())) {
            holder.imgUser.setImageResource(conv.getimg_user2());
            holder.tvName.setText(conv.getname_user2());
        }
        else  {
            holder.imgUser.setImageResource(conv.getimg_user1());
            holder.tvName.setText(conv.getname_user1());
        }
        holder.tvlasmes.setText(conv.getlast_message());
        holder.tvtime.setText(conv.getlast_message_timestamp());
        holder.item_conv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,chatActivity.class);
                intent.putExtra("key_userId",uid);
                intent.putExtra("Key_conv",conv.getKey_conv());
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                ((Activity) mContext).finish();
            }
        });
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public int getItemCount() {
        if (mListConv != null) {
            return mListConv.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgUser;
        private TextView tvName;

        private TextView tvlasmes;

        private  TextView tvtime;

        private CardView item_conv;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser= itemView.findViewById(R.id.img_user);
            tvName= itemView.findViewById(R.id.tv_name);
            tvlasmes = itemView.findViewById(R.id.tv_lastmes);
            tvtime = itemView.findViewById(R.id.tv_time);
            item_conv = itemView.findViewById(R.id.item_conv);
        }
    }
}

