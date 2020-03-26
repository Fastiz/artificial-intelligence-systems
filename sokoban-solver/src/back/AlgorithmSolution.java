package back;

import java.util.List;

import back.game.Game;

public class AlgorithmSolution {
	private String parameters;
	private boolean goalFound;
	private int solutionDepth;
	private int solutionCost;
	private int numberOfExpandedNodes;
	private int numberOfBorderNodes;
	private List<Game> solutionStates;
	private float processingTime;
	
	public AlgorithmSolution (String parameters, boolean goalFound, int solutionDepth, int solutionCost, 
			int numberOfExpandedNodes, int numberOfBorderNodes, List<Game> solutionStates, float processingTime) {
		this.parameters = parameters;
		this.goalFound = goalFound;
		this.solutionDepth = solutionDepth;
		this.solutionCost = solutionCost;
		this.numberOfExpandedNodes = numberOfExpandedNodes;
		this.numberOfBorderNodes = numberOfBorderNodes;
		this.solutionStates = solutionStates;
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
		this.solutionStates = solutionStates;
	}

	public float getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(float processingTime) {
		this.processingTime = processingTime;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	
	
}
