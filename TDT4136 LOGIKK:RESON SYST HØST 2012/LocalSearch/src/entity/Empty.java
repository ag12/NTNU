package entity;
public class Empty {

	public int Row;
	public int Column;
	public int Attacks;

	
	public Empty(int row, int column) {
		Row = row;
		Column = column;
	}
	public Empty(int row, int column, int attacks) {
		Row = row;
		Column = column;
		Attacks = attacks;
	}


	public String toAttack(){
		return  "   a:" + Attacks + " " ;//" E, (" + Row + ", " + Column + ") A:" + Attacks + " " ;
	}

	public String toPuzzle() {
		return "Empty [Row=" + Row + ", Column=" + Column + "]";
	}

	@Override
	public String toString() {
		return "[ E ] (" + Row + ", " + Column +  " )" + Attacks  ;
	}
}
