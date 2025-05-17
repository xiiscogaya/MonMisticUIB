package com.example.monmisticuib.controller;

import android.graphics.Color;

import com.example.monmisticuib.model.Creature;
import com.example.monmisticuib.model.GenereAtributs;
import com.example.monmisticuib.model.Zone;

import java.util.*;

public class CreatureGenerator {

    // Mapa principal: zona popular → gènere → conjunt de criatures
    private final Map<String, Map<String, Set<Creature>>> catalogPerZonaIGenere = new TreeMap<>();

    // Conjunts de captures i escapades
    private final Set<Creature> capturades = new TreeSet<>(Comparator.comparing(Creature::getName));
    private final Set<Creature> escapades = new TreeSet<>(Comparator.comparing(Creature::getName));

    // Atributs de cada gènere
    private final Map<String, GenereAtributs> atributsPerGenere = new HashMap<>();

    private final List<String> generes = List.of("aiguard", "focguard", "tornadrac", "vapordrac");
    private final Random random = new Random();

    public CreatureGenerator(Collection<Zone> zones) {
        definirAtributs();
        generarCriatures(zones);
    }

    private void definirAtributs() {
        atributsPerGenere.put("aiguard", new GenereAtributs(0.01f, Color.BLACK, 10, 0f));
        atributsPerGenere.put("focguard", new GenereAtributs(0.015f, Color.GREEN, 15, 1f));
        atributsPerGenere.put("tornadrac", new GenereAtributs(0.02f, Color.RED, 20, 2.5f));
        atributsPerGenere.put("vapordrac", new GenereAtributs(0.025f, Color.BLUE, 30, 3.5f));
    }

    private void generarCriatures(Collection<Zone> zones) {
        List<Zone> zonaList = new ArrayList<>(zones);
        int id = 1;

        for (String genere : generes) {
            for (int i = 0; i < 125; i++) {
                int especie = random.nextInt(8) + 1; // 1 a 8
                Zone zonaAleatoria = zonaList.get(random.nextInt(zonaList.size()));
                Creature c = new Creature(genere, especie, id++);

                String nomZona = zonaAleatoria.getNomPopular();

                catalogPerZonaIGenere
                        .computeIfAbsent(nomZona, z -> new TreeMap<>())
                        .computeIfAbsent(genere, g -> new TreeSet<>(Comparator.comparing(Creature::getName)))
                        .add(c);
            }
        }
    }

    // Métodos públicos para acceso desde controladores

    public Map<String, Map<String, Set<Creature>>> getCatalogPerZonaIGenere() {
        return catalogPerZonaIGenere;
    }

    public Set<Creature> getCapturades() {
        return capturades;
    }

    public Set<Creature> getEscapades() {
        return escapades;
    }

    public Map<String, GenereAtributs> getAtributsPerGenere() {
        return atributsPerGenere;
    }

    // Métodos para añadir criaturas capturades o escapades
    public void afegirCapturada(Creature c) {
        capturades.add(c);
    }

    public void afegirEscapada(Creature c) {
        escapades.add(c);
    }
}
