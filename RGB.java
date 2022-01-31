import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.NullPointerException;

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
	
	/**
    * Set color
    * @param color RGB value
    */
	public void setColor(int color) {
		this.rgbBlue = (byte)(color);
		this.rgbGreen = (byte)(color >> 8);
		this.rgbRed = (byte)(color >> 16);
	}
	/**
    * Get color
    * @return RGB value
    */
	public int getColor() {
		int value = 0;
		value |= (this.rgbRed & 0xff);
		value <<= 8;
		value |= (this.rgbGreen & 0xff);
		value <<= 8;
		value |= (this.rgbBlue & 0xff);
		return value;
	}
	/**
    * Set color to data array 
    * @param data data to set
    * @param offset offset
    * @return true if everything went succesfully
    */
	public boolean setBytes(byte[] data, int offset) {
		if (data == null)
			throw new NullPointerException("Data is null pointing");
		if (offset < 0 || data.length < offset + 2)
			throw new ArrayIndexOutOfBoundsException("Offset out of scope");
		data[offset]     = this.rgbBlue;
		data[offset + 1] = this.rgbGreen;
		data[offset + 2] = this.rgbRed;
		return true;
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
