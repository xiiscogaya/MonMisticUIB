package com.example.monmisticuib.view;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.monmisticuib.R;

import java.util.HashMap;
import java.util.Map;

public class CriaturesUI {
    private final Map<String, View> viewMap = new HashMap<>();
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

        viewMap.put("textInfo", textInfo);
        viewMap.put("radioGroup", radioGroup);
        viewMap.put("rbCriatures", rbCriatures);
        viewMap.put("rbZones", rbZones);
        viewMap.put("rbCapturades", rbCapturades);
        viewMap.put("rbEscapades", rbEscapades);

        show(false); // ocultar por defecto
    }

    public void show(boolean visible) {
        int state = visible ? View.VISIBLE : View.GONE;
        for (View v : viewMap.values()) {
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
