package back.game;

public enum CellTypeEnum {
	WALL, BOX, EMPTY, PLAYER, GOAL, GOALANDBOX, PLAYERANDGOAL;
	
	public static boolean movableCell(CellTypeEnum cell) {
		if(cell == EMPTY || cell == GOAL) {
			return true;
		}else {
			return false;	
		}
	}

}
