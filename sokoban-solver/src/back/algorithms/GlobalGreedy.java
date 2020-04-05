package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Algorithm;
import back.interfaces.Game;
import back.interfaces.Heuristic;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

public class GlobalGreedy implements Algorithm {

    private Heuristic heuristic;

    public GlobalGreedy(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public AlgorithmSolution run(Game game) {
        Comparator<Game> gameComparator = Comparator.comparingInt(Game::getEstimatedCost);
        PriorityQueue<Game> gamesPriorityQueue = new PriorityQueue<>(gameComparator);

        game.setEstimatedCost(heuristic.evaluate(game));
        gamesPriorityQueue.add(game);

        QueueSearch queueSearch = new QueueSearch();
        return queueSearch.run(gamesPriorityQueue, heuristic);
    }
}
