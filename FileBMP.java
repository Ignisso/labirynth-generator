import java.io.File;
import java.io.FileOutputStream;

public class FileBMP
implements FileIO {
	Labirynth maze;
	BMPInfo   bitmap;
	
	public FileBMP() {
		this.maze   = null;
		this.bitmap = null;
	}
	
	public void load(Labirynth maze) {
		this.maze = maze;
		this.bitmap = new BMPInfo();
		int width = this.maze.getWidth();
		int height = this.maze.getHeight();
		this.bitmap.setInfo(width, height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (this.maze.getCell(i, j).getType() == CellType.WALL)
					this.bitmap.setPixel(i, j, 0x000000);
				else if (this.maze.getCell(i, j).getType() == CellType.CORRECT)
					this.bitmap.setPixel(i, j, 0x00ff00);
				else
					this.bitmap.setPixel(i, j, 0xffffff);
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
			streamout.write(this.bitmap.getBitmap());
		}
		catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	public void read(String path) {
		
	}
}
