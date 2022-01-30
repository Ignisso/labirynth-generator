import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;

public class Labirynth {
	private int     width     = 0;
	private int     height    = 0;
	private Cell    grid[][]  = null;
	private Cell    begin     = null;
	private Cell    end       = null;
	private boolean completed = false;
	
	/**
	 * Constructor for class Labirynth
	 * @param width - width of the Labirynth in vertices
	 * @param height - height of the Labirynth in vertices
	 */
	public Labirynth(int width, int height) {
		this.width = 2 * width - 1;
		this.height = 2 * height - 1;
	
		this.grid = new Cell[this.width][this.height];
	
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}
	
		this.begin = this.grid[1][1];
		this.begin.setType(CellType.ROUTE);
	}
	
	public Labirynth() {
	
	}
	
	public void clear() {
		this.width = 0;
		this.height = 0;
		this.grid = null;
		this.begin = null;
		this.end = null;
		this.completed = false;
	}
	
	public void reset() {
		if (this.completed == false)
			return;
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.grid[i][j].getType() == CellType.CORRECT ||
					this.grid[i][j].getType() == CellType.END)
					this.grid[i][j].setType(CellType.ROUTE);
			}
		}
		this.completed = false;
	}
	
	public void clearVisits() {
        for(Cell[] row: grid) {
            for(Cell cell: row) {
                cell.unvisit();
            }
        }
	}
	
	/**
	 *  Generates Labirytnth
	 *  @param seed [optional] - generates Labirynth using gives seed
	 */
	public void generateLabirynth(long seed) {
		Generator mazeGenerator = new GeneratorDFS(this, seed);
		mazeGenerator.generate();
	}
	
	public void generateLabirynth() {
		Generator mazeGenerator = new GeneratorDFS(this, new Random().nextLong());
		mazeGenerator.generate();
	}
	
	/**
	 * Solves Labirynth
	 */
	public void solveLabirynth() {
		Solver mazeSolver = new SolverBFS(this);
		mazeSolver.solve();
	}
	
	/**
	* Write data from Labiryth to bitmap file
	*/
	public void writeToBitmap(String path) {
		FileIO bmp = new FileBMP(this);
		bmp.load();
		bmp.write(path);
	}
	
	/**
	* Read data from bitmap into Labirynth
	*/
	public void readFromBitmap(String path) {
		FileIO bmp = new FileBMP(this);
		bmp.read(path);
	}
	
	/**
	* Write data from Labiryth to binary file
	*/
	public void writeToBinary(String path) {
		FileIO bin = new FileBinary(this);
		bin.load();
		bin.write(path);
	}
	
	/**
	* Read data from binary file into Labirynth
	*/
	public void readFromBinary(String path) {
		FileIO bin = new FileBinary(this);
		bin.read(path);
	}
	
	/**
	* Write data from Labiryth to text file
	*/
	public void writeToText(String path) {
		FileIO txt = new FileText(this);
		txt.load();
		txt.write(path);
	}
	
	/**
	* Read data from text file file into Labirynth
	*/
	public void readFromText(String path) {
		FileIO txt = new FileText(this);
		txt.read(path);
	}
	
	/**
	 * Getters and Setters
	 */
	public void setCompleted() {
		this.completed = true;
	}
	
	public boolean isCompleted() {
		return this.completed;
	}
	
	public void setBegin(Cell cell) {
		reset();
		this.begin = cell;
	}
	
	public void setBegin(int x, int y) {
		reset();
		this.begin = this.grid[2 * x - 1][2 * y - 1];
	}
	
	public Cell getBegin() {
		return this.begin;
	}
	
	public void setEnd(Cell cell) {
		reset();
		this.end = cell;
	}

	public void setEnd(int x, int y) {
		reset();
		this.end = this.grid[2 * x - 1][2 * y - 1];
	}
	
	public Cell getEnd() {
		return this.end;
	}
	
	public Cell getCell(int x, int y) throws IncorrectCoordsException {
		if (x >= this.width || y >= this.height)
			throw new IncorrectCoordsException("X or Y coord is not valid");
		return this.grid[x][y];
	}

	public Cell getCell(Cell v, int direction, int distance) throws IncorrectCoordsException {
    	Cell u = null;
    	switch(direction) {
			case Direction.UP:
				if(v.y >= distance){
					u = this.getCell(v.x, v.y - distance);
				}
			break;

			case Direction.RIGHT:
				if(width - distance > v.x){
					u = this.getCell(v.x + distance, v.y);
				}
			break;

			case Direction.DOWN:
				if(height - distance > v.y){
					u = this.getCell(v.x, v.y + distance);
				}
			break;

			case Direction.LEFT:
				if(v.x >= distance){
					u = this.getCell(v.x - distance, v.y);
				}
			break;
		}
		return u;
    }
	
	public boolean setSize(int width, int height) throws IncorrectSizeException {
		if (width > 2048 || height > 2048)
			throw new IncorrectSizeException("Width or height is out of 2048");
		this.width = width;
		this.height = height;
		this.grid = new Cell[this.width][this.height];
	
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = new Cell(i, j, CellType.WALL);
		}
		return true;
	}
	
	public boolean setGrid(Cell arr[][]) {
		if (this.grid == null || arr == null)
			throw new NullPointerException("No data to set");
		if (this.grid.length != arr.length)
			throw new ArrayIndexOutOfBoundsException("Grids are not equal");
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++)
				this.grid[i][j] = arr[i][j];
		}
		return true;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	@Override
		public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				str.append(this.grid[j][i]);
			}
			str.append("\n");
		}
		return str.toString();
	}
}
