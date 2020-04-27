package src.pipeline.recombination;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.recombination.crossoverFunctions.CrossoverFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsecutivePairsRecombination implements Recombination {
    private double a, b;
    private CrossoverFunction crossoverFunction1, crossoverFunction2;
    private int generation;

    public ConsecutivePairsRecombination(double a, double b){
        this.generation = 0;
        this.a = a;
        this.b = b;
    }

    @Override
    public void setCrossoverFunctions(CrossoverFunction crossoverFunction1, CrossoverFunction crossoverFunction2) {
        this.crossoverFunction1 = crossoverFunction1;
        this.crossoverFunction2 = crossoverFunction2;

        if(this.crossoverFunction1 == null && this.crossoverFunction2 == null){
            throw new Error();
        }else if(this.crossoverFunction1 == null){
            this.crossoverFunction1 = this.crossoverFunction2;
            this.crossoverFunction2 = null;
        }
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, int childrenSize) {
        this.generation++;
        List<Individual> newIndividuals = new ArrayList<>(individuals);

        int population1 = (int)this.a*childrenSize;
        int population2 = childrenSize-population1;

        newIndividuals.addAll(generateChildren(individuals, this.crossoverFunction1, population1));

        if(crossoverFunction2!=null)
            newIndividuals.addAll(generateChildren(individuals, this.crossoverFunction2, population2));

        return newIndividuals;
    }

    private List<Individual> generateChildren(List<Individual> individuals, CrossoverFunction crossoverFunction, int childrenNumber){
        List<Individual> children = new ArrayList<>(childrenNumber);

        List<Integer> indexes = IntStream.range(0, individuals.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(indexes);

        for(int i=0; i<childrenNumber/2; i++){
            children.addAll(crossoverFunction.cross(individuals.get(indexes.get(i*2)), individuals.get(indexes.get(i*2+1))));
        }

        for(Individual child: children){
            child.setGeneration(generation);
        }

        return children;
    }
}
