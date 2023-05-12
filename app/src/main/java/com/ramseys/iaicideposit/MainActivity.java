
package com.ramseys.iaicideposit;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ramseys.iaicideposit.Admin.FragmentHome;
import com.ramseys.iaicideposit.Admin.FragmentList;
import com.ramseys.iaicideposit.Admin.FragmentProfil;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.bottomAppbar);
        navbar.setOnNavigationItemSelectedListener(navListner);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner = item -> {
        Fragment selectedFrag = null;
        switch (item.getItemId()){
            case R.id.home:
                selectedFrag = new FragmentHome();
                break;
            case R.id.search:
                selectedFrag = new FragmentList();
                break;
            case R.id.profil:
                selectedFrag = new FragmentProfil();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();
        return true;
    };
}