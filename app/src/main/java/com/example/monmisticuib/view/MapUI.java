package com.example.monmisticuib.view;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.SurfaceView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.monmisticuib.R;

import java.util.HashMap;
import java.util.Map;

public class MapUI {

    public ImageButton btnZoomIn, btnZoomOut, btnZoomMax, btnZoomMin;
    public TextView tvZoomPercent, tvZonaNom, tvPunts;
    public EditText etCercaZona;
    public MapSurfaceView surfaceMapa;
    public ProgressBar progressLoading;

    private final Map<String, View> viewMap = new HashMap<>();

    public MapUI(Activity activity) {
        progressLoading = activity.findViewById(R.id.progressLoading);
        btnZoomIn = activity.findViewById(R.id.zoom_in);
        btnZoomOut = activity.findViewById(R.id.zoom_out);
        btnZoomMax = activity.findViewById(R.id.zoom_out_max);
        btnZoomMin = activity.findViewById(R.id.zoom_in_min);

        tvZoomPercent = activity.findViewById(R.id.text_zoom);
        tvZonaNom = activity.findViewById(R.id.text_zona);
        tvPunts = activity.findViewById(R.id.text_puntos);

        etCercaZona = activity.findViewById(R.id.text_buscar_zona);
        surfaceMapa = activity.findViewById(R.id.surfaceMapa);

        // Añadir todas las vistas al set para controlarlas fácilmente
        viewMap.put("progressLoading", progressLoading);
        viewMap.put("btnZoomIn", btnZoomIn);
        viewMap.put("btnZoomOut", btnZoomOut);
        viewMap.put("btnZoomMax", btnZoomMax);
        viewMap.put("btnZoomMin", btnZoomMin);
        viewMap.put("tvZoomPercent", tvZoomPercent);
        viewMap.put("tvZonaNom", tvZonaNom);
        viewMap.put("tvPunts", tvPunts);
        viewMap.put("etCercaZona", etCercaZona);
        viewMap.put("surfaceMapa", surfaceMapa);

        show(false); // Ocultar por defecto
    }

    public void show(boolean visible) {
        int state = visible ? View.VISIBLE : View.GONE;
        for (View v : viewMap.values()) {
            v.setVisibility(state);
        }
    }

    public boolean isVisible() {
        return surfaceMapa.getVisibility() == View.VISIBLE;
    }

    public SurfaceView getSurfaceMapa() {
        return surfaceMapa;
    }
}
