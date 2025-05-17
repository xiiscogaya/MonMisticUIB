package com.example.monmisticuib.controller;

import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.model.Zone;
import com.example.monmisticuib.view.CriaturesUI;

import java.util.Map;
import java.util.Set;

public class CriaturesController {

    private final CriaturesUI criaturesUI;
    private final CreatureGenerator creatureGenerator;
    private final ZoneManager zoneManager;

    public CriaturesController(CriaturesUI criaturesUI,
                               CreatureGenerator creatureGenerator,
                               ZoneManager zoneManager) {
        this.criaturesUI = criaturesUI;
        this.creatureGenerator = creatureGenerator;
        this.zoneManager = zoneManager;

        setupRadioButtons();
    }

    public void toggleCriaturesUI() {
        boolean show = !criaturesUI.isVisible();
        criaturesUI.show(show);

        if (show) {
            criaturesUI.textInfo.setMovementMethod(new ScrollingMovementMethod());
            criaturesUI.textInfo.scrollTo(0, 0);
            mostrarCriatures(); // por defecto
        }
    }

    private void setupRadioButtons() {
        criaturesUI.rbCriatures.setOnClickListener(v -> mostrarCriatures());
        criaturesUI.rbZones.setOnClickListener(v -> mostrarZones());
        criaturesUI.rbCapturades.setOnClickListener(v -> mostrarCapturades());
        criaturesUI.rbEscapades.setOnClickListener(v -> mostrarEscapades());
    }

    private void mostrarCriatures() {
        StringBuilder sb = new StringBuilder();
        sb.append("<strong><u>Criatures per zona i gènere:</u></strong><br><br>");

        var mapa = creatureGenerator.getCatalogPerZonaIGenere();
        for (String zona : mapa.keySet()) {
            sb.append("<strong>").append(zona).append("</strong><br>");
            Map<String, Set<Creature>> perGenere = mapa.get(zona);
            for (String genere : perGenere.keySet()) {
                sb.append("&nbsp;&nbsp;→ <font color='blue'>")
                        .append(genere).append("</font>: ")
                        .append(perGenere.get(genere).size()).append("<br>");
            }
            sb.append("<br>");
        }

        actualizarText(sb.toString());
    }

    private void mostrarZones() {
        StringBuilder sb = new StringBuilder();
        sb.append("<strong><u>ZONES i coordenades:</u></strong><br><br>");

        for (Zone z : zoneManager.getAllZones().values()) {
            sb.append("<strong>").append(z.getNomOficial()).append("</strong><br>")
                    .append("&nbsp;&nbsp;Rect: [")
                    .append(z.getX1()).append(",").append(z.getY1())
                    .append(" - ")
                    .append(z.getX2()).append(",").append(z.getY2()).append("]<br><br>");
        }

        actualizarText(sb.toString());
    }

    private void mostrarCapturades() {
        StringBuilder sb = new StringBuilder();
        sb.append("<strong><u>CRIATURES CAPTURADES:</u></strong><br><br>");

        for (Creature c : creatureGenerator.getCapturades()) {
            sb.append("<font color='green'>").append(c.getName()).append("</font><br>");
        }

        if (creatureGenerator.getCapturades().isEmpty()) {
            sb.append("No has capturat cap criatura.<br>");
        }

        actualizarText(sb.toString());
    }

    private void mostrarEscapades() {
        StringBuilder sb = new StringBuilder();
        sb.append("<strong><u>CRIATURES ESCAPADES:</u></strong><br><br>");

        for (Creature c : creatureGenerator.getEscapades()) {
            sb.append("<font color='red'>").append(c.getName()).append("</font><br>");
        }

        if (creatureGenerator.getEscapades().isEmpty()) {
            sb.append("No s'ha escapat cap criatura.<br>");
        }

        actualizarText(sb.toString());
    }

    private void actualizarText(String html) {
        TextView tv = criaturesUI.textInfo;
        tv.setText(Html.fromHtml(html));
        tv.scrollTo(0, 0);
    }
}
