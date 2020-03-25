package game;

public enum CellTypeEnum {
	WALL, BOX, EMPTY, PLAYER, GOAL;
	
	public static boolean movableCell(CellTypeEnum cell) {
		if(cell == EMPTY || cell == GOAL || cell == PLAYER) {
			return true;
		}else {
			return false;	
		}
	}
}
