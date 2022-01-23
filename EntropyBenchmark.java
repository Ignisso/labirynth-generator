public class EntropyBenchmark {
	public static void main(String[] args) {
		int[][] entropy = new int[25][25];
		for(int i = 1; i <= 10000; i++) {
			Labirynth lab = new Labirynth(10, 10);
			lab.generateLabirynth();
			for(int y = 0; y < lab.getHeight(); y++) {
				for(int x = 0; x < lab.getWidth(); x++) {
					if(lab.getCell(x,y).getType() == CellType.WALL) {
						entropy[x][y]++;
					}
				}
			}
		}	
	for(int y = 0; y < 25; y++) {
			for(int x = 0; x < 25; x++) {
				if(entropy[x][y] != 10000 && entropy[x][y] != 0) {
					System.out.print(entropy[x][y] + "\t");
				}
			}   System.out.println();
		}
	}
}