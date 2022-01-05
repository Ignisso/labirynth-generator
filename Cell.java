public class Cell {
	public final int x;
	public final int y;
	private boolean  visited = false;
	private CellType type    = CellType.WALL;

    public Cell(int x, int y, CellType t) {
		this.x = x;
		this.y = y;
		this.type = t;
    }
	
	public void setType(CellType t) {
		this.type = t;
	}
	
	public boolean isVisited() {
		return this.visited;
	}
	
	public void visit() {
		this.visited = true;
	}
	
	public void reset() {
		this.visited = false;
	}

    @Override
    public String toString() {
        switch(this.type) {
			case WALL:
				return "0";
			case ROUTE:
				return "1";
			case CORRECT:
				return "2";
			default:
				return "?";
		}
    }
}