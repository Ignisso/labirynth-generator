import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;

public class Labirynth {
	private int     width     = 0;
	private int     height    = 0;
	private Cell    grid[][]  = null;
	private Cell    begin     = null;
	private Cell    end       = null;
	private boolean completed = false;
	
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
	
	/**
    * Clear object
    */
	public void clear() {
		this.width = 0;
		this.height = 0;
		this.grid = null;
		this.begin = null;
		this.end = null;
		this.completed = false;
	}
	/**
    * Unsolve labirynth
    */
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
	/**
    * Unvisit every cell
    */
	public void clearVisits() {
        for(Cell[] row: grid) {
            for(Cell cell: row) {
                cell.unvisit();
            }
        }
	}
	
	/**
	 * Generate labirytnth
	 * @param seed [optional] - generates Labirynth using given seed
	 * @return true if everything went succesfully
	 */
	public boolean generateLabirynth(long seed) {
		Generator mazeGenerator = new GeneratorDFS(this, seed);
		return mazeGenerator.generate();
	}
	
	public boolean generateLabirynth() {
		Generator mazeGenerator = new GeneratorDFS(this, new Random().nextLong());
		return mazeGenerator.generate();
	}
	
	/**
	 * Solve labirynth
	 * @return true if everything went succesfully
	 */
	public boolean solveLabirynth() throws UninitializedDataException{
		Solver mazeSolver = new SolverBFS(this);
		return mazeSolver.solve();
	}
	
	/**
	* Write data from labiryth to bitmap file
	* @return true if everything went succesfully
	*/
	public boolean writeToBitmap(String path) {
		FileIO bmp = new FileBMP(this);
		bmp.load();
		return bmp.write(path);
	}
	
	/**
	* Read data from bitmap into labirynth
	* @return true if everything went succesfully
	*/
	public boolean readFromBitmap(String path) {
		FileIO bmp = new FileBMP(this);
		return bmp.read(path);
	}
	
	/**
	* Write data from labiryth to binary file
	* @return true if everything went succesfully
	*/
	public boolean writeToBinary(String path) {
		FileIO bin = new FileBinary(this);
		bin.load();
		return bin.write(path);
	}
	
	/**
	* Read data from binary file into labirynth
	* @return true if everything went succesfully
	*/
	public boolean readFromBinary(String path) {
		FileIO bin = new FileBinary(this);
		return bin.read(path);
	}
	
	/**
	* Write data from labiryth to text file
	* @return true if everything went succesfully
	*/
	public boolean writeToText(String path) {
		FileIO txt = new FileText(this);
		txt.load();
		return txt.write(path);
	}
	
	/**
	* Read data from text file file into labirynth
	* @return true if everything went succesfully
	*/
	public boolean readFromText(String path) {
		FileIO txt = new FileText(this);
		return txt.read(path);
	}
	
	/**
    * Set labirynth as completed
    */
	public void setCompleted() {
		this.completed = true;
	}
	/**
    * Check if labirynth is solved
    * @return true if labirynth is solved
    */
	public boolean isCompleted() {
		return this.completed;
	}
	/**
    * Set beginning cell of labirynth
    * @param cell beginning cell
    */
	public void setBegin(Cell cell) {
		reset();
		this.begin = cell;
	}
	/**
    * Set beginning cell of labirynth
    * @param x x coordinate
    * @param y y coordinate
    */
	public void setBegin(int x, int y) 
	throws IncorrectCoordsException {
		reset();
		if(0 <= 2*x-1 && 2*x-1 < this.grid[0].length &&
			0 <= 2*y-1 && 2*y-1 < this.grid.length )
		this.begin = this.grid[2 * x - 1][2 * y - 1];
		else {
			throw new IncorrectCoordsException("Incorrect x,y corrds");
		}
	}
	/**
    * Get beginning cell of labirynth
    * @return beginning cell
    */
	public Cell getBegin() {
		return this.begin;
	}
	
	/**
    * Set ending cell of labirynth
    * @param cell ending cell
    */
	public void setEnd(Cell cell) {
		reset();
		this.end = cell;
	}

	/**
    * Set ending cell of labirynth
    * @param x x coordinate
    * @param y y coordinate
    */
	public void setEnd(int x, int y) throws IncorrectCoordsException {
		reset();
		if(0 <= 2*x-1 && 2*x-1 < this.grid[0].length &&
			0 <= 2*y-1 && 2*y-1 < this.grid.length )
			this.end = this.grid[2 * x - 1][2 * y - 1];
		else {
			throw new IncorrectCoordsException("Incorrect x,y corrds");
		}
	}
	/**
    * Get ending cell of labirynth
    * @return ending cell
    */
	public Cell getEnd() {
		return this.end;
	}
	/**
    * Get cell given (x,y)
    * @param x x coord
    * @param y y coord
    * @return asked cell
    * @throws IncorrectCoordsException if x or y isnt valid
    */
	public Cell getCell(int x, int y) throws IncorrectCoordsException {
		if (x >= this.width || y >= this.height)
			throw new IncorrectCoordsException("X or Y coord is not valid");
		return this.grid[x][y];
	}
	/**
    * Get cell based on other cell and direction
    * @param cell starting cell
    * @param direction direction 
    * @param distance distance between cells
    * @return asked cell
    * @throws IncorrectCoordsException if x or y isnt valid
    */
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
	/**
    * Set ending cell of labirynth
    * @param width width of labirynth
    * @param height height of labirynth
    * @return true if everything went succesfully
    * @throws IncorrectSizeException if width or height is bigger than 2048
    */
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
	/**
    * Set grid of labirynth
    * @param cell grid
    * @return true if everything went succesfully
    * @throws NullPointerException no data to set
    * @throws ArrayIndexOutOfBoundsException grids have different sizes
    */
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
	/**
    * Get labirynth's width
    * @return asked cell
    */
	public int getWidth() {
		return this.width;
	}
	/**
    * Get labirynth's height
    * @return asked cell
    */
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
