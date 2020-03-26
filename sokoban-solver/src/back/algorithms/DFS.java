package back.algorithms;

import back.AlgorithmSolution;
import back.ResultPrinter;
import back.game.Action;
import back.game.Game;
import back.interfaces.Algorithm;

import java.util.HashSet;
import java.util.List;

public class DFS implements Algorithm {
	private HashSet<Integer> visitedStates;
	
    private int expandedNodes;
    private int visitedNodes;

    public DFS() {
    	visitedStates = new HashSet<>();
    }
    
    public AlgorithmSolution run(Game game) {
        expandedNodes = 0;
        visitedNodes = 0;

        long startTime = System.nanoTime();
        boolean result = recursiveDFS(game);
        long endTime = System.nanoTime();
        
        List<Game> solution = game.getPathToCurrentState();
        
        long processingTime = endTime - startTime;
        
        //TODO: see cost and border count
        return new AlgorithmSolution(null, result, solution.size(), 0, expandedNodes, 1, solution, processingTime);

    }

    private boolean recursiveDFS(Game game) {
    	visitedNodes++;
    	        
    	//System.out.println(visitedNodes);
        if(game.gameFinished()){
            return true;
        }

        List<Action> availableActions = game.getAvailableActions();

        for(int i = 0; i < availableActions.size(); i++) {
            Action action = availableActions.get(i);
            
            game.applyAction(action);

            //System.out.println(game);
            int hash = game.hashCode();
            if(!visitedStates.contains(hash)) {
            	visitedStates.add(hash);
            	
            	if(recursiveDFS(game)) {
                    /*if(i + 1 == availableActions.size())
                        this.expandedNodes++;*/
                    return true;
                }
            }else {
            	//System.out.println("HOL");
            }
            
            visitedStates.remove(hash);
            
            game.revertAction();

        }

        if(visitedNodes > 50000)
        System.out.print(game);
        this.expandedNodes++;
        return false;
    }
}
