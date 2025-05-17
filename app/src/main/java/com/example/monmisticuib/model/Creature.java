package com.example.monmisticuib.model;

public class Creature {
    private final String genre;
    private final int species;
    private final int id;
    private final String name;

    public Creature(String genre, int species, int id) {
        this.genre = genre;
        this.species = species;
        this.id = id;
        this.name = genre + species + " " + id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getSpecies() {
        return species;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    // Igualdad basada en nombre
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creature)) return false;
        Creature c = (Creature) o;
        return this.name.equals(c.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
