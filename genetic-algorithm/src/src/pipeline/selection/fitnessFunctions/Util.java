package src.pipeline.selection.fitnessFunctions;

import src.models.Equipment;
import src.models.Individual;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static double attack(Individual individual){
        return (totalAgility(individual) + totalExpertise(individual))*totalForce(individual)*ATM(individual);
    }

    public static double defense(Individual individual){
        return (totalResistance(individual) + totalExpertise(individual))*totalVitality(individual)*DEM(individual);
    }

    private static List<Equipment> extractEquipment(Individual individual){
        List<Equipment> eq = new ArrayList<>();
        eq.add(individual.getBreastplate());
        eq.add(individual.getBoots());
        eq.add(individual.getGauntlet());
        eq.add(individual.getHelm());
        eq.add(individual.getWeapon());
        return eq;
    }

    private static double totalForce(Individual individual){
        double sum = extractEquipment(individual).stream().map(Equipment::getForce).mapToDouble(Double::doubleValue).sum();
        return 100*Math.tanh(0.01*sum);
    }

    private static double totalAgility(Individual individual){
        double sum = extractEquipment(individual).stream().map(Equipment::getAgility).mapToDouble(Double::doubleValue).sum();
        return Math.tanh(0.01*sum);
    }

    private static double totalExpertise(Individual individual){
        double sum = extractEquipment(individual).stream().map(Equipment::getExpertise).mapToDouble(Double::doubleValue).sum();
        return 0.6*Math.tanh(0.01*sum);
    }

    private static double totalVitality(Individual individual){
        double sum = extractEquipment(individual).stream().map(Equipment::getVitality).mapToDouble(Double::doubleValue).sum();
        return 100*Math.tanh(0.01*sum);
    }

    private static double totalResistance(Individual individual){
        double sum = extractEquipment(individual).stream().map(Equipment::getResistance).mapToDouble(Double::doubleValue).sum();
        return Math.tanh(0.01*sum);
    }

    private static double ATM(Individual individual){
        double h = individual.getHeight();
        return 0.7 - Math.pow(3*h - 5, 4) + Math.pow(3*h - 5, 2) + h/4;
    }

    private static double DEM(Individual individual){
        double h = individual.getHeight();
        return 1.9 + Math.pow(2.5*h - 4.16, 4) - Math.pow(2.5*h - 4.16, 2) - 3*h/10;
    }
}
