package com.example.monmisticuib.controller;

import android.content.Context;

import com.example.monmisticuib.R;
import com.example.monmisticuib.model.Zone;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class ZoneManager {

    // TreeMap ordena alfabéticamente por clave
    private final Map<String, Zone> zonesByPopularName = new TreeMap<>();
    private final Map<String, String> popularToOficial = new TreeMap<>();

    public ZoneManager(Context context) {
        loadZonesFromJSON(context);
    }

    private void loadZonesFromJSON(Context context) {
        try {
            // Leer archivo raw/zones.json como string
            InputStream is = context.getResources().openRawResource(R.raw.zones);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parsear JSON
            JSONObject root = new JSONObject(json);
            JSONArray zonesArray = root.getJSONArray("zones_coords");

            for (int i = 0; i < zonesArray.length(); i++) {
                JSONObject obj = zonesArray.getJSONObject(i);

                String popular = obj.getString("zona");
                String oficial = obj.getString("nom");
                // Escalas respecto al JSON original (6144x4214) y mapa real (16128x10752)
                final float ESCALA_X = 16128f / 6144f;
                final float ESCALA_Y = 10752f / 4214f;

                // Leer y escalar
                int x1 = (int) (obj.getInt("x1") * ESCALA_X);
                int y1 = (int) (obj.getInt("y1") * ESCALA_Y);
                int x2 = (int) (obj.getInt("x2") * ESCALA_X);
                int y2 = (int) (obj.getInt("y2") * ESCALA_Y);

                Zone z = new Zone(popular, oficial, x1, y1, x2, y2);
                zonesByPopularName.put(popular, z);
                popularToOficial.put(popular, oficial);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ACCESOS

    public Zone getZoneByPopularName(String name) {
        return zonesByPopularName.get(name);
    }

    public String getNomOficial(String nomPopular) {
        return popularToOficial.get(nomPopular);
    }

    public Map<String, Zone> getAllZones() {
        return zonesByPopularName;
    }

    public Zone getZonaActual(float cx, float cy) {
        for (Zone z : zonesByPopularName.values()) {
            if (cx >= z.getX1() && cx <= z.getX2() &&
                    cy >= z.getY1() && cy <= z.getY2()) {
                return z;
            }
        }
        return null;
    }

}
