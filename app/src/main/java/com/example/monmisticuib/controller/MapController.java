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
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.monmisticuib.R;
import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.model.Zone;
import com.example.monmisticuib.view.MapUI;

public class MapController {
    private final MapUI mapUI;
    private final Context context;
    private Bitmap mapaBitmap;
    private final ZoneManager zoneManager;
    private final CreatureGenerator creatureGenerator;


    private float cx, cy, zoomFactor, zoomMin;
    private final float zoomStepFactor = 1.25f;
    private final float zoomMax = 5.0f;

    public MapController(MapUI mapUI, Context context, ZoneManager zoneManager, CreatureGenerator creatureGenerator) {
        this.mapUI = mapUI;
        this.context = context;
        this.zoneManager = zoneManager;
        this.creatureGenerator = creatureGenerator;
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

                    // Centro y zoom mínimo
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
                updatePunts();
            });
        }).start();
    }

    private void drawToCanvas(SurfaceView surfaceView) {
        int surfaceWidth = surfaceView.getWidth();
        int surfaceHeight = surfaceView.getHeight();
        int bitmapWidth = mapaBitmap.getWidth();
        int bitmapHeight = mapaBitmap.getHeight();

        int visibleWidth = (int) (surfaceWidth / zoomFactor);
        int visibleHeight = (int) (surfaceHeight / zoomFactor);

        float halfVisibleWidth = visibleWidth / 2f;
        float halfVisibleHeight = visibleHeight / 2f;

        cx = Math.max(halfVisibleWidth, Math.min(cx, bitmapWidth - halfVisibleWidth));
        cy = Math.max(halfVisibleHeight, Math.min(cy, bitmapHeight - halfVisibleHeight));

        int left = (int) (cx - halfVisibleWidth);
        int top = (int) (cy - halfVisibleHeight);
        int right = left + visibleWidth;
        int bottom = top + visibleHeight;

        Rect src = new Rect(left, top, right, bottom);
        Rect dst = new Rect(0, 0, surfaceWidth, surfaceHeight);

        Canvas canvas = surfaceView.getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(mapaBitmap, src, dst, new Paint());

            // Círculo en el centro del mapa (jugador)
            Paint playerPaint = new Paint();
            playerPaint.setColor(Color.YELLOW);
            playerPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(surfaceWidth / 2f, surfaceHeight / 2f, 10, playerPaint);

            surfaceView.getHolder().unlockCanvasAndPost(canvas);
        }

        updateZonaActual();
    }


    private void updateZonaActual() {
        Zone zona = zoneManager.getZonaActual(cx, cy);
        String nom = (zona != null) ? zona.getNomOficial() : "Fora de zona";

        new Handler(Looper.getMainLooper()).post(() ->
                mapUI.tvZonaNom.setText(nom)
        );
    }

    public void updatePunts() {
        int total = 0;
        for (Creature c : creatureGenerator.getCapturades()) {
            String genere = c.getGenre();
            int punts = creatureGenerator.getAtributsPerGenere().get(genere).punts;
            total += punts;
        }

        final String txt = context.getString(R.string.punts_format, total);

        new Handler(Looper.getMainLooper()).post(() ->
                mapUI.tvPunts.setText(txt)
        );
    }


    public void setupSurfaceCallback(ScaleGestureDetector scaleDetector) {
        mapUI.surfaceMapa.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                drawMapImage();
                mapUI.surfaceMapa.setOnTouchListener(new MapTouchListener(MapController.this, scaleDetector));
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
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

    public void adjustZoom(float scaleFactor) {
        float nextZoom = zoomFactor * scaleFactor;
        if (nextZoom < zoomMin) nextZoom = zoomMin;
        if (nextZoom > zoomMax) nextZoom = zoomMax;

        zoomFactor = nextZoom;
        updateZoomText();
        quickDraw();
    }

    public void quickDraw() {
        if (mapUI.surfaceMapa.getHolder().getSurface().isValid()) {
            drawToCanvas(mapUI.surfaceMapa);
        }
    }

    public float getZoomFactor() {
        return zoomFactor;
    }

    public void offsetCenter(float dx, float dy) {
        cx += dx;
        cy += dy;
    }

    private void updateZoomText() {
        String zoomText = context.getString(R.string.zoom_format, zoomFactor);
        mapUI.tvZoomPercent.setText(zoomText);
    }

    public void setCenter(float x, float y) {
        this.cx = x;
        this.cy = y;
    }

}
