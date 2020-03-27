package back.algorithms;

import back.AlgorithmSolution;
import back.interfaces.Game;
import back.interfaces.Algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


public class DFS implements Algorithm {
	private HashSet<Game> hashSet;
	private LinkedList<Game> visitedStates;
	
    private int expandedNodes;
    private int visitedNodes;

    public DFS() {
    	hashSet = new HashSet<>();
    	visitedStates = new LinkedList<>();
    }
    
    public AlgorithmSolution run(Game game) {
        expandedNodes = 0;
        visitedNodes = 0;

        long startTime = System.nanoTime();
        boolean result = recursiveDFS(game);
        long endTime = System.nanoTime();
                
        long processingTime = endTime - startTime;
        
        //TODO: see cost and border count
        return new AlgorithmSolution(null, result, this.visitedStates.size(), 0, expandedNodes, 1, this.visitedStates, processingTime);

    }

    private boolean recursiveDFS(Game game) {
    	visitedNodes++;

    	if(hashSet.contains(game))
    		return false;
    	
        visitedStates.add(game);
        hashSet.add(game);
        
        if(game.gameFinished()){
            return true;
        }
        

        List<Game> childs = game.calculateChilds();

        for(Game child : childs) {
        	if(recursiveDFS(child)) {
        		/*if(i + 1 == availableActions.size())
                this.expandedNodes++;*/
        		return true;
        	}
        }
                
        visitedStates.removeLast();
        hashSet.remove(game);

        this.expandedNodes++;
        return false;
    }
}
