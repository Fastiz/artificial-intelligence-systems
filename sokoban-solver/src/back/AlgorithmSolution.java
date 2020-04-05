package back;

import java.util.ArrayList;
import java.util.List;

import back.interfaces.Game;

public class AlgorithmSolution {
	private String parameters;
	private boolean goalFound;
	private int solutionDepth;
	private int solutionCost;
	private int numberOfExpandedNodes;
	private int numberOfBorderNodes;
	private List<Game> solutionStates;
	private long processingTime;

	public AlgorithmSolution (boolean goalFound, int numberOfExpandedNodes, long processingTime) {
		this.goalFound = goalFound;
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.processingTime = processingTime;
	}

	public AlgorithmSolution (int numberOfExpandedNodes, int numberOfBorderNodes, Game solution, long processingTime) {
		this.goalFound = true;
		this.solutionDepth = solution.getDepth();
		this.solutionCost = solution.getDepth();
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.numberOfBorderNodes = numberOfBorderNodes;
		this.solutionStates = solution.getPathToRoot();
		this.solutionStates.add(solution);
		this.processingTime = processingTime;
	}
	
	public AlgorithmSolution (String parameters, boolean goalFound, int solutionDepth, int solutionCost, 
			int numberOfExpandedNodes, int numberOfBorderNodes, List<Game> visitedStates, long processingTime) {
		this.parameters = parameters;
		this.goalFound = goalFound;
		this.solutionDepth = solutionDepth;
		this.solutionCost = solutionCost;
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.numberOfBorderNodes = numberOfBorderNodes;
		this.solutionStates = visitedStates;
		this.processingTime = processingTime;
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

	public int getSolutionCost() {
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

		sb.append("Solution found!" + "\n\n");

		List<Game> allGames = new ArrayList<>(solution.getSolutionStates());
		sb.append("Solution depth: ").append(solution.getSolutionDepth()).append("\n\n");

		sb.append("Solution cost: ").append(solution.getSolutionCost()).append("\n\n");

		sb.append("Expanded nodes: ").append(solution.getNumberOfExpandedNodes()).append("\n\n");

		sb.append("Border nodes: ").append(solution.getNumberOfBorderNodes()).append("\n\n");

		sb.append("Time: ").append(solution.getProcessingTime()).append("\n\n");

		for(int i = allGames.size() - 1; i >= 0; i--) {
			sb.append(allGames.get(i).toString()).append("\n\n");
		}

		return sb.toString();
	}

}
