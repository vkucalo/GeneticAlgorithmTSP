package com.tsp;

/**
 * Created by Viktor on 12.5.2017..
 */
import java.lang.reflect.Array;
import java.util.*;

public class GeneticAlgorithm {
    static final int kolicinaOdabranih = 50;
    static final int numberOfGenerations = 1000;
    static final int numberOfIndividuals = 100;

    Individual best;

    public GeneticAlgorithm() {

        Scanner unos = new Scanner(System.in);
        int N = 10;
        City[] city = new City[10];
        city[0] = new City(200, 200);
        city[1] = new City(300, 300);
        city[2] = new City(400, 400);
        city[3] = new City(500, 500);
        city[4] = new City(600, 600);
        city[5] = new City(700, 700);
        city[6] = new City(800, 800);
        city[7] = new City(1000, 1000);
        city[8] = new City(1100, 1100);
        city[9] = new City(1110, 1110);

        //Initialize the population
        Population population = new Population(numberOfIndividuals, N, city);
        population.initializeRandom(numberOfIndividuals);
        best = new Individual(N, city);
        best.calculateFitness();
        for(int i = 0; i < 100; i++){
            population = crossover(population);
            if(population.findBest().fitness > best.fitness){
                best = population.findBest();
            }
            System.out.println("the best : " + best.toString() + " fitness = " + best.fitness);
        }

    }

    public Population crossover(Population population) {

        //Select the best using roullete selection
        Population survivors = new Population(0,population.dnaLength, population.city);
        survivors.population.addAll(rouletteSelection(population));
        for(int i = 0; i < population.population.size()/2; i+=2){
            survivors.population.addAll(PMX(survivors.get(i), survivors.get(i+1)));
        }
        //Add the better 50% back to the survivors
        mutate(survivors.population);
        return survivors;
    }


    public ArrayList<Individual> PMX(Individual parent1, Individual parent2){
        int dnaLen = parent1.dna.length;
        Individual child1 = new Individual(dnaLen, parent1.city);
        Individual child2 = new Individual(dnaLen, parent1.city);

        Map map1 = new HashMap();
        Map map2 = new HashMap();

        int from = (int)(dnaLen * Math.random()) ;
        int to = (int)((dnaLen - from) * Math.random()) + from;
        for(int i = 0; i < from; i++){
            child1.dna[i] = -1;
            child2.dna[i] = -1;
        }
        for(int i = from; i <= to; i++){
            child1.dna[i] = parent1.dna[i];
            child2.dna[i] = parent2.dna[i];
            map1.put(child1.dna[i], child2.dna[i]);
            map2.put(child2.dna[i], child1.dna[i]);
        }
        for(int i = to+1; i < dnaLen; i++){
            child1.dna[i] = -1;
            child2.dna[i] = -1;
        }

        child1= PMXCrossover(parent1,parent2,child1,map1,from, to);
        child2= PMXCrossover(parent2,parent1,child2,map2,from, to);

        ArrayList<Individual> offspring = new ArrayList<>();
        offspring.add(child1);
        offspring.add(child2);
        return offspring;

    }
    private Individual PMXCrossover(Individual parent1, Individual parent2, Individual child, Map map, int from, int to){
        for(int i = 0; i < parent1.dna.length; i++){
            int temp = parent2.dna[i];
            if(child.dna[i] != -1){
                continue;
            }
            if(map.containsKey(temp)){
                while(map.containsKey(temp)){
                    int tempPrev = temp;
                    temp = (int) map.get(temp);
                    if(temp == child.dna[i]){
                        break;
                    }
                    if(temp == tempPrev){
                        temp = tempPrev;
                        break;
                    }
                }
                child.dna[i] = temp;
            }
            else {
                if(child.dna[i] == -1){
                    child.dna[i] = temp;
                }
            }
        }
        return child;
    }
    public ArrayList<Individual> rouletteSelection(Population population){
        double [] roulette = new double[population.population.size()];
        double totalFit = 0;
        for(Individual i: population.population){
            totalFit += i.calculateFitness();
        }
        roulette[0] = population.get(0).fitness / totalFit;
        for(int i = 1; i < population.population.size(); i++){
            roulette[i] = roulette[i-1] + (population.get(i).fitness / totalFit);
        }
        Population chosen = new Population(0, population.get(0).dna.length, population.city);
        for(int i = 0; i < population.population.size()/2; i++){
            chosen.add(population.get(((Arrays.binarySearch(roulette, Math.random()))*(-1)) -1));

        }

        return chosen.population;
    }
    public void mutate(ArrayList<Individual> prezivjeli){
        ArrayList<Integer> positionToMutate = new ArrayList<>();
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        for(int i = 0; i < prezivjeli.size(); i++){
            if(Math.random()< 0.1){
                prezivjeli.get(i).shuffleDna(positionToMutate);
            }
        }
    }
    public static void main(String [] args){
        GeneticAlgorithm l = new GeneticAlgorithm();
    }
}
