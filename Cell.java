public class Cell {
	public final int x;
	public final int y;
	private boolean  visited = false;
	private CellType type    = CellType.WALL;
	private Cell     parent  = null;

    public Cell(int x, int y, CellType t) {
		this.x = x;
		this.y = y;
		this.type = t;
    }

    public void setParent(Cell cell) {
    	this.parent = cell;
    }

    public Cell getParent() {
    	return this.parent;
    }	
	
	public void setType(CellType t) {
		this.type = t;
	}

	public CellType getType() {
		return this.type;
	}
	
	public CellType getType() {
		return this.type;
	}
	
	public boolean isVisited() {
		return this.visited;
	}
	
	public void visit() {
		this.visited = true;
	}

	public void unvisit() {
		this.visited = false;
	}
	
	public void reset() {
		this.visited = false;
	}

    @Override
    public String toString() {
        switch(this.type) {
			case WALL:
				return "#";
			case ROUTE:
				return " ";
			case CORRECT:
				return ".";
			default:
				return "?";
		}
    }
}
