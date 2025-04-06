package com.example.monmisticuib.model;

public class Creature {
    private String name;
    private String genre;
    private int species;
    private int id;

    public Creature(String genre, int species, int id) {
        this.name = genre + species + " " + id;
        this.genre = genre;
        this.species = species;
        this.id = id;
    }

    public String getName() { return name; }
    public String getGenre() { return genre; }
    public int getSpecies() { return species; }
    public int getId() { return id; }
}
