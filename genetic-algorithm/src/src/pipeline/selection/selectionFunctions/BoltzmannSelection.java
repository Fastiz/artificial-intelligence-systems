package src.pipeline.selection.selectionFunctions;

import src.models.Individual;
import src.pipeline.selection.fitnessFunctions.FitnessFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class BoltzmannSelection implements SelectionFunction{

    private double t;

    private final double T0;
    private final double Tc;
    private final double k;

    public BoltzmannSelection(double T0, double Tc, double k){
        this.t = 0;
        this.T0 = T0;
        this.Tc = Tc;
        this.k = k;
    }

    public void stepTime(double step){
        this.t+=step;
    }

    @Override
    public List<Individual> select(List<Individual> individuals, FitnessFunction fitnessFunction, int resultSize) {
        List<Double> values;

        values = calculateExps(individuals, fitnessFunction);

        double mean = calculateMean(values);

        values = values.stream().map(p->p/mean).collect(Collectors.toList());

        values = Util.accValues(Util.relativeValues(values));

        return Util.roulette(individuals, values, resultSize);
    }

    private List<Double> calculateExps(List<Individual> individuals, FitnessFunction fitnessFunction){
        List<Double> results = new ArrayList<>();

        for (Individual individual : individuals) {
            double value = Math.exp(fitnessFunction.calculate(individual) / getTemperature());
            results.add(value);
        }

        return results;
    }

    private double calculateMean(List<Double> values){
        double sum = 0;
        for (double value : values){
            sum+=value;
        }
        return sum/values.size();
    }

    private double getTemperature(){
        return this.T0 + (T0-Tc)*Math.exp(-this.k * this.t);
    }
}
