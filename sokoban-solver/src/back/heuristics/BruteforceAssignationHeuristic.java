package back.heuristics;

import back.heuristics.util.Edge;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.LinkedList;
import java.util.List;

public class BruteforceAssignationHeuristic implements Heuristic {
    @Override
    public String getName(){
        return "Bruteforce assignation";
    }

    @Override
    public int evaluate(Game game) {
        int[][] boxes = game.getBoxesPositions(), goals = game.getGoalsPositions();

        List<Edge> edges = generatePriorityQueue(boxes, goals);

        return bruteForce(edges);
    }

    private List<Edge> generatePriorityQueue(int[][] boxes, int[][] goals){
        List<Edge> priorityQueue = new LinkedList<>();
        for(int[] box : boxes){
            for(int[] goal : goals){
                priorityQueue.add(new Edge(box, goal));
            }
        }
        return priorityQueue;
    }

    private int bruteForce(List<Edge> remainingEdges){
        if(remainingEdges.isEmpty())
            return 0;

        int min = Integer.MAX_VALUE;
        for(Edge edge : remainingEdges){
            List<Edge> newRemainingEdges = getListWithoutStaleEdges(edge, remainingEdges);
            int newVal = bruteForce(newRemainingEdges) + edge.getDistance();
            if(newVal < min)
                min = newVal;
        }

        return min;
    }

    private List<Edge> getListWithoutStaleEdges(Edge edge, List<Edge> remainingEdges){
        List<Edge> newEdges = new LinkedList<>();
        for(Edge newEdge : remainingEdges){
            if(newEdge.getBox() != edge.getBox() && newEdge.getGoal() != edge.getGoal()){
                newEdges.add(newEdge);
            }
        }
        return newEdges;
    }

}
