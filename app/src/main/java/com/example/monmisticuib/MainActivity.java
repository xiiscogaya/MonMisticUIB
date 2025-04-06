package com.example.monmisticuib;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monmisticuib.controller.*;
import com.example.monmisticuib.view.MapUI;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnMapa, btnCriatures, btnInventari;
    private MapUI mapUI;
    private MapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        initButtons();
        initMVC();
        setupListeners();
    }

    private void initButtons() {
        btnMapa = findViewById(R.id.boton_mapa);
        btnCriatures = findViewById(R.id.boton_criatura);
        btnInventari = findViewById(R.id.boton_inventario);
    }

    private void initMVC() {
        mapUI = new MapUI(this);
        mapController = new MapController(mapUI);
    }

    private void setupListeners() {
        btnMapa.setOnClickListener(v -> mapController.toggleMapUI());

        btnCriatures.setOnClickListener(v -> {
            // lógica futura
        });

        btnInventari.setOnClickListener(v -> {
            // lógica futura
        });
    }
}
