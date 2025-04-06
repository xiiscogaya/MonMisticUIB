package com.example.monmisticuib.view;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.monmisticuib.R;

import java.util.HashSet;
import java.util.Set;

public class CriaturesUI {
    private final Set<View> views = new HashSet<>();
    public TextView textInfo;
    public RadioGroup radioGroup;
    public RadioButton rbCriatures, rbZones, rbCapturades, rbEscapades;

    public CriaturesUI(Activity activity) {
        textInfo = activity.findViewById(R.id.text_info);
        radioGroup = activity.findViewById(R.id.radioGroupInfo);
        rbCriatures = activity.findViewById(R.id.rbCriatures);
        rbZones = activity.findViewById(R.id.rbZones);
        rbCapturades = activity.findViewById(R.id.rbCapturades);
        rbEscapades = activity.findViewById(R.id.rbEscapades);

        views.add(textInfo);
        views.add(radioGroup);

        show(false); // ocultar por defecto
    }

    public void show(boolean visible) {
        int state = visible ? View.VISIBLE : View.GONE;
        for (View v : views) {
            v.setVisibility(state);
        }

        if (visible) {
            textInfo.bringToFront();
            radioGroup.bringToFront();
        }
    }

    public boolean isVisible() {
        return textInfo.getVisibility() == View.VISIBLE;
    }
}
