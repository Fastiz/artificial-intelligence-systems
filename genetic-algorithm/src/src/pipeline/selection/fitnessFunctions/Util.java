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
        double sum = 0;
        for(Equipment eq : extractEquipment(individual)){
            sum += eq.getForce();
        }
        return 100*Math.atan(0.01*sum);
    }

    private static double totalAgility(Individual individual){
        double sum = 0;
        for(Equipment eq : extractEquipment(individual)){
            sum += eq.getAgility();
        }
        return Math.atan(0.01*sum);
    }

    private static double totalExpertise(Individual individual){
        double sum = 0;
        for(Equipment eq : extractEquipment(individual)){
            sum += eq.getExpertise();
        }
        return 0.6*Math.atan(0.01*sum);
    }

    private static double totalVitality(Individual individual){
        double sum = 0;
        for(Equipment eq : extractEquipment(individual)){
            sum += eq.getVitality();
        }
        return 100*Math.tan(0.01*sum);
    }

    private static double totalResistance(Individual individual){
        double sum = 0;
        for(Equipment eq : extractEquipment(individual)){
            sum += eq.getResistance();
        }
        return Math.tan(0.01*sum);
    }

    private static double ATM(Individual individual){
        double h = individual.getHeight();
        return 0.7 - (3*h - 5)*4 + (3*h - 5)*2 + h/4;
    }

    private static double DEM(Individual individual){
        double h = individual.getHeight();
        return 1.9 + (2.5*h - 4.16)*4 - (2.5*h - 4.16)*2 - 3*h/10;
    }
}
