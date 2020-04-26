package src;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.CutCriterion;
import src.pipeline.Mutation;
import src.pipeline.Recombination;
import src.pipeline.Selection;
import src.pipeline.crossover.ConsecutivePairsRecombination;
import src.pipeline.crossover.crossoverFunctions.OnePointCross;
import src.pipeline.cutCriterion.TimeCut;
import src.pipeline.mutation.NoMutation;
import src.pipeline.selection.BestFitnessSelection;
import src.pipeline.selection.FitnessFunction;
import src.pipeline.selection.fitnessFunctions.Archer;

import java.io.IOException;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PipelineAdministrator {
    private final int populationSize;
    private final Alleles alleles;
    private List<Individual> population;

    private final Recombination recombination;
    private final Selection selection;
    private final Mutation mutation;
    private final CutCriterion cutCriterion;
    private final FitnessFunction fitnessFunction;

    private int generationNumber;
    private List<Double> fitnessHistorial;

    public PipelineAdministrator(int populationSize, Mutation mutation, Selection selection, FitnessFunction fitnessFunction, Recombination recombination, CutCriterion cutCriterion) throws IOException {
        this.generationNumber = 0;
        this.fitnessHistorial = new ArrayList<>();
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);

        String folder = "genetic-algorithm/testdata/";
        this.alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                "armas.tsv", "guantes.tsv", "botas.tsv");

        this.recombination = recombination;
        this.recombination.setCrossoverFunction(new OnePointCross());

        this.fitnessFunction = fitnessFunction;

        this.selection = selection;
        this.selection.setFitnessFunction(fitnessFunction);

        this.mutation = mutation;

        this.cutCriterion = cutCriterion;

        generateRandomPopulation();
    }

    public PipelineAdministrator(int populationSize) throws IOException {
        this.generationNumber = 0;
        this.fitnessHistorial = new ArrayList<>();
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);

        String folder = "genetic-algorithm/testdata/";
        this.alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                "armas.tsv", "guantes.tsv", "botas.tsv");

        this.recombination = new ConsecutivePairsRecombination();
        this.recombination.setCrossoverFunction(new OnePointCross());

        this.fitnessFunction = new Archer();

        this.selection = new BestFitnessSelection();
        this.selection.setFitnessFunction(this.fitnessFunction);

        this.mutation = new NoMutation();

        this.cutCriterion = new TimeCut(60);

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

    public boolean shouldEnd() {
        return cutCriterion.shouldEnd(generationNumber, fitnessHistorial);
    }

    public void step(){
        List<Individual> genes = this.population;
        genes = this.recombination.execute(genes, alleles);
        genes = this.mutation.execute(genes, alleles);
        genes = this.selection.execute(genes, populationSize);
        this.population = genes;
        this.fitnessHistorial.add(getBestFitnessIndividual());
        this.generationNumber++;
    }

    private double getBestFitnessIndividual() {
        double bestFitness = Double.MIN_VALUE;
        for(Individual individual : population) {
            double individualFitness = fitnessFunction.calculate(individual);
            if(individualFitness > bestFitness)
                bestFitness = individualFitness;
        }
        return bestFitness;
    }

    public List<Individual> getPopulation() {
        return population;
    }
}
