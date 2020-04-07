package back;

import java.util.ArrayList;
import java.util.List;

import back.interfaces.Game;

public class AlgorithmSolution {
	private String parameters;
	private boolean goalFound;
	private int solutionDepth;
	private float solutionCost;
	private int numberOfExpandedNodes;
	private int numberOfBorderNodes;
	private List<Game> solutionStates;
	private long processingTime;
	private String algorithmName;
	private String heuristicName;

	public AlgorithmSolution (String algorithmName, boolean goalFound, int numberOfExpandedNodes, long processingTime) {
		this.algorithmName = algorithmName;
		this.goalFound = goalFound;
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.processingTime = processingTime;
	}

	public AlgorithmSolution (String algorithmName, int numberOfExpandedNodes, int numberOfBorderNodes, Game solution, long processingTime) {
		this.algorithmName = algorithmName;
		this.goalFound = true;
		this.solutionDepth = solution.getDepth();
		this.solutionCost = solution.getCostValue();
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.numberOfBorderNodes = numberOfBorderNodes;
		this.solutionStates = solution.getPathFromRoot();
		this.solutionStates.add(solution);
		this.processingTime = processingTime;
	}
	
	public AlgorithmSolution (String algorithmName, String parameters, boolean goalFound, int solutionDepth, int solutionCost,
			int numberOfExpandedNodes, int numberOfBorderNodes, List<Game> visitedStates, long processingTime) {
		this.algorithmName = algorithmName;
		this.parameters = parameters;
		this.goalFound = goalFound;
		this.solutionDepth = solutionDepth;
		this.solutionCost = solutionCost;
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.numberOfBorderNodes = numberOfBorderNodes;
		this.solutionStates = visitedStates;
		this.processingTime = processingTime;
	}

	public void setHeuristicName(String name){
		this.heuristicName = name;
	}
	
	public String getParameters() {
		return this.parameters;
	}

	public boolean isGoalFound() {
		return goalFound;
	}

	public void setGoalFound(boolean goalFound) {
		this.goalFound = goalFound;
	}

	public int getSolutionDepth() {
		return solutionDepth;
	}

	public void setSolutionDepth(int solutionDepth) {
		this.solutionDepth = solutionDepth;
	}

	public float getSolutionCost() {
		return solutionCost;
	}

	public void setSolutionCost(int solutionCost) {
		this.solutionCost = solutionCost;
	}

	public int getNumberOfExpandedNodes() {
		return numberOfExpandedNodes;
	}

	public void setNumberOfExpandedNodes(int numberOfExpandedNodes) {
		this.numberOfExpandedNodes = numberOfExpandedNodes;
	}

	public int getNumberOfBorderNodes() {
		return numberOfBorderNodes;
	}

	public void setNumberOfBorderNodes(int numberOfBorderNodes) {
		this.numberOfBorderNodes = numberOfBorderNodes;
	}

	public List<Game> getSolutionStates() {
		return solutionStates;
	}

	public void setSolutionStates(List<Game> solutionStates) {
		if(solutionStates != null && !solutionStates.isEmpty())
			this.setGoalFound(true);
		this.solutionStates = solutionStates;
	}

	public long getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(long processingTime) {
		this.processingTime = processingTime;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		if(this.goalFound) {
			return printSolution(this);
		} else {
			return printNoSolution(this.numberOfExpandedNodes, this.processingTime);
		}
	}

	private String printNoSolution(int expandedNodes, long time) {
		StringBuffer sb = new StringBuffer();

		sb.append("No solution found" + "\n\n");

		sb.append("Expanded nodes: ").append(expandedNodes).append("\n\n");

		sb.append("Time: ").append(time).append("\n\n");

		return sb.toString();
	}

	private String printSolution(AlgorithmSolution solution) {
		StringBuffer sb = new StringBuffer();

		sb.append("Algorithm: ").append(this.algorithmName).append('\n');

		if(this.heuristicName != null)
			sb.append("Heuristic: ").append(this.heuristicName).append('\n');

		sb.append("Solution found!" + "\n");

		sb.append("Solution depth: ").append(solution.getSolutionDepth()).append("\n");

		sb.append("Solution cost: ").append(solution.getSolutionCost()).append("\n");

		sb.append("Expanded nodes: ").append(solution.getNumberOfExpandedNodes()).append("\n");

		sb.append("Border nodes: ").append(solution.getNumberOfBorderNodes()).append("\n");

		sb.append("Time: ").append(solution.getProcessingTime()).append(" ms \n\n");

		for(Game game : this.solutionStates){
			//sb.append(game).append("\n");
		}
		return sb.toString();
	}

}
