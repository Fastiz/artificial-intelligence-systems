package kohonen;

import java.util.*;
import java.util.function.Supplier;

public class SquareLattice implements Lattice {
    private static class Coord {
        int i, j;

        Coord(int i, int j){
            this.i=i;
            this.j=j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return i == coord.i &&
                    j == coord.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    private final Cell[][] lattice;

    public SquareLattice(Supplier<Cell> cellGenerator, int dim){
        this.lattice = new Cell[dim][dim];

        generateLatticeWithSupplier(cellGenerator, dim);
    }

    private void generateLatticeWithSupplier(Supplier<Cell> cellGenerator, int dim){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                this.lattice[i][j] = cellGenerator.get();
            }
        }
    }

    /*@Override
    public List<Neighbor> getNeighbors(Cell cell, double distance) {

        List<Neighbor> result = new ArrayList<>();

        Set<Coord> addedCoords = new HashSet<>();

        Queue<Coord> frontier = new LinkedList<>();
        Coord cellCoord = findCoordinate(cell);

        if(cellCoord == null)
            throw new IllegalArgumentException("The cell passed as parameter does not belong to the lattice");

        frontier.add(cellCoord);

        while(!frontier.isEmpty()){
            Coord current = frontier.poll();

            double eucDistance = euclideanDistance(cellCoord.i, cellCoord.j, current.i, current.j);
            if(eucDistance > distance)
                continue;

            result.add(new Neighbor(lattice[current.i][current.j], eucDistance));

            Coord newCoord;

            if(current.i + 1 < lattice.length){
                newCoord = new Coord(current.i + 1, current.j);
                if(!addedCoords.contains(newCoord)){
                    addedCoords.add(newCoord);
                    frontier.add(newCoord);
                }
            }

            if(current.i - 1 >= 0){
                newCoord = new Coord(current.i - 1, current.j);
                if(!addedCoords.contains(newCoord)){
                    addedCoords.add(newCoord);
                    frontier.add(newCoord);
                }
            }

            if(current.j + 1 < lattice.length){
                newCoord = new Coord(current.i, current.j + 1);
                if(!addedCoords.contains(newCoord)){
                    addedCoords.add(newCoord);
                    frontier.add(newCoord);
                }
            }

            if(current.j - 1 >= 0){
                newCoord = new Coord(current.i, current.j - 1);
                if(!addedCoords.contains(newCoord)){
                    addedCoords.add(newCoord);
                    frontier.add(newCoord);
                }
            }

        }

        return result;
    }*/

    @Override
    public List<Neighbor> getNeighbors(Cell cell){
        Coord cellCoord = findCoordinate(cell);

        if(cellCoord == null)
            throw new IllegalArgumentException("The cell passed as parameter does not belong to the lattice");

        List<Neighbor> result = new ArrayList<>();
        for(int i=0; i<lattice.length; i++){
            for(int j=0; j<lattice.length; j++) {
                Cell neighbor = lattice[i][j];
                result.add(
                        new Neighbor(
                                neighbor,
                                euclideanDistance(cellCoord.i, cellCoord.j, i, j)
                        )
                );
            }
        }
        return result;
    }

    @Override
    public List<Cell> getAllCells() {
        List<Cell> cells = new ArrayList<>();
        for(int i=0; i<lattice.length; i++){
            for(int j=0; j<lattice.length; j++){
                cells.add(lattice[i][j]);
            }
        }
        return cells;
    }

    private Coord findCoordinate(Cell cell){
        for(int i=0; i<lattice.length; i++){
            for(int j=0; j<lattice.length; j++){
                if(lattice[i][j] == cell)
                    return new Coord(i, j);
            }
        }
        return null;
    }

    private double euclideanDistance(int i1, int j1, int i2, int j2){
        return Math.sqrt(Math.pow(i1-i2, 2) + Math.pow(j1-j2, 2));
    }
}
