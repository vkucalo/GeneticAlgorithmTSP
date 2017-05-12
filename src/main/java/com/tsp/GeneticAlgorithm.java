package com.tsp;

/**
 * Created by Viktor on 12.5.2017..
 */
import java.util.*;

public class GeneticAlgorithm {
    static final int kolicinaOdabranih = 50;
    static final int numberOfGenerations = 1000;
    static final int numberOfIndividuals = 100;

    Individual best ;

    public GeneticAlgorithm() {

        Scanner unos = new Scanner(System.in);
        int N = unos.nextInt();
        City[] city = new City[N];
        for (int i = 0; i < N; i++) {
            city[i] = new City(unos.nextInt(), unos.nextInt());
        }

        Population population = new Population(numberOfIndividuals, N, city);
        population.initializeRandom(numberOfIndividuals);
        best = new Individual(population.findBest().dna.length, city);
        best.calculateFitness();
        for(int i = 0; i < numberOfGenerations; i++){
            if(population.findBest().fitness > best.fitness){
                System.out.println("naso bolji : " + population.findBest().fitness);
                best.dna = population.findBest().dna;

                best.calculateFitness();
            }
            population = crossover(city, population);
            System.out.println("the best : " + best.toString() + " fitness = " + best.fitness);
        }

    }

    public Population crossover(City[] city, Population population) {

        Collections.sort(population.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.fitness < o2.fitness){
                    return 1;
                }
                else if(o1.fitness > o2.fitness){
                    return -1;
                }
                return 0;
            }
        });
        //Select the best 50%
        Population prezivjeli = new Population(numberOfIndividuals/2, population.dnaLength, city);
        for(int i = 0; i < population.population.size()/2; i++){
            prezivjeli.add(population.get(i));
        }
//        for (int i = 0; i < numberOfIndividuals/2; i++) {
//            prezivjeli.add(population.get(((Arrays.binarySearch(normalizedFitness, Math.random())) * (-1))-1));
//        }
        ArrayList<Integer> positionsToSwap = new ArrayList<>();
        for(int i = 0; i <(int) (city.length * 0.2); i++){
            int x = (int) (Math.random() * city.length);
            if(positionsToSwap.contains(x)){
                i--;
                continue;
            }
            positionsToSwap.add(x);

        }
        Collections.shuffle(positionsToSwap);
        for(int i = 0; i < prezivjeli.population.size(); i++){
            prezivjeli.get(i).shuffleDna(positionsToSwap);
        }
        prezivjeli.initializeRandom(numberOfIndividuals/2);
        return prezivjeli;
    }


    public void mutate(ArrayList<Individual> prezivjeli){
        ArrayList<Integer> polozajMutacija = new ArrayList<>();
        polozajMutacija.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        polozajMutacija.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        for(int i = 0; i < prezivjeli.size(); i++){
            prezivjeli.get(i).shuffleDna(polozajMutacija);
        }
    }
    public static void main(String [] args){
        GeneticAlgorithm l = new GeneticAlgorithm();
    }
}
