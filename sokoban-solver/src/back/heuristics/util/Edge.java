package back.heuristics.util;


public class Edge implements Comparable<Edge> {
    int[] box;
    int[] goal;
    int distance;

    public Edge(int[] box, int[] goal){
        this.box = box;
        this.goal = goal;
        this.distance = manhattanDistance();
    }

    private int manhattanDistance(){
        return Math.abs(box[0]-goal[0]) + Math.abs(box[1]-goal[1]);
    }

    @Override
    public int compareTo(Edge o) {
        return distance - o.distance;
    }

    public int getDistance() {
        return distance;
    }

    public int[] getBox() {
        return box;
    }

    public int[] getGoal() {
        return goal;
    }
}