package back.heuristics;

import back.interfaces.Game;
import back.interfaces.Heuristic;
import back.heuristics.Edge;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class GreedyAssignation implements Heuristic {

    @Override
    public int evaluate(Game game) {
        int[][] boxes = game.getBoxesPositions(), goals = game.getGoalsPositions();

        List<Edge> matching = new LinkedList<>();

        PriorityQueue<Edge> priorityQueue = generatePriorityQueue(boxes, goals);
        List<int[]> matchedBoxes = new LinkedList<>();
        List<int[]> matchedGoals = new LinkedList<>();
        while(!priorityQueue.isEmpty()){
            Edge edge = priorityQueue.poll();
            if(!matchedBoxes.contains(edge.getBox()) && !matchedGoals.contains(edge.getGoal())){
                matching.add(edge);
                matchedBoxes.add(edge.getBox());
                matchedGoals.add(edge.getGoal());
            }
        }
        for(int[] box : boxes){
            if(!matchedBoxes.contains(box)){
                Edge newEdge = getMinimumEdge(box, goals);
                matching.add(newEdge);
            }
        }

        int totalDistance = 0;
        for(Edge edge : matching){
            totalDistance += edge.getDistance();
        }

        return totalDistance;
    }

    private PriorityQueue<Edge> generatePriorityQueue(int[][] boxes, int[][] goals){
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
        for(int[] box : boxes){
            for(int[] goal : goals){
                priorityQueue.add(new Edge(box, goal));
            }
        }
        return priorityQueue;
    }

    private Edge getMinimumEdge(int[] box, int[][] goals){
        Edge minEdge = null;
        for(int[] goal : goals){
            Edge newEdge = new Edge(box, goal);
            if(minEdge == null || minEdge.compareTo(newEdge) > 0){
                minEdge = newEdge;
            }
        }
        return minEdge;
    }
}
