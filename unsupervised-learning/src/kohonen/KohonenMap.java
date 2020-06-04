package kohonen;

import utils.Vector;

import java.util.List;

public class KohonenMap {

    @FunctionalInterface
    public interface NeighborhoodFunction {
        Double apply(Double distance, Integer time, Double timeConstant);
    }

    @FunctionalInterface
    public interface LearningRateFunction {
        Double apply(Integer time, Double timeConstant);
    }


    public static class Builder {
        private Lattice lattice;
        private double timeConstant;
        private NeighborhoodFunction neighborhoodFunction;
        private LearningRateFunction learningRateFunction;

        public Builder(){}

        public Builder setLattice(Lattice lattice){
            this.lattice = lattice;
            return this;
        }

        public Builder setTimeConstant(double timeConstant){
            this.timeConstant = timeConstant;
            return this;
        }

        public Builder setNeighborhoodFunction(NeighborhoodFunction neighborhoodFunction){
            this.neighborhoodFunction = neighborhoodFunction;
            return this;
        }

        public Builder setLearningRateFunction(LearningRateFunction learningRateFunction){
            this.learningRateFunction = learningRateFunction;
            return this;
        }

        public KohonenMap create(){
            return new KohonenMap(lattice, timeConstant, neighborhoodFunction, learningRateFunction);
        }
    }

    private final Lattice lattice;
    private final double timeConstant;
    private final NeighborhoodFunction neighborhoodFunction;
    private final LearningRateFunction learningRateFunction;

    private int time = 0;

    public KohonenMap(Lattice lattice, double timeConstant, NeighborhoodFunction neighborhoodFunction,
                      LearningRateFunction learningRateFunction){
        this.lattice = lattice;
        this.timeConstant = timeConstant;
        this.neighborhoodFunction = neighborhoodFunction;
        this.learningRateFunction = learningRateFunction;
    }

    public void step(Vector input){
        Cell bmu = lattice.bestMatchingUnit(input);

        List<Lattice.Neighbor> neighborhood = lattice.getNeighbors(bmu);

        for(Lattice.Neighbor neighbor : neighborhood){
            updateWeights(neighbor, input);
        }

        time++;
    }

    public Lattice.Coord activate(Vector input){
         return lattice.bestMatchingUnitCoord(input);
    }

    private void updateWeights(Lattice.Neighbor neighbor, Vector input){
        double scalar = neighborhoodFunction.apply(neighbor.distance, time, timeConstant) *
                learningRateFunction.apply(time, timeConstant);

        Vector factor = Vector.subtract(input, neighbor.cell.getWeights());

        neighbor.cell.sumWeights(Vector.scalarMultiplication(factor, scalar));
    }

}
