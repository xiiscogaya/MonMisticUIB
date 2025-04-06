package com.example.monmisticuib.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.monmisticuib.R;
import com.example.monmisticuib.view.MapUI;

public class MapController {
    private final MapUI mapUI;
    private final Context context;
    private Bitmap mapaBitmap;
    private final float zoomMax;

    private float cx, cy, zoomFactor, zoomMin;
    private final float zoomStepFactor = 1.25f;
    private float lastTouchX = 0, lastTouchY = 0;

    public MapController(MapUI mapUI, Context context) {
        this.mapUI = mapUI;
        this.context = context;

        zoomMax = 5.0f;
    }

    public void toggleMapUI() {
        boolean show = !mapUI.isVisible();
        mapUI.show(show);
        if (show && mapaBitmap != null) {
            drawMapImage();
        }
    }

    public void drawMapImage() {
        new Handler(Looper.getMainLooper()).post(() ->
                mapUI.progressLoading.setVisibility(View.VISIBLE)
        );

        new Thread(() -> {
            SurfaceView surfaceView = mapUI.surfaceMapa;

            if (surfaceView.getHolder().getSurface().isValid()) {
                int surfaceWidth = surfaceView.getWidth();
                int surfaceHeight = surfaceView.getHeight();

                if (mapaBitmap == null) {
                    mapaBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mapam);

                    int bitmapWidth = mapaBitmap.getWidth();
                    int bitmapHeight = mapaBitmap.getHeight();

                    // Calcular centro y zoom mínimo
                    cx = bitmapWidth / 2f;
                    cy = bitmapHeight / 2f;

                    float zoomX = (float) surfaceWidth / bitmapWidth;
                    float zoomY = (float) surfaceHeight / bitmapHeight;

                    zoomMin = Math.max(zoomX, zoomY);
                    zoomFactor = zoomMin;
                }

                drawToCanvas(surfaceView);
            }

            new Handler(Looper.getMainLooper()).post(() -> {
                mapUI.progressLoading.setVisibility(View.GONE);
                updateZoomText();
            });
        }).start();
    }

    private void quickDraw() {
        SurfaceView surfaceView = mapUI.surfaceMapa;

        if (surfaceView.getHolder().getSurface().isValid() && mapaBitmap != null) {
            drawToCanvas(surfaceView);
        }
    }

    private void drawToCanvas(SurfaceView surfaceView) {
        int surfaceWidth = surfaceView.getWidth();
        int surfaceHeight = surfaceView.getHeight();
        int bitmapWidth = mapaBitmap.getWidth();
        int bitmapHeight = mapaBitmap.getHeight();

        int visibleWidth = (int)(surfaceWidth / zoomFactor);
        int visibleHeight = (int)(surfaceHeight / zoomFactor);

        float halfVisibleWidth = visibleWidth / 2f;
        float halfVisibleHeight = visibleHeight / 2f;

        cx = Math.max(halfVisibleWidth, Math.min(cx, bitmapWidth - halfVisibleWidth));
        cy = Math.max(halfVisibleHeight, Math.min(cy, bitmapHeight - halfVisibleHeight));

        int left = (int)(cx - halfVisibleWidth);
        int top = (int)(cy - halfVisibleHeight);
        int right = left + visibleWidth;
        int bottom = top + visibleHeight;

        Rect src = new Rect(left, top, right, bottom);
        Rect dst = new Rect(0, 0, surfaceWidth, surfaceHeight);

        Canvas canvas = surfaceView.getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(mapaBitmap, src, dst, new Paint());
            surfaceView.getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void setupSurfaceCallback() {
        mapUI.surfaceMapa.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                drawMapImage();
                setupTouchControls();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });
    }

    public void setupTouchControls() {
        mapUI.surfaceMapa.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastTouchX = event.getX();
                    lastTouchY = event.getY();
                    return true;

                case MotionEvent.ACTION_MOVE:
                    float dx = (lastTouchX - event.getX()) / zoomFactor;
                    float dy = (lastTouchY - event.getY()) / zoomFactor;

                    cx += dx;
                    cy += dy;

                    lastTouchX = event.getX();
                    lastTouchY = event.getY();

                    quickDraw();
                    return true;

                case MotionEvent.ACTION_UP:
                    v.performClick();
                    return true;
            }
            return false;
        });
    }

    public void zoomIn() {
        float nextZoom = zoomFactor * zoomStepFactor;
        if (nextZoom <= zoomMax) {
            zoomFactor = nextZoom;
            updateZoomText();
            drawMapImage();
        }
    }

    public void zoomOut() {
        float nextZoom = zoomFactor / zoomStepFactor;
        if (nextZoom >= zoomMin) {
            zoomFactor = nextZoom;
            updateZoomText();
            drawMapImage();
        }
    }

    public void zoomMax() {
        zoomFactor = zoomMax;
        updateZoomText();
        drawMapImage();
    }

    public void zoomMin() {
        zoomFactor = zoomMin;
        if (mapaBitmap != null) {
            cx = mapaBitmap.getWidth() / 2f;
            cy = mapaBitmap.getHeight() / 2f;
        }
        updateZoomText();
        drawMapImage();
    }

    private void updateZoomText() {
        String zoomText = context.getString(R.string.zoom_format, zoomFactor);
        mapUI.tvZoomPercent.setText(zoomText);
    }
}
