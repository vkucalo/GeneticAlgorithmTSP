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
                if(o1.fitness < o2.fitness){
                    return 1;
                }
                else if(o1.fitness > o2.fitness){
                    return -1;
                }
                return 0;
            }
        });

        //Select the best 50% + crossover
        Population survivors = new Population(0, population.dnaLength, city);
        for(int i = 0; i < population.population.size()/2; i+=2){
            survivors.population.addAll(PMX(population.get(i), population.get(i+1)));
        }
//        //Determine which positions inside the DNA will be swapped
//        ArrayList<Integer> positionsToSwap = new ArrayList<>();
//        int possiblePairs = city.length / 2;
//        for(int i = 0; i < (possiblePairs + 1) * Math.random(); i++){
//            int x = (int) (Math.random() * city.length);
//            int y = (int) (Math.random() * city.length);
//            positionsToSwap.add(x);
//            positionsToSwap.add(y);
//
//        }
//        Collections.shuffle(positionsToSwap);
//        //Shuffle the DNA of the worse 50% of the population
//        for(int i = 0; i < survivors.population.size(); i++){
//            survivors.get(i).shuffleDna(positionsToSwap);
//        }
        //Add the better 50% back to the survivors
        for(int i = 0; i < population.population.size()/2; i+=2){
            survivors.population.add(population.get(i));
        }
        mutate(survivors.population);
        return survivors;
    }


    public void mutate(ArrayList<Individual> prezivjeli){
        ArrayList<Integer> positionToMutate = new ArrayList<>();
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        positionToMutate.add((int) (Math.random() * prezivjeli.get(0).dna.length));
        for(int i = 0; i < prezivjeli.size(); i++){
            if(Math.random()< 0.01){
                prezivjeli.get(i).shuffleDna(positionToMutate);
            }
        }
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

        child1= createChild(parent1,parent2,child1,map1,from, to);
        child2= createChild(parent2,parent1,child2,map2,from, to);

        ArrayList<Individual> offspring = new ArrayList<>();
        offspring.add(child1);
        offspring.add(child2);
        return offspring;

    }
    private Individual createChild(Individual parent1, Individual parent2, Individual child, Map map, int from, int to){

        for(int i = 0; i < parent1.dna.length; i++){
            int temp = parent2.dna[i];
//            System.out.println("temp =" + temp);
            if(child.dna[i] != -1){
                continue;
            }
            if(map.containsKey(temp)){
//                System.out.println("sadrži temp = " + temp);
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
    private int checkLocation(Individual individual, int value){
        int dnaLen = individual.dna.length;
        for(int i = 0; i < dnaLen; i++){
            if(individual.dna[i] == value){
                return i;
            }
        }
        return -1;
    }
    public static void main(String [] args){
        GeneticAlgorithm l = new GeneticAlgorithm();
    }
}
