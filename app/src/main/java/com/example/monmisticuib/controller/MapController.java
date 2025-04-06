package com.example.monmisticuib.controller;

import com.example.monmisticuib.view.MapUI;

public class MapController {
    private final MapUI mapUI;

    public MapController(MapUI mapUI) {
        this.mapUI = mapUI;
    }

    public void toggleMapUI() {
        boolean show = !mapUI.isVisible();
        mapUI.show(show);
    }
}
