package com.tsp;
import java.util.ArrayList;

/**
 * Created by Viktor on 12.5.2017..
 */
public class Population {
    int size;
    ArrayList<Individual> population = new ArrayList<>();
    City[] city;
    double totalFitness;
    int dnaLength;

    public Population(int size, int dnaLength, City[] city){
        this.dnaLength = dnaLength;
        this.size = size;
        this.city = city;
    }
    public Individual findBest(){
        Individual aux = new Individual(population.get(0).dna.length, city);
        aux.fitness = 0;
        for(int i = 0; i < population.size(); i++){
            if(population.get(i).fitness > aux.fitness){
                aux = population.get(i);
            }
        }
        return aux;
    }
    public Individual get(int x){
        return population.get(x);
    }

    public void initializeRandom(int n){
        for(int i = 0; i < n; i++){
            population.add(new Individual(dnaLength, city));
        }
    }
    public void calcTotalFitness(){
        totalFitness = 0;
        for(int i = 0; i < population.size(); i++){
            totalFitness += population.get(i).calculateFitness();
        }
    }
    public int size(){
        return size;
    }
    public void add(Individual i){
        population.add(i);
    }
}