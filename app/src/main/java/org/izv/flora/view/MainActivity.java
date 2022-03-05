package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.view.adapter.FloraAdapter;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd, fabImagen, fabMenuMain;
    private RecyclerView rvFloraMain;
    private MainActivityViewModel mavm;
    private Boolean isFABOpen = false;

    @Override
    protected void onRestart() {
        super.onRestart();
        mavm.getFlora();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        rvFloraMain = findViewById(R.id.rvFloraMain);
        fabAdd = findViewById(R.id.fabAdd);
        fabImagen = findViewById(R.id.fabImagen);
        fabMenuMain = (FloatingActionButton) findViewById(R.id.fabMenuMain);

        menuFlotante();

        fabAdd.setOnClickListener(v -> openAddActivity());
        fabImagen.setOnClickListener(v -> openAddImagenActivity());
        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mavm.getFloraLiveData().observe(this, floraPlural -> {
            //Recycle view
            createRVFloraMain(floraPlural);
        });
        mavm.getFlora();
    }

    private void createRVFloraMain(ArrayList<Flora> floraPlural) {
        rvFloraMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FloraAdapter floraAdapter = new FloraAdapter(getApplicationContext());
        rvFloraMain.setAdapter(floraAdapter);
        System.out.println(floraPlural.toString());
        floraAdapter.setFloraList(floraPlural);
    }

    private void openAddImagenActivity() {
        Intent intent = new Intent(this, AddImagenActivity.class);
        startActivity(intent);
    }

    private void openAddActivity() {
        Intent intent = new Intent(this, AddFloraActivity.class);
        startActivity(intent);
    }

    private void menuFlotante() {
        fabMenuMain.setOnClickListener(view -> {
            if(!isFABOpen){
                fabMenuMain.setImageResource(R.drawable.ic_baseline_menu_open_24);
                showFABMenu();
            }else{
                fabMenuMain.setImageResource(R.drawable.ic_baseline_menu_24);
                closeFABMenu();
            }
        });
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabAdd.animate().translationY(-110);
        fabImagen.animate().translationY(-200);
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabAdd.animate().translationY(0);
        fabImagen.animate().translationY(0);
    }
}