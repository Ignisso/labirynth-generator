import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class FileBinary
implements FileIO {
	Labirynth maze;
	byte[] data;
	
	public static final byte WALL_B = 0x00;
	public static final byte ROUTE_B = 0x01;
	public static final byte CORRECT_B = 0x02;
	public static final byte END_B = 0x03;
	
	public FileBinary(Labirynth maze) {
		this.maze = maze;
	}
	
	public void load() {
		int change = 1;
		if (maze.isCompleted())
			change = 2;
		int size = 40 + change * (maze.getWidth() * maze.getHeight());
		this.data = new byte[size / 8 + 1];
		this.data[0] = (byte)(maze.getWidth() >> 8);
		this.data[1] = (byte)maze.getWidth();
		this.data[2] = (byte)(maze.getHeight() >> 8);
		this.data[3] = (byte)maze.getHeight();
		
		if (maze.isCompleted())
			this.data[4] = 0x01;
		int offset = 40;
		for (int i = 0; i < maze.getHeight(); i++) {
			for (int j = 0; j < maze.getWidth(); j++) {
				int shift = 8 - change - offset % 8;
				byte d = 0x00;
				switch(this.maze.getCell(j, i).getType()) {
					case WALL:
						d = WALL_B;
						break;
					case ROUTE:
						d = ROUTE_B;
						break;
					case CORRECT:
						d = CORRECT_B;
						break;
					case END:
						d = END_B;
						break;
				}
				this.data[offset / 8] |= (d << shift);
				offset += change;
			}
		}
	}
	
	public void write(String path) {
		File fileout = new File(path);
		FileOutputStream streamout;
		try {
			streamout = new FileOutputStream(fileout);
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			streamout.write(this.data);
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public void read(String path) {
		File filein = new File(path);
		FileInputStream streamin;
		try {
			streamin = new FileInputStream(filein);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			return;
		}
		try {
			this.data = new byte[1024 * 1024 * 64]; // 64 MB
			streamin.read(this.data);
			int width = 0;
			width |= this.data[0];
			width <<= 8;
			width |= (this.data[1] & 0xff);
			int height = 0;
			height |= this.data[2];
			height <<= 8;
			height |= (this.data[3] & 0xff);
			this.maze.setSize(width, height);
			int change = 1;
			if (this.data[4] != 0x00) {
				this.maze.setCompleted();
				change = 2;
			}
			
			
			int offset = 40;
			Cell grid[][] = new Cell[width][height];
			for (int j = 0; j < height; j++) {
				for (int i = 0; i < width; i++) {
					int shift = 8 - change - offset % 8;
					byte b = (byte)(this.data[offset / 8] >> shift);
					b &= 0x03;
					if (this.data[4] == 0x00)
						b &= 0x01;
					switch(b) {
						case WALL_B:
							grid[i][j] = new Cell(i, j, CellType.WALL);
							break;
						case ROUTE_B:
							grid[i][j] = new Cell(i, j, CellType.ROUTE);
							break;
						case CORRECT_B:
							grid[i][j] = new Cell(i, j, CellType.CORRECT);
							break;
						case END_B:
							grid[i][j] = new Cell(i, j, CellType.END);
							break;
					}
					offset += change;
				}
			}
			maze.setGrid(grid);
		}
		catch (IOException e) {
			System.out.println(e.getStackTrace());
			return;
		}
	}
}
