package com.example.monmisticuib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class MapSurfaceView extends SurfaceView {

    public MapSurfaceView(Context context) {
        super(context);
    }

    public MapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        // Esto es requerido para accesibilidad y evitar advertencias de Lint
        return super.performClick();
    }
}
