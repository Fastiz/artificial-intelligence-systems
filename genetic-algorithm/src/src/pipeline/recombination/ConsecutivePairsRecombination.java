package src.pipeline.recombination;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.recombination.crossoverFunctions.CrossoverFunction;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;
import src.pipeline.selection.selectionFunctions.SelectionFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsecutivePairsRecombination implements Recombination {
    private double a, b;
    private FitnessFunction fitnessFunction;
    private SelectionFunction selectionFunction1, selectionFunction2;
    private CrossoverFunction crossoverFunction;
    private int generation;

    public ConsecutivePairsRecombination(double a, double b){
        this.generation = 0;
        this.a = a;
        this.b = b;
    }

    @Override
    public void setCrossoverFunction(CrossoverFunction crossoverFunction) {
        this.crossoverFunction = crossoverFunction;
    }

    @Override
    public void setSelectionFunctions(SelectionFunction s1, SelectionFunction s2){
        this.selectionFunction1 = s1;
        this.selectionFunction2 = s2;

        if(selectionFunction1 == null && selectionFunction2 == null){
            throw new Error();
        }else if(selectionFunction1 == null){
            this.selectionFunction1 = selectionFunction2;
            this.selectionFunction2 = null;
        }
    }

    @Override
    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, int childrenSize) {
        this.generation++;
        List<Individual> newIndividuals = new ArrayList<>(individuals);

        int population1 = (int)(this.a*childrenSize);
        int population2 = childrenSize-population1;

        newIndividuals.addAll(
                this.selectionFunction1.select(individuals, this.fitnessFunction, population1)
        );

        if(this.selectionFunction2 != null)
            newIndividuals.addAll(
                    this.selectionFunction2.select(individuals, this.fitnessFunction, population2)
            );

        newIndividuals = generateChildren(newIndividuals, this.crossoverFunction, childrenSize);

        newIndividuals.addAll(individuals);

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
