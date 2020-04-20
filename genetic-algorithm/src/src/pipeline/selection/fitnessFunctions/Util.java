package src.pipeline.selection.fitnessFunctions;

import src.models.Equipment;
import src.models.Gen;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static double attack(Gen gen){
        return (totalAgility(gen) + totalExpertise(gen))*totalForce(gen)*ATM(gen);
    }

    public static double defense(Gen gen){
        return (totalResistance(gen) + totalExpertise(gen))*totalVitality(gen)*DEM(gen);
    }

    private static List<Equipment> extractEquipment(Gen gen){
        List<Equipment> eq = new ArrayList<>();
        eq.add(gen.getBreastplate());
        eq.add(gen.getBoots());
        eq.add(gen.getGauntlet());
        eq.add(gen.getHelm());
        eq.add(gen.getWeapon());
        return eq;
    }

    private static double totalForce(Gen gen){
        double sum = 0;
        for(Equipment eq : extractEquipment(gen)){
            sum += eq.getForce();
        }
        return 100*Math.atan(0.01*sum);
    }

    private static double totalAgility(Gen gen){
        double sum = 0;
        for(Equipment eq : extractEquipment(gen)){
            sum += eq.getAgility();
        }
        return Math.atan(0.01*sum);
    }

    private static double totalExpertise(Gen gen){
        double sum = 0;
        for(Equipment eq : extractEquipment(gen)){
            sum += eq.getExpertise();
        }
        return 0.6*Math.atan(0.01*sum);
    }

    private static double totalVitality(Gen gen){
        double sum = 0;
        for(Equipment eq : extractEquipment(gen)){
            sum += eq.getVitality();
        }
        return 100*Math.tan(0.01*sum);
    }

    private static double totalResistance(Gen gen){
        double sum = 0;
        for(Equipment eq : extractEquipment(gen)){
            sum += eq.getResistance();
        }
        return Math.tan(0.01*sum);
    }

    private static double ATM(Gen gen){
        double h = gen.getHeight();
        return 0.7 - (3*h - 5)*4 + (3*h - 5)*2 + h/4;
    }

    private static double DEM(Gen gen){
        double h = gen.getHeight();
        return 1.9 + (2.5*h - 4.16)*4 - (2.5*h - 4.16)*2 - 3*h/10;
    }
}
