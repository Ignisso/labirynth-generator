public class BMPInfo {
	private class RGB {
		byte rgbBlue;
		byte rgbGreen;
		byte rgbRed;
		
		public static int SIZE = 3;
		public RGB() {
			this.rgbBlue  = 0;
			this.rgbGreen = 0;
			this.rgbRed   = 0;
		}
		
		public void set(int color) {
			this.rgbBlue = (byte)(color);
			this.rgbGreen = (byte)(color >> 8);
			this.rgbRed = (byte)(color >> 16);
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%02X", rgbBlue));
			sb.append(String.format("%02X", rgbGreen));
			sb.append(String.format("%02X", rgbRed));
			return sb.toString();
		}
	}
	
	private BMPHeader header;
	private RGB[] bitmap;
	
	BMPInfo() {
		this.header = null;
		this.bitmap = null;
	}
	
	public void setInfo(int width, int height) {
		this.header = new BMPHeader();
		this.header.setHeader(width, height);
		int size = (width + 1) * height;
		System.out.println(size);
		this.bitmap = new RGB[size];
		for (int i = 0; i < size; i++)
			this.bitmap[i] = new RGB();
	}
	
	public void setPixel(int x, int y, int color) {
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		this.bitmap[offset].set(color);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.header.toString());
		for (RGB pixel : this.bitmap)
			sb.append(pixel);
		return sb.toString();
	}
}
