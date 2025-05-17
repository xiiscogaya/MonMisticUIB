package com.example.monmisticuib.controller;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.monmisticuib.MainActivity;
import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.model.GenereAtributs;
import com.example.monmisticuib.model.RpsGame;
import com.example.monmisticuib.view.PedraPaperTisoresView;

import java.util.Random;

public class PedraPaperTisoresController {

    private final Context context;
    private final PedraPaperTisoresView view;
    private final Creature criatura;
    private final CreatureGenerator creatureGenerator;

    public PedraPaperTisoresController(Context context,
                                       PedraPaperTisoresView view,
                                       Creature criatura,
                                       CreatureGenerator generator) {
        this.context = context;
        this.view = view;
        this.criatura = criatura;
        this.creatureGenerator = generator;

        setupListeners();
    }

    private void setupListeners() {
        view.getBtnPedra().setOnClickListener(v -> jugar(RpsGame.Move.ROCK));
        view.getBtnPaper().setOnClickListener(v -> jugar(RpsGame.Move.PAPER));
        view.getBtnTisora().setOnClickListener(v -> jugar(RpsGame.Move.SCISSORS));
    }

    private void jugar(RpsGame.Move jugador) {
        RpsGame.Move jugadaCriatura = getRandomMove();
        view.setRespostaText(jugadaCriatura.name());

        RpsGame.Result resultat = RpsGame.getResult(jugador, jugadaCriatura);
        String missatge;

        switch (resultat) {
            case WIN:
                creatureGenerator.afegirCapturada(criatura);
                missatge = "Has guanyat! Col·laborarà amb tu.";
                if (context instanceof MainActivity) {
                    ((MainActivity) context).getMapController().updatePunts();
                }
                break;
            case LOSE:
                creatureGenerator.afegirEscapada(criatura);
                missatge = "Has perdut! S’ha escapat.";
                break;
            default:
                missatge = "Empat! Torna-hi...";
                break;
        }

        Toast.makeText(context, missatge, Toast.LENGTH_SHORT).show();

        new CountDownTimer(2000, 1000) {
            @Override public void onTick(long l) {}

            @Override public void onFinish() {
                if (resultat == RpsGame.Result.DRAW) {
                    view.setRespostaText("?");
                    view.getTvMsg().setText("Empat! Torna a triar:");
                } else {
                    view.dismiss();
                }
            }
        }.start();
    }

    private RpsGame.Move getRandomMove() {
        RpsGame.Move[] moves = RpsGame.Move.values();
        return moves[new Random().nextInt(moves.length)];
    }
}
