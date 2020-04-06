package back.algorithms;

import back.AlgorithmSolution;
import back.algorithms.util.QueueSearch;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import back.interfaces.Heuristic;
import back.interfaces.InformedAlgorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar implements InformedAlgorithm {

    private Heuristic heuristic;

    public AStar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public AStar(){

    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    @Override
    public String getName(){
        return "A*";
    }

    @Override
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public AlgorithmSolution run(Game game) {
        Comparator<Game> gameComparator = (g1, g2) -> {
            int f1 = g1.getEstimatedCost() + g1.getDepth();
            int f2 = g2.getEstimatedCost() + g2.getDepth();
            return f1 - f2;
        };

        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);
        game.setEstimatedCost(heuristic.evaluate(game));
        gamesPriorityQueue.add(game);

        QueueSearch queueSearch = new QueueSearch(this.getName());
        return queueSearch.run(gamesPriorityQueue, heuristic);
    }
}
