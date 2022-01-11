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
		
		public void setBytes(byte[] data, int offset) {
			data[offset]     = this.rgbBlue;
			data[offset + 1] = this.rgbGreen;
			data[offset + 2] = this.rgbRed;
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
		this.bitmap = new RGB[size];
		for (int i = 0; i < size; i++)
			this.bitmap[i] = new RGB();
	}
	
	public void setPixel(int x, int y, int color) {
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		this.bitmap[offset].set(color);
	}
	
	public byte[] getBitmap() {
		int size = 26 + this.bitmap.length * 3;
		byte[] data = new byte[size];
		for (int i = 0; i < 26; i++) {
			data[i] = this.header.getData()[i];
		}
		int offset = 26;
		for (RGB p : this.bitmap) {
			p.setBytes(data, offset);
			offset += 3;
		}
		return data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.header.toString());
		for (RGB pixel : this.bitmap)
			sb.append(pixel);
		return sb.toString();
	}
}
