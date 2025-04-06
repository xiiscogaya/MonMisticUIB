package com.example.monmisticuib.controller;

import com.example.monmisticuib.view.CriaturesUI;

public class CriaturesController {
    private final CriaturesUI criaturesUI;

    public CriaturesController(CriaturesUI criaturesUI) { this.criaturesUI = criaturesUI; }

    public void toggleCriaturesUI() {
        boolean show = !criaturesUI.isVisible();
        criaturesUI.show(show);
    }
}
