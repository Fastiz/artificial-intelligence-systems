package back.game;

import java.util.Arrays;

public class Action {
	private int[] boxCurrentPosition;
	private int[] boxTargetPosition;
	private int[] playerPosition;
	//TODO
	private int actionCost = 1;

	public Action(int[] boxCurrentPosition, int[] boxTargetPosition, int[] playerPosition) {
		this.boxCurrentPosition = boxCurrentPosition;
		this.boxTargetPosition = boxTargetPosition;
		this.playerPosition = playerPosition;
	}

	public int[] getBoxCurrentPosition() {
		return this.boxCurrentPosition;
	}

	public int[] getBoxTargetPosition() {
		return this.boxTargetPosition;
	}

	public int[] getPlayerPosition() {
		return this.playerPosition;
	}

	public int getActionCost() {
		return actionCost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + actionCost;
		result = prime * result + Arrays.hashCode(boxCurrentPosition);
		result = prime * result + Arrays.hashCode(boxTargetPosition);
		result = prime * result + Arrays.hashCode(playerPosition);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (actionCost != other.actionCost)
			return false;
		if (!Arrays.equals(boxCurrentPosition, other.boxCurrentPosition))
			return false;
		if (!Arrays.equals(boxTargetPosition, other.boxTargetPosition))
			return false;
		if (!Arrays.equals(playerPosition, other.playerPosition))
			return false;
		return true;
	}
}