package com.tsp;

/**
 * Created by Viktor on 12.5.2017..
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Individual{
    int[] dna;
    double fitness;
    int test;
    City[] city;
    public Individual(int n, City[] city) {
        this.city = city;
        test = 323;
        dna = new int [n];
        ArrayList<Integer> randomizer = new ArrayList<>();
        for(int i = 0; i < n; i++){
            randomizer.add(i);
        }
        Collections.shuffle(randomizer);
        for(int i = 0; i < n; i++){
            dna[i] = randomizer.get(i);
        }
        fitness = calculateFitness();
    }


    public void shuffleDna(ArrayList<Integer> positionsToShuffle){
        for(int i = 0; i < positionsToShuffle.size(); i+=2){
            int buffer = dna[positionsToShuffle.get(i)];
            dna[positionsToShuffle.get(i)] = dna[positionsToShuffle.get(i+1)];
            dna[positionsToShuffle.get(i+1)] = buffer;
        }
        fitness = calculateFitness();
    }
    public String toString(){
        return Arrays.toString(dna);
    }

    public double calculateFitness(){
        double ukupno = 0;
        for (int i = 0; i < this.dna.length - 1; i++) {
//            System.out.println("kalkuliram za dna = " + this.toString());
//            System.out.println("udaljenost izmedju " +city[this.dna[i]] + " i " + city[this.dna[i+1]] + " = " + city[i].izracunajUdaljenost(city[this.dna[i + 1]]) );
            ukupno += city[i].izracunajUdaljenost(city[this.dna[i + 1]]);
//            System.out.println("ukupno = " + ukupno);
        }
        this.fitness = 1/ukupno;
        return this.fitness;
    }
}
