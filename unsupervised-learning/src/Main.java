import kohonen.Cell;
import kohonen.CellImpl;
import kohonen.KohonenMap;
import kohonen.SquareLattice;
import utils.Vector;

import java.util.function.Function;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args){
        int inputDim = 10;

        KohonenMap map = (new KohonenMap.Builder())
                .setLattice(new SquareLattice(cellGenerator(inputDim), inputDim))
                .setNeighborhoodFunction(Main::neighborhoodFunction)
                .setLearningRateFunction(Main::learningRateFunction)
                .setTimeConstant(1)
                .create();

        for(int t=0; t<1000; t++){
            map.step(Vector.zero(inputDim));
        }

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
