package com.example.monmisticuib;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.monmisticuib.controller.*;
import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.model.Zone;
import com.example.monmisticuib.view.CriaturesUI;
import com.example.monmisticuib.view.InventariUI;
import com.example.monmisticuib.view.MapUI;
import com.example.monmisticuib.view.PedraPaperTisoresView;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnMapa, btnCriatures, btnInventari;
    private MapUI mapUI;
    private CriaturesUI criaturesUI;
    private InventariUI inventariUI;
    private MapController mapController;
    private CriaturesController criaturesController;
    private InventariController inventariController;
    private ScaleGestureDetector scaleDetector;
    private ZoneManager zoneManager;
    private CreatureGenerator creatureGenerator;

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
        // Creamos UI
        mapUI = new MapUI(this);
        criaturesUI = new CriaturesUI(this);
        inventariUI = new InventariUI(this);

        // Detectores y managers
        scaleDetector = new ScaleGestureDetector(this, new ScaleListener());
        zoneManager = new ZoneManager(this);
        creatureGenerator = new CreatureGenerator(zoneManager.getAllZones().values());

        // Controllers
        mapController = new MapController(mapUI, this, zoneManager, creatureGenerator);
        mapController.setupSurfaceCallback(scaleDetector);
        criaturesController = new CriaturesController(criaturesUI, creatureGenerator, zoneManager);
        inventariController = new InventariController(inventariUI, this, creatureGenerator.getCapturades());

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
            if (inventariUI.isVisible()) {
                inventariUI.show(false);
            } else {
                hideAllUIs();
                inventariController.toggleInventariUI();
            }
        });

        mapUI.btnZoomIn.setOnClickListener(v -> mapController.zoomIn());
        mapUI.btnZoomOut.setOnClickListener(v -> mapController.zoomOut());
        mapUI.btnZoomMax.setOnClickListener(v -> mapController.zoomMax());
        mapUI.btnZoomMin.setOnClickListener(v -> mapController.zoomMin());

        mapUI.etCercaZona.setOnEditorActionListener((v, actionId, event) -> {
            buscarZona(v.getText().toString());
            return true;
        });

    }

    private void hideAllUIs() {
        mapUI.show(false);
        criaturesUI.show(false);
        inventariUI.show(false);
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            mapController.adjustZoom(scaleFactor);
            return true;
        }
    }

    private void buscarZona(String input) {
        String query = input.trim().toLowerCase();
        Zone zona = zoneManager.getZoneByPopularName(query);

        if (zona != null) {
            int centerX = (zona.getX1() + zona.getX2()) / 2;
            int centerY = (zona.getY1() + zona.getY2()) / 2;
            mapController.zoomMax();
            mapController.setCenter(centerX, centerY);
            mapController.drawMapImage();
        } else {
            Toast.makeText(this, "Zona no trobada", Toast.LENGTH_SHORT).show();
        }

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public MapController getMapController() {
        return mapController;
    }

}
