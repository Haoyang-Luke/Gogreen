package com.example.gogreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gogreen.Data.ModelUser;
import com.example.gogreen.Picture.CircularImage;
import com.example.gogreen.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 *It ’s about the leaderboard ’s adapter, adapting the data
 */
public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {

    Context context;
    final List<ModelUser> userList;

    public AdapterUsers(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout(row_user.xml)
        View view= LayoutInflater.from(context).inflate(R.layout.activity_user_rank,parent,false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //get data
        holder.setData(userList.get(position));

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder{


       private TextView mNameTv,mPoint,mPlace;
       private CircularImage mavatar;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mPoint=itemView.findViewById(R.id.pointsTv);
            mPlace=itemView.findViewById(R.id.placeTv);
            mavatar=itemView.findViewById(R.id.avatarIv);

        }
        public void setData(ModelUser modelUser){

            mNameTv.setText(modelUser.getName());
            mPlace.setText(modelUser.getPlace());
            mPoint.setText(modelUser.getPoints());
            try{
                        Picasso.get().load(modelUser.getImage()).placeholder(R.drawable.ic_face).into(mavatar);

            }catch (Exception e){

            }
        }
    }
}
