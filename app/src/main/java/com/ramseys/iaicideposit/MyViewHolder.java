package com.ramseys.iaicideposit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends  RecyclerView.ViewHolder{
    CircleImageView imageView;
    TextView name, filiere, status;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.name);
        filiere = itemView.findViewById(R.id.filiere);
        status = itemView.findViewById(R.id.status);


    }
}
