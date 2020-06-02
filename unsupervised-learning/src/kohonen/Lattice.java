package kohonen;

import java.util.List;

public interface Lattice {
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

}
