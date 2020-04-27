package src.pipeline.selection;

import src.models.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class FillParentSelection extends SelectionImpl{
    private int generation;

    public FillParentSelection(double a, double b) {
        super(a, b);
        this.generation = 0;
    }

    @Override
    public List<Individual> execute(List<Individual> individuals, int populationSize) {
        this.generation++;
        List<Individual> filtered = individuals.stream().filter(p->p.getGeneration()==this.generation).collect(Collectors.toList());

        int size = filtered.size();
        if(size == populationSize){
            return filtered;
        }else if(size > populationSize){
            return super.execute(filtered, populationSize);
        }else{
            List<Individual> lastGeneration = individuals.stream().filter(p->p.getGeneration()!=this.generation).collect(Collectors.toList());
            filtered.addAll(super.select(lastGeneration, populationSize-size));
            return filtered;
        }

    }
}
