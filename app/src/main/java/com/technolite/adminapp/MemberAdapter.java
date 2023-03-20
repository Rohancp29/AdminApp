package com.technolite.adminapp;

import android.annotation.SuppressLint;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import java.util.List;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{

    public List<Member> memberList=new ArrayList<>();

    String path="";
    private static int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE=101;
    private Context context;
    RequestOptions option;
    public MemberAdapter(List<Member> memberList,Context context) {
        this.context=context;
        this.memberList = memberList;

    }
    @NonNull
    @Override
    public MemberAdapter.MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.customer_list,parent,false);

        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.MemberViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Member member = memberList.get(position);
        holder.nameTV.setText(member.getMember_name());
        holder.dateTV.setText((CharSequence) member.getJoin_date());
        holder.numberTV.setText(member.getMember_number());
        holder.addrsTV.setText(member.getMember_addrs());
        holder.remainderTV.setText(member.getReminder_status());
        //holder.expiry.setText(member.getExpiry_date());


    }





    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder{
        TextView nameTV,addrsTV,numberTV,dateTV,remainderTV,expiry;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV=itemView.findViewById(R.id.idTVCUName);
            addrsTV=itemView.findViewById(R.id.idTVCUAddress);
            numberTV=itemView.findViewById(R.id.idTVCUPhoneNumber);
            remainderTV=itemView.findViewById(R.id.idcalender);
            dateTV=itemView.findViewById(R.id.iddate);


        }
    }

}
