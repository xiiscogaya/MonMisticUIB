package com.example.monmisticuib.model;

public class Zone {
    private final String nomPopular;
    private final String nomOficial;
    private final int x1, y1, x2, y2;

    public Zone(String nomPopular, String nomOficial, int x1, int y1, int x2, int y2) {
        this.nomPopular = nomPopular;
        this.nomOficial = nomOficial;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public String getNomPopular() { return nomPopular; }
    public String getNomOficial() { return nomOficial; }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }

    @Override
    public String toString() {
        return nomOficial + " [" + x1 + "," + y1 + " - " + x2 + "," + y2 + "]";
    }
}
