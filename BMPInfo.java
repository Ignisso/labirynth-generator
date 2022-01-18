public class BMPInfo {
	private BMPHeader header;
	private RGB[]     bitmap;
	
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
	
	public void setPixelAt(int x, int y, int color) {
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		this.bitmap[offset].setColor(color);
	}
	
	public int getPixelAt(int x, int y) {
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		return this.bitmap[offset].getColor();
	}

	public byte[] getBytes() {
		int size = BMPHeader.SIZE + this.bitmap.length * 3;
		byte[] data = new byte[size];
		for (int i = 0; i < BMPHeader.SIZE; i++) {
			data[i] = this.header.getData()[i];
		}
		int offset = BMPHeader.SIZE;
		for (RGB p : this.bitmap) {
			p.setBytes(data, offset);
			offset += 3;
		}
		return data;
	}

	public short getWidth() {
		return this.header.getWidth();
	}
	
	public short getHeight() {
		return this.header.getHeight();
	}

	public void loadHeader(byte[] b) {
		this.header = new BMPHeader();
		this.header.load(b);
		this.setInfo(header.getWidth(), header.getHeight());
	}

	public void load(byte[] b) {
		int size = (this.header.getWidth() + 1) * this.header.getHeight();
		for (int i = 0; i < size; i++) {
			int color = 0;
			color |= (b[BMPHeader.SIZE + i * 3 + 2] & 0xff);
			color <<= 8;
			color |= (b[BMPHeader.SIZE + i * 3 + 1] & 0xff);
			color <<= 8;
			color |= (b[BMPHeader.SIZE + i * 3] & 0xff);
			this.bitmap[i].setColor(color);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.header.toString());
		for (RGB pixel : this.bitmap)
			sb.append(pixel);
		return sb.toString();
	}
}
