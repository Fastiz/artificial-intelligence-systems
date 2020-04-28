package src.pipeline;

import src.models.Alleles;
import src.models.Individual;
import src.pipeline.cutCriterion.*;
import src.pipeline.mutation.Mutation;
import src.pipeline.recombination.Recombination;
import src.pipeline.selection.Selection;

import src.pipeline.selection.fitnessFunctions.FitnessFunction;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PipelineAdministrator {
    private final int populationSize;
    private final Alleles alleles;
    private List<Individual> population;

    private final Recombination recombination;
    private final Selection selection;
    private final Mutation mutation;
    private final CutCriterion cutCriterion;
    private final FitnessFunction fitnessFunction;
    private final boolean cloneEnabled;

    private int childrenSize;

    private int generationNumber;
    private List<Double> fitnessHistorial;
    private List<List<Individual>> generations;


    public PipelineAdministrator(int populationSize, int childrenSize, Mutation mutation, Selection selection, FitnessFunction fitnessFunction, Recombination recombination, CutCriterion cutCriterion) throws IOException {
        this.generationNumber = 0;
        this.generations = new ArrayList<>();
        this.fitnessHistorial = new ArrayList<>();
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
        this.childrenSize = childrenSize;
        String folder = "genetic-algorithm/testdata/";
        this.alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                "armas.tsv", "guantes.tsv", "botas.tsv");

        this.recombination = recombination;

        this.fitnessFunction = fitnessFunction;

        this.selection = selection;

        this.mutation = mutation;

        this.cutCriterion = cutCriterion;
        this.cloneEnabled = cutCriterion.getClass() == EstructureCut.class;

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
        return cutCriterion.shouldEnd(generationNumber, fitnessHistorial, generations);
    }

    public void step(){
        List<Individual> individuals = this.population;
        individuals = this.recombination.execute(individuals, this.childrenSize);
        individuals = this.mutation.execute(individuals, alleles);
        individuals = this.selection.execute(individuals, populationSize);
        this.population = individuals;
        this.fitnessHistorial.add(getBestFitness());
        this.generationNumber++;
        //Clonar elementos y agregar al historial
        if(cloneEnabled)
            generations.add(population.stream().map(Individual::new).collect(Collectors.toCollection(ArrayList::new)));
    }

    public double getBestFitness() {
        double bestFitness = -Double.MAX_VALUE;
        for(Individual individual : population) {
            double individualFitness = fitnessFunction.calculate(individual);
            if(individualFitness > bestFitness)
                bestFitness = individualFitness;
        }
        return bestFitness;
    }

    public void printBestFitnessIndividual() {
        double bestFitness = -Double.MAX_VALUE;
        Individual bestFitnessIndiviual = null;
        for(Individual individual : population) {
            double individualFitness = fitnessFunction.calculate(individual);
            if(individualFitness > bestFitness) {
                bestFitness = individualFitness;
                bestFitnessIndiviual = individual;
            }
        }

        if(bestFitnessIndiviual != null) {
            System.out.println("Fitness: " + bestFitness);
            System.out.println(bestFitnessIndiviual.toString());
        }
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public List<Double> getFitnessHistorial() {
        return fitnessHistorial;
    }

    public List<Individual> getDistinctIndividuals(){
        Set<Individual> set = new HashSet<>(this.population);
        return Arrays.asList(set.toArray(new Individual[0]));
    }
}
