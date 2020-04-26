package src;

import src.files.ConfigReader;
import src.pipeline.PipelineAdministrator;

import java.io.IOException;

public class Main {
    public static void main(String[] args){

        ConfigReader configReader;
        try{
            configReader = new ConfigReader("config.properties");
        }catch (IOException e){
            System.err.println(e);
            return;
        }

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
