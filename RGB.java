public class RGB {
	byte rgbBlue;
	byte rgbGreen;
	byte rgbRed;
	
	public static final int SIZE = 3;
	public RGB() {
		this.rgbBlue  = 0;
		this.rgbGreen = 0;
		this.rgbRed   = 0;
	}
	
	public void setColor(int color) {
		this.rgbBlue = (byte)(color);
		this.rgbGreen = (byte)(color >> 8);
		this.rgbRed = (byte)(color >> 16);
	}

	public int getColor() {
		int value = 0;
		value |= (this.rgbRed & 0xff);
		value <<= 8;
		value |= (this.rgbGreen & 0xff);
		value <<= 8;
		value |= (this.rgbBlue & 0xff);
		return value;
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
