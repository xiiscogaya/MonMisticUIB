package com.example.monmisticuib.model;

public class RpsGame {
    public enum Move { ROCK, PAPER, SCISSORS }

    public enum Result { WIN, LOSE, DRAW }

    public static Result getResult(Move player, Move enemy) {
        if (player == enemy) return Result.DRAW;

        switch (player) {
            case ROCK: return (enemy == Move.SCISSORS) ? Result.WIN : Result.LOSE;
            case PAPER: return (enemy == Move.ROCK) ? Result.WIN : Result.LOSE;
            case SCISSORS: return (enemy == Move.PAPER) ? Result.WIN : Result.LOSE;
        }
        return Result.DRAW;
    }
}
