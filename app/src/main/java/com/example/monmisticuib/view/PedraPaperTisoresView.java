package com.example.monmisticuib.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.monmisticuib.R;

public class PedraPaperTisoresView {
    private final Dialog dialog;
    private final Button btnPedra, btnPaper, btnTisora, btnResposta;
    private final TextView tvMsg;
    private final ImageView imgCriatura;

    public PedraPaperTisoresView(Context context, int drawableId) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_rps);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(1000, 1000);

        imgCriatura = dialog.findViewById(R.id.img_criatura);
        imgCriatura.setForeground(ContextCompat.getDrawable(context, drawableId));

        tvMsg = dialog.findViewById(R.id.tv_dialog_message);
        btnPedra = dialog.findViewById(R.id.btn_pedra);
        btnPaper = dialog.findViewById(R.id.btn_paper);
        btnTisora = dialog.findViewById(R.id.btn_tisora);
        btnResposta = dialog.findViewById(R.id.btn_resposta_criatura);
    }

    public void show() { dialog.show(); }
    public void dismiss() { dialog.dismiss(); }

    public Button getBtnPedra() { return btnPedra; }
    public Button getBtnPaper() { return btnPaper; }
    public Button getBtnTisora() { return btnTisora; }
    public Button getBtnResposta() { return btnResposta; }
    public TextView getTvMsg() { return tvMsg; }
    public void setRespostaText(String txt) { btnResposta.setText(txt); }
}
