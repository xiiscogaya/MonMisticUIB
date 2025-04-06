package com.example.monmisticuib;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monmisticuib.controller.*;
import com.example.monmisticuib.view.CriaturesUI;
import com.example.monmisticuib.view.MapUI;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnMapa, btnCriatures, btnInventari;
    private MapUI mapUI;
    private CriaturesUI criaturesUI;
    private MapController mapController;
    private CriaturesController criaturesController;

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
        criaturesUI = new CriaturesUI(this);
        mapController = new MapController(mapUI, this);
        mapController.setupSurfaceCallback();
        criaturesController = new CriaturesController(criaturesUI);
    }

    private void setupListeners() {
        btnMapa.setOnClickListener(v -> {
            if (mapUI.isVisible()) {
                mapUI.show(false);
            } else {
                hideAllUIs();
                mapController.toggleMapUI();
            }
        });

        btnCriatures.setOnClickListener(v -> {
            if (criaturesUI.isVisible()) {
                criaturesUI.show(false);
            } else {
                hideAllUIs();
                criaturesController.toggleCriaturesUI();
            }
        });

        btnInventari.setOnClickListener(v -> {
            // Aquí haces lo mismo cuando crees InventariUI
        });

        mapUI.btnZoomIn.setOnClickListener(v -> mapController.zoomIn());
        mapUI.btnZoomOut.setOnClickListener(v -> mapController.zoomOut());
        mapUI.btnZoomMax.setOnClickListener(v -> mapController.zoomMax());
        mapUI.btnZoomMin.setOnClickListener(v -> mapController.zoomMin());

    }

    private void hideAllUIs() {
        mapUI.show(false);
        criaturesUI.show(false);
    }
}
