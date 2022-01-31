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

	/**
    * Set cell's parent
    * @param cell parent cell
    */
    public void setParent(Cell cell) {
    	this.parent = cell;
    }

	/**
    * Get cell's parent
    * @return cell's parent
    */
    public Cell getParent() {
    	return this.parent;
    }

	/**
    * Set cell's type
    * @param t new cell type
    */	
	public void setType(CellType t) {
		this.type = t;
	}

	/**
    * Get cell's type
    * @return cell's type
    */
	public CellType getType() {
		return this.type;
	}

	/**
    * Check if cell was visited
    * @return true if cell was visited otherwise returns false
    */
	public boolean isVisited() {
		return this.visited;
	}

	/**
    * Visit a cell
    */
	public void visit() {
		this.visited = true;
	}
	
	/**
    * Unvisit a cell
    */
	public void unvisit() {
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
			case END:
				return "X";
			default:
				return "?";
		}
    }
}
