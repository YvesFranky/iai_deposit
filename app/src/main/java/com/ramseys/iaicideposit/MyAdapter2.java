package com.ramseys.iaicideposit;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramseys.iaicideposit.Admin.GestionnaireItem;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<GestionnaireItem> gestionnaireItems;
    private ItemClickListener itemClickListener;
    public MyAdapter2(Context context, List<GestionnaireItem> gestionnaireItems, ItemClickListener itemClickListener) {
        this.context = context;
        this.gestionnaireItems = gestionnaireItems;
        this.itemClickListener = itemClickListener;
    }


    public void setFilteredList(List<GestionnaireItem> filteredList){
        this.gestionnaireItems = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_model, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(gestionnaireItems.get(position).getName());
        holder.filiere.setText(gestionnaireItems.get(position).getMatricule());
        holder.status.setText(gestionnaireItems.get(position).getPost());
        holder.status.setTextColor(Color.GREEN);
        holder.imageView.setImageResource(gestionnaireItems.get(position).getImage());

        holder.itemView.setOnClickListener( view ->{
            itemClickListener.onItemClick(gestionnaireItems.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return gestionnaireItems.size();
    }
    public interface ItemClickListener{
        void onItemClick(GestionnaireItem candidatItem);
    }
}
