package src;

import src.files.ConfigReader;
import src.models.Alleles;
import src.models.Equipment;
import src.models.Individual;
import src.pipeline.PipelineAdministrator;
import src.pipeline.cutCriterion.CutCriterion;
import src.pipeline.cutCriterion.GenerationAmountCut;
import src.pipeline.mutation.Mutation;
import src.pipeline.mutation.SingleGenMutation;
import src.pipeline.mutation.UniformMultiGenMutation;
import src.pipeline.recombination.ConsecutivePairsRecombination;
import src.pipeline.recombination.Recombination;
import src.pipeline.recombination.crossoverFunctions.OnePointCross;
import src.pipeline.recombination.crossoverFunctions.RingCross;
import src.pipeline.recombination.crossoverFunctions.TwoPointCross;
import src.pipeline.recombination.crossoverFunctions.UniformCross;
import src.pipeline.selection.FillAllSelection;
import src.pipeline.selection.FillParentSelection;
import src.pipeline.selection.Selection;
import src.pipeline.selection.fitnessFunctions.Archer;
import src.pipeline.selection.fitnessFunctions.Warrior;
import src.pipeline.selection.selectionFunctions.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args){

        run();
        //test();

    }

    private static void run(){
        ConfigReader configReader;
        try{
            configReader = new ConfigReader("./genetic-algorithm/config.properties");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

        Recombination recombination = new ConsecutivePairsRecombination(1, 0);
        recombination.setCrossoverFunction(new UniformCross());
        recombination.setFitnessFunction(new Archer());
        recombination.setSelectionFunctions(new StochasticTournamentSelection(), null);

        Mutation mutation = new SingleGenMutation(.1);

        Selection selection = new FillAllSelection(1, 0);
        selection.setSelectionFunctions(new StochasticTournamentSelection(), null);
        selection.setFitnessFunction(new Archer());

        CutCriterion cutCriterion = new GenerationAmountCut(1000);

        PipelineAdministrator pa;
        try {
            pa = new PipelineAdministrator(500, 200, mutation, selection, new Warrior(), recombination, cutCriterion);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(!pa.shouldEnd()){
            pa.step();
        }

        //System.out.println(pa.getFitnessHistorial());

        try(BufferedWriter bw = new BufferedWriter(new FileWriter("./genetic-algorithm/output"))){
            for(double fitness : pa.getFitnessHistorial()){
                bw.write(String.valueOf(fitness)+'\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test(){
        try {
            String folder = "genetic-algorithm/testdata/";
            Alleles alleles = new Alleles(folder, "cascos.tsv", "pecheras.tsv",
                    "armas.tsv", "guantes.tsv", "botas.tsv");

            double height = 1.6230886063507983;
            Equipment weapon = alleles.getWeapons().get(7);
            Equipment boots = alleles.getBoots().get(6);
            Equipment helmet = alleles.getHelms().get(8);
            Equipment gloves = alleles.getGauntlets().get(8);
            Equipment chest = alleles.getBreastplates().get(8);

            Individual individual = new Individual(height, helmet, chest, weapon, gloves, boots);

            double fitness = (new Warrior()).calculate(individual);

            System.out.println(fitness);

            System.out.println(weapon.getId());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
