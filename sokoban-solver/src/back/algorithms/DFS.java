package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Game;
import back.interfaces.Algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class DFS implements Algorithm {
    private HashSet<Game> hashSet;

    private int expandedNodes;
    private int borderNodes;

    private boolean onlyCachePathStates;

    public DFS() {
        hashSet = new HashSet<>();

        onlyCachePathStates = false;
    }

    public AlgorithmSolution run(Game game) {
        expandedNodes = 0;

        long startTime = System.currentTimeMillis();
        Game result = searchDFS(game);
        long endTime = System.currentTimeMillis();

        long processingTime = endTime - startTime;

        List<Game> path = result.getPathToRoot();

        return new AlgorithmSolution(null, result != null, result.getDepth(), result.getDepth(), this.expandedNodes, this.borderNodes, path, processingTime);

    }

    private Game searchDFS(Game initialGame) {
        Stack<Game> stack = new Stack<>();
        stack.push(initialGame);

        while (!stack.isEmpty()) {

            Game game = stack.pop();
            if (!hashSet.contains(game)) {
                hashSet.add(game);

                if (game.gameFinished()) {
                    this.borderNodes = stack.size();
                    return game;
                }

                List<Game> children = game.calculateChildren();

                children.forEach(stack::push);

                if (this.onlyCachePathStates)
                    hashSet.remove(game);

                this.expandedNodes++;
            }
        }
        return null;
    }
}