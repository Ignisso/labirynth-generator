public class FileBMP
implements FileIO {
	Labirynth maze;
	BMPInfo   BitMap;
	
	public FileIO() {
		this.maze = null;
	}
	
	public void load(Labirynth maze) {
		this.maze = maze;
	}
	
	public void write(String path);
	public void read(String path);
}
