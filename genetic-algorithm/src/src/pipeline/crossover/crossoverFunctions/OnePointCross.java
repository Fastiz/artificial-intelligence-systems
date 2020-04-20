package src.pipeline.crossover.crossoverFunctions;

import src.models.Equipment;
import src.models.Gen;
import src.pipeline.crossover.CrossoverFunction;

import java.util.ArrayList;
import java.util.List;

public class OnePointCross implements CrossoverFunction {
    @Override
    public List<Gen> cross(Gen gen1, Gen gen2) {
        int randomLocus = (int) (Math.random()*Gen.maxLocus);
        List<Gen> children = new ArrayList<>();

       Gen child1 = new Gen(), child2 = new Gen();

       if(randomLocus == 0){
           child1.setHeight(gen2.getHeight());
           child2.setHeight(gen1.getHeight());
       }else{
           child1.setHeight(gen1.getHeight());
           child2.setHeight(gen2.getHeight());
       }

       List<Equipment> equipment1 = gen1.getEquipmentLocus(), equipment2 = gen2.getEquipmentLocus();
       List<Equipment> equipmentList1 = new ArrayList<>(), equipmentList2 = new ArrayList<>();

       for(int i=0; i<Gen.maxLocus-1; i++){
           if(i>=randomLocus){
               equipmentList1.add(equipment2.get(i));
               equipmentList2.add(equipment1.get(i));
           }else{
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
