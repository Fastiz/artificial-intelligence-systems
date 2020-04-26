package src;

import src.pipeline.CutCriterion;
import src.pipeline.cutCriterion.TimeCut;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        PipelineAdministrator pa;
        try {
            pa = new PipelineAdministrator(500);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(!pa.shouldEnd()){
            pa.step();
        }

        pa.getPopulation();
    }
}
