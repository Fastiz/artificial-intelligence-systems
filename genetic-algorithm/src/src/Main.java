package src;

import src.files.ConfigReader;
import src.models.Alleles;
import src.models.Equipment;
import src.models.Individual;
import src.pipeline.PipelineAdministrator;

import src.pipeline.selection.fitnessFunctions.Warrior;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        runWithStats();
        //test();
    }

    private static void run() {
        ConfigReader configReader;
        try {
            configReader = new ConfigReader("./config.properties");
        } catch (Exception e) {
            System.out.println("Error:");
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Loading files...");
        PipelineAdministrator pa;
        try {
            pa = new PipelineAdministrator(configReader.getPopulation(), configReader.getChildrenAmount(), configReader.getMutation(),
                    configReader.getSelection(), configReader.getFitnessFunction(), configReader.getRecombination(), configReader.getCutCriterion());
        } catch (IOException e) {
            System.out.println("Error:");
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Running...");
        while (!pa.shouldEnd()) {
            pa.step();
        }
        pa.printBestFitnessIndividual();
    }

    private static void runWithStats() {
        ConfigReader configReader;
        try {
            configReader = new ConfigReader("./config.properties");
        } catch (Exception e) {
            System.out.println("Error:");
            System.out.println(e.getMessage());
            return;
        }


        System.out.println("Loading files...");

        PipelineAdministrator pa;
        try {
            pa = new PipelineAdministrator(configReader.getPopulation(), configReader.getChildrenAmount(), configReader.getMutation(),
                    configReader.getSelection(), configReader.getFitnessFunction(), configReader.getRecombination(), configReader.getCutCriterion());
        } catch (IOException e) {
            System.out.println("Error:");
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Running...");
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("./distinct"))) {
            while (!pa.shouldEnd()) {
                bf.write(String.valueOf(pa.getDistinctIndividuals().size()) + '\n');
                pa.step();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pa.printBestFitnessIndividual();
        System.out.println("Output files generated.");
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("./fitness"))) {
            for (double fitness : pa.getFitnessHistorial()) {
                bf.write(String.valueOf(fitness) + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test() {
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
