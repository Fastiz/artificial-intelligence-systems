package src;

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

        for(int i=0; i<1000; i++){
            pa.step();
        }

        pa.getPopulation();
    }
}
