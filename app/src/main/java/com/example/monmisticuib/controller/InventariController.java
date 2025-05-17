package com.example.monmisticuib.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.monmisticuib.R;
import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.view.InventariUI;

import java.util.Set;

public class InventariController {

    private final InventariUI inventariUI;
    private final Context context;
    private final Set<Creature> capturades;

    public InventariController(InventariUI ui, Context context, Set<Creature> capturades) {
        this.inventariUI = ui;
        this.context = context;
        this.capturades = capturades;
    }

    public void toggleInventariUI() {
        boolean show = !inventariUI.isVisible();
        inventariUI.show(show);

        if (show) {
            pintarInventari();
        }
    }

    private void pintarInventari() {
        SurfaceView surface = inventariUI.surfaceInventari;
        SurfaceHolder holder = surface.getHolder();

        if (!holder.getSurface().isValid()) return;

        Canvas canvas = holder.lockCanvas();
        if (canvas == null) return;

        canvas.drawColor(0xFFFFFFFF); // fondo blanco

        String[] generes = {"aiguard", "focguard", "tornadrac", "vapordrac"};
        int rows = generes.length;
        int cols = 8;

        int padding = 10;
        int width = surface.getWidth();
        int height = surface.getHeight();

        int cellW = (width - (cols + 1) * padding) / cols;
        int cellH = (height - (rows + 1) * padding) / rows;

        Bitmap checkBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.check);

        for (int row = 0; row < rows; row++) {
            String genere = generes[row];

            for (int col = 0; col < cols; col++) {
                int especie = col + 1;
                String imageName = genere + especie;

                int left = padding + col * (cellW + padding);
                int top = padding + row * (cellH + padding);
                int right = left + cellW;
                int bottom = top + cellH;

                int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

                if (resId != 0) {
                    Bitmap creatureBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
                    Rect dst = new Rect(left, top, right, bottom);
                    canvas.drawBitmap(creatureBitmap, null, dst, null);

                    boolean capturada = isCapturada(genere, especie);
                    if (capturada) {
                        canvas.drawBitmap(checkBitmap, null, dst, null);
                    }
                }
            }
        }

        holder.unlockCanvasAndPost(canvas);
    }

    private boolean isCapturada(String genere, int especie) {
        for (Creature c : capturades) {
            if (c.getGenre().equals(genere) && c.getSpecies() == especie) {
                return true;
            }
        }
        return false;
    }
}
