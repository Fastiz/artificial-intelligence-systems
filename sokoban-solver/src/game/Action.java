package game;

public class Action {
	private int[] boxCurrentPosition;
	private int[] boxTargetPosition;
	private int[] playerPosition;
	
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
 }
