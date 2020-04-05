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
    private LinkedList<Game> visitedStates;

    private int expandedNodes;
    private int visitedNodes;

    private boolean onlyCachePathStates;

    public DFS() {
        hashSet = new HashSet<>();
        visitedStates = new LinkedList<>();

        onlyCachePathStates = false;
    }

    public AlgorithmSolution run(Game game) {
        expandedNodes = 0;
        visitedNodes = 0;

        long startTime = System.currentTimeMillis();
        Game result = searchDFS(game);
        long endTime = System.currentTimeMillis();

        long processingTime = endTime - startTime;

        //TODO: see cost and border count
        return new AlgorithmSolution(null, result != null, this.visitedStates.size(), this.visitedStates.getLast().getAccumulatedCost(), expandedNodes, 1, this.visitedStates, processingTime);

    }

    private boolean recursiveDFS(Game game) {
        this.visitedNodes++;

        if(hashSet.contains(game))
            return false;

        visitedStates.add(game);
        hashSet.add(game);

        if(game.gameFinished()){
            return true;
        }


        List<Game> children = game.calculateChildren();

        int i = 0;
        for(Game child : children) {
            if(recursiveDFS(child)) {
                if(i + 1 == children.size())
                    this.expandedNodes++;
                return true;
            }
            i++;
        }

        visitedStates.removeLast();

        if(this.onlyCachePathStates)
            hashSet.remove(game);

        this.expandedNodes++;
        return false;
    }

    private Game searchDFS(Game initialGame) {
        Stack<Game> stack = new Stack<>();
        stack.push(initialGame);

        while (!stack.isEmpty()) {
            this.visitedNodes++;

            Game game = stack.pop();
            if (!hashSet.contains(game)) {

                visitedStates.add(game);
                hashSet.add(game);

                if (game.gameFinished()) {
                    return game;
                }

                List<Game> children = game.calculateChildren();

                children.forEach(stack::push);

                visitedStates.removeLast();

                if (this.onlyCachePathStates)
                    hashSet.remove(game);

                this.expandedNodes++;
            }
        }
        return null;
    }
}