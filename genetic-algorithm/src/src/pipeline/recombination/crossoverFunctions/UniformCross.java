package src.pipeline.recombination.crossoverFunctions;

import src.models.Equipment;
import src.models.Individual;

import java.util.ArrayList;
import java.util.List;

public class UniformCross implements CrossoverFunction {
    @Override
    public List<Individual> cross(Individual individual1, Individual individual2) {
        int randomLocus = (int) (Math.random() * Individual.maxLocus);
        List<Individual> children = new ArrayList<>();

        Individual child1 = new Individual(), child2 = new Individual();

        if (randomLocus == 0) {
            child1.setHeight(individual2.getHeight());
            child2.setHeight(individual1.getHeight());
        } else {
            child1.setHeight(individual1.getHeight());
            child2.setHeight(individual2.getHeight());
        }

        List<Equipment> equipment1 = individual1.getEquipmentLocus(), equipment2 = individual2.getEquipmentLocus();
        List<Equipment> equipmentList1 = new ArrayList<>(), equipmentList2 = new ArrayList<>();

        for (int i = 0; i < Individual.maxLocus - 1; i++) {
            if (i + 1 == randomLocus) {
                equipmentList1.add(equipment2.get(i));
                equipmentList2.add(equipment1.get(i));
            } else {
                equipmentList1.add(equipment1.get(i));
                equipmentList2.add(equipment2.get(i));
            }
        }

        child1.setEquipmentLocus(equipmentList1);
        child2.setEquipmentLocus(equipmentList2);

        children.add(child1);
        children.add(child2);

        return children;
    }
}