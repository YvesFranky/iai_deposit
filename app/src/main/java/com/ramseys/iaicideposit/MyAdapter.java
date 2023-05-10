package com.ramseys.iaicideposit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<CandidatItem> candidatItemList;
    private ItemClickListener itemClickListener;
    public MyAdapter(Context context, List<CandidatItem> candidatItemList, ItemClickListener itemClickListener) {
        this.context = context;
        this.candidatItemList = candidatItemList;
        this.itemClickListener = itemClickListener;
    }


    public void setFilteredList(List<CandidatItem> filteredList){
        this.candidatItemList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(candidatItemList.get(position).getNomCadidat());
        holder.filiere.setText(candidatItemList.get(position).getFiliere());
        if (candidatItemList.get(position).isStatus()){

            holder.status.setText("Cadidatute Acceptée");
            holder.status.setTextColor(Color.GREEN);
        }else holder.status.setText("Candidature Réfusée");
        holder.imageView.setImageResource(candidatItemList.get(position).getImage());

        holder.itemView.setOnClickListener( view ->{
            itemClickListener.onItemClick(candidatItemList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return candidatItemList.size();
    }
    public interface ItemClickListener{
        void onItemClick(CandidatItem candidatItem);
    }
}
