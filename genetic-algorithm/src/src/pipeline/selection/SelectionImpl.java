package src.pipeline.selection;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;
import src.pipeline.selection.selectionFunctions.SelectionFunction;

import java.util.ArrayList;
import java.util.List;

public class SelectionImpl implements Selection {
    private double a, b;

    private SelectionFunction selectionFunction1, selectionFunction2;

    private FitnessFunction fitnessFunction;

    public SelectionImpl(double a, double b){
        this.a = a;
        this.b = b;
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
    public List<Individual> execute(List<Individual> individuals, int populationSize) {
        return null;
    }

    @Override
    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    protected List<Individual> select(List<Individual> individuals, int populationSize) {
        int population1 = (int)(this.a*populationSize);
        int population2 = populationSize - population1;

        List<Individual> newPopulation = new ArrayList<>(populationSize);

        newPopulation.addAll(this.selectionFunction1.select(individuals, this.fitnessFunction, population1));

        if(selectionFunction2 != null)
            newPopulation.addAll(this.selectionFunction2.select(individuals, this.fitnessFunction, population2));

        return newPopulation;
    }
}
