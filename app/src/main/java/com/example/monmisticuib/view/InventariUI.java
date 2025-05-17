package com.example.monmisticuib.view;

import android.app.Activity;
import android.view.View;
import android.view.SurfaceView;

import com.example.monmisticuib.R;

import java.util.HashMap;
import java.util.Map;
public class InventariUI {
    private final Map<String, View> viewMap = new HashMap<>();
    public SurfaceView surfaceInventari;

    public InventariUI(Activity activity) {
        surfaceInventari = activity.findViewById(R.id.surfaceInventari);

        viewMap.put("surfaceInventari", surfaceInventari);
        show(false);
    }

    public void show(Boolean visible) {
        int state = visible ? View.VISIBLE : View.GONE;
        for (View v : viewMap.values()) {
            v.setVisibility(state);
        }
    }

    public boolean isVisible() {
        return surfaceInventari.getVisibility() == View.VISIBLE;
    }
}
