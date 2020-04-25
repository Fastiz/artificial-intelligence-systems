package src;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.Mutation;
import src.pipeline.Recombination;
import src.pipeline.Selection;
import src.pipeline.crossover.ConsecutivePairsRecombination;
import src.pipeline.crossover.crossoverFunctions.OnePointCross;
import src.pipeline.mutation.NoMutation;
import src.pipeline.selection.BestFitnessSelection;
import src.pipeline.selection.fitnessFunctions.Archer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PipelineAdministrator {
    private final int populationSize;
    private final Alleles alleles;
    private List<Individual> population;

    private final Recombination recombination;
    private final Selection selection;
    private final Mutation mutation;

    public PipelineAdministrator(int populationSize) throws IOException {
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);

        String folder = "genetic-algorithm/testdata/";
        this.alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                "armas.tsv", "guantes.tsv", "botas.tsv");

        this.recombination = new ConsecutivePairsRecombination();
        this.recombination.setCrossoverFunction(new OnePointCross());

        this.selection = new BestFitnessSelection();
        this.selection.setFitnessFunction(new Archer());

        this.mutation = new NoMutation();

        generateRandomPopulation();
    }

    public void generateRandomPopulation(){
        for(int i=0; i<populationSize; i++){
            this.population.add(
                    new Individual(
                            Math.random()*(2 - 1.3) + 1.3,
                            alleles.getHelms().get((int)(Math.random()*alleles.getHelms().size())),
                            alleles.getBreastplates().get((int)(Math.random()*alleles.getBreastplates().size())),
                            alleles.getWeapons().get((int)(Math.random()*alleles.getWeapons().size())),
                            alleles.getGauntlets().get((int)(Math.random()*alleles.getGauntlets().size())),
                            alleles.getBoots().get((int)(Math.random()*alleles.getBoots().size()))
                    )
            );
        }
    }

    public void step(){
        List<Individual> genes = this.population;
        genes = this.recombination.execute(genes, alleles);
        genes = this.mutation.execute(genes, alleles);
        genes = this.selection.execute(genes, populationSize);
        this.population = genes;
    }

    public List<Individual> getPopulation() {
        return population;
    }
}
