package com.example.monmisticuib.controller;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MapTouchListener implements View.OnTouchListener {
    private final MapController mapController;
    private final ScaleGestureDetector scaleDetector;
    private boolean isZooming = false;
    private float lastTouchX = 0, lastTouchY = 0;

    public MapTouchListener(MapController mapController, ScaleGestureDetector scaleDetector) {
        this.mapController = mapController;
        this.scaleDetector = scaleDetector;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        scaleDetector.onTouchEvent(event);

        // Zoom activo si hay más de un dedo
        isZooming = (event.getPointerCount() > 1);

        if (isZooming) return true;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchX = event.getX();
                lastTouchY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float dx = (lastTouchX - event.getX()) / mapController.getZoomFactor();
                float dy = (lastTouchY - event.getY()) / mapController.getZoomFactor();

                mapController.offsetCenter(dx, dy);

                lastTouchX = event.getX();
                lastTouchY = event.getY();

                mapController.quickDraw();
                return true;

            case MotionEvent.ACTION_UP:
                v.performClick();
                return true;
        }
        return false;
    }
}
