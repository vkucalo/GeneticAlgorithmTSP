package com.tsp;

/**
 * Created by Viktor on 12.5.2017..
 */
import java.util.*;

public class GeneticAlgorithm {
    static final int kolicinaOdabranih = 50;
    static final int numberOfGenerations = 1000;
    static final int numberOfIndividuals = 100;

    Individual best;

    public GeneticAlgorithm() {

        Scanner unos = new Scanner(System.in);
        int N = 7;
        City[] city = new City[7];
        city[0] = new City(2, 2);
        city[1] = new City(3, 3);
        city[2] = new City(4, 4);
        city[3] = new City(5, 5);
        city[4] = new City(6, 6);
        city[5] = new City(1000, 1000);
        city[6] = new City(100, 100);


        //Initialize the population
        Population population = new Population(numberOfIndividuals, N, city);
        population.initializeRandom(numberOfIndividuals);
        best = new Individual(N, city);
        best.calculateFitness();
        System.out.println(best.fitness);
        for(int i = 0; i < 100; i++){
            population = crossover(city, population);
            if(population.findBest().fitness > best.fitness){
                best = population.findBest();
            }

            System.out.println("the best : " + best.toString() + " fitness = " + best.fitness);
        }

    }

    public Population crossover(City[] city, Population population) {

        //Sort the population by fitness
        Collections.sort(population.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.fitness > o2.fitness){
                    return 1;
                }
                else if(o1.fitness < o2.fitness){
                    return -1;
                }
                return 0;
            }
        });

        //Select the best 50%
        Population survivors = new Population(numberOfIndividuals/2, population.dnaLength, city);
        for(int i = 0; i < population.population.size()/2; i++){
            survivors.add(population.get(i));
        }
        //Determine which positions inside the DNA will be swapped
        ArrayList<Integer> positionsToSwap = new ArrayList<>();
        int possiblePairs = city.length / 2;
        for(int i = 0; i < (possiblePairs + 1) * Math.random(); i++){
            int x = (int) (Math.random() * city.length);
            int y = (int) (Math.random() * city.length);
            positionsToSwap.add(x);
            positionsToSwap.add(y);

        }
        Collections.shuffle(positionsToSwap);
        //Shuffle the DNA of the worse 50% of the population
        for(int i = 0; i < survivors.population.size(); i++){
            survivors.get(i).shuffleDna(positionsToSwap);
        }
        //Add the better 50% back to the survivors
        for(int i = population.population.size()/2; i < population.population.size() ; i++){
            survivors.add(population.get(i));
        }
        mutate(survivors.population);
        return survivors;
    }


    public void mutate(ArrayList<Individual> prezivjeli){
        ArrayList<Integer> positionToMutate = new ArrayList<>();
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        for(int i = 0; i < prezivjeli.size(); i++){
            if(Math.random()< 0.05){
                prezivjeli.get(i).shuffleDna(positionToMutate);
            }
        }
    }
    public static void main(String [] args){
        GeneticAlgorithm l = new GeneticAlgorithm();
    }
}
