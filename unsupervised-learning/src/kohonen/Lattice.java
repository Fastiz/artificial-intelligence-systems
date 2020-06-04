package kohonen;

import utils.Vector;

import java.util.List;

public interface Lattice {
    class Coord{
        private final int i;
        private int j;
        public Coord(int i, int j){
            this.i=i;
            this.j=j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }

    class Neighbor {
        double distance;
        Cell cell;

        public Neighbor(Cell cell, double distance){
            this.distance = distance;
            this.cell = cell;
        }
    }

    public List<Neighbor> getNeighbors(Cell cell);

    public List<Cell> getAllCells();

    public Cell bestMatchingUnit(Vector input);

    public Coord bestMatchingUnitCoord(Vector input);

}
