package kohonen;

import utils.CsvReader;
import utils.Vector;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

public class Simulation {

    public static void run(){

        //Kohonen map generator
        int latticeDim = 5;
        int inputDim = 7;
        KohonenMap map = (new KohonenMap.Builder())
                .setLattice(new SquareLattice(cellGenerator(inputDim), latticeDim))
                .setNeighborhoodFunction(Simulation::neighborhoodFunction)
                .setLearningRateFunction(Simulation::learningRateFunction)
                .setTimeConstant(1)
                .create();

        //Read europe.csv for training
        Map<String, Vector> categories;
        try{
            categories = (new CsvReader(
                    "./unsupervised-learning/europe.csv",
                    ",",
                    true)
            )
                    .read()
                    .getCategories();

        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        //Training
        for(int t=0; t<10000; t++){
            for(Vector vector : categories.values()){
                map.step(vector);
            }
        }

        //Done training
        System.out.println("Done training!");

    }

    public static double neighborhoodFunction(double distance, int time, double timeConstant){
        if(distance == 0)
            return 1;

        double a0 = 1;
        double aux = a0*Math.exp(-time/timeConstant);

        return Math.exp(-Math.pow(distance, 2)/(2*Math.pow(aux, 2)));
    }

    public static double learningRateFunction(int time, double timeConstant){
        double a0 = 1;
        return a0*Math.exp(-time/timeConstant);
    }

    public static Supplier<Cell> cellGenerator(int dim){
        return () -> {
            double[] values = new double[dim];
            for(int i=0; i<dim; i++){
                values[i] = Math.random();
            }
            return new CellImpl(new Vector(values));
        };
    }
}
