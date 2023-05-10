package com.ramseys.iaicideposit.UserScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ramseys.iaicideposit.CandidatDetailInfo;
import com.ramseys.iaicideposit.CandidatItem;
import com.ramseys.iaicideposit.MyAdapter;
import com.ramseys.iaicideposit.R;

import java.util.ArrayList;
import java.util.List;

public class CandidatHome extends AppCompatActivity {
    private RecyclerView recyclerView;
     List<CandidatItem> candidatItemList;
    private SearchView searchView;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_home);

        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        candidatItemList = new ArrayList<>();

        candidatItemList.add(new CandidatItem(1, "Amadou", R.drawable.mainbkg, "Génie logiciel", true));
        candidatItemList.add(new CandidatItem(1, "Aanopelba diebalbe dilane", R.drawable.mainbkg, "Système et Réseau", true));
        candidatItemList.add(new CandidatItem(1, "Yves Franky", R.drawable.mainbkg, "Génie logiciel", false));
        candidatItemList.add(new CandidatItem(1, "Amadou", R.drawable.mainbkg, "Génie logiciel", true));
        candidatItemList.add(new CandidatItem(1, "Amadou", R.drawable.mainbkg, "Génie logiciel", false));
        candidatItemList.add(new CandidatItem(1, "Amadou", R.drawable.mainbkg, "Génie logiciel", true));
        candidatItemList.add(new CandidatItem(1, "Amadou", R.drawable.mainbkg, "Génie logiciel", false));

        myAdapter = new MyAdapter(getApplicationContext(), candidatItemList, new MyAdapter.ItemClickListener() {
            @Override
            public void onItemClick(CandidatItem candidatItem) {
                Intent intent = new Intent(CandidatHome.this, CandidatDetailInfo.class);
                intent.putExtra("candidatItem",candidatItem);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(myAdapter);



    }

    private void filterList(String newText) {
        List<CandidatItem> filteredList = new ArrayList<>();

        for (CandidatItem candidatItem : candidatItemList) {
            if (candidatItem.getNomCadidat().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(candidatItem);
            }
        }
        
        if (filteredList.isEmpty()){
            Toast.makeText(this, "Pas de correspondance", Toast.LENGTH_SHORT).show();
        }else {
            myAdapter.setFilteredList(filteredList);
        }
    }

}