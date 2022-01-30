import java.lang.NullPointerException;

public class BMPHeader {
	public static final int SIZE = 26;
	/*
	struct BITMAPFILEHEADER
	sizeof 14
	*/
	private short  bfType;
	private int    bfSize;
	private short  bfReserved1;
	private short  bfReserved2;
	private int    bfOffBits;
	/*
	struct BITMAPCOREHEADER
	sizeof 12
	*/
	private int    bcSize;
	private short  bcWidth;
	private short  bcHeight;
	private short  bcPlanes;
	private short  bcBitCount;
	/* data bytes */
	private byte[] data;
	
	
	
	public BMPHeader() {
		this.data        = new byte[SIZE];
		this.bfType      = 0x424D; // BM
		this.bfReserved1 = 0; // reserved must be 0
		this.bfReserved2 = 0; // reserved must be 0
		this.bfOffBits   = SIZE; // 26 bytes from the begining to the bitmap bits
		this.bcSize      = 12; // bytes required by this structure
		this.bcPlanes    = 1; // must be 1
		this.bcBitCount  = 24; // 24-bit bitmap
		
		data[0]  = (byte)(bfType >> 8);
		data[1]  = (byte)(bfType);
		data[6]  = (byte)(bfReserved1 >> 8);
		data[7]  = (byte)(bfReserved1);
		data[8]  = (byte)(bfReserved2 >> 8);
		data[9]  = (byte)(bfReserved2);
		data[10] = (byte)(bfOffBits);
		data[11] = (byte)(bfOffBits >> 8);
		data[12] = (byte)(bfOffBits >> 16);
		data[13] = (byte)(bfOffBits >> 24);
		data[14] = (byte)(bcSize);
		data[15] = (byte)(bcSize >> 8);
		data[16] = (byte)(bcSize >> 16);
		data[17] = (byte)(bcSize >> 24);
		data[22] = (byte)(bcPlanes);
		data[23] = (byte)(bcPlanes >> 8);
		data[24] = (byte)(bcBitCount);
		data[25] = (byte)(bcBitCount >> 8);
	}
	
	public boolean setHeader(int width, int height)
	throws IncorrectSizeException, UninitializedDataException {
		if (width < 0 || height < 0)
			throw new IncorrectSizeException("Width or height can not be negative");
		this.bfSize   = SIZE + width * height * 3 + height * 3; // 3 bytes per pixel  + 3 bytes line end
		this.bcWidth  = (short)width;
		this.bcHeight = (short)height;
		
		if (data == null)
			throw new UninitializedDataException("Data is null pointing");
		data[2]  = (byte)(bfSize);
		data[3]  = (byte)(bfSize >> 8);
		data[4]  = (byte)(bfSize >> 16);
		data[5]  = (byte)(bfSize >> 24);
		data[18] = (byte)(bcWidth);
		data[19] = (byte)(bcWidth >> 8);
		data[20] = (byte)(bcHeight);
		data[21] = (byte)(bcHeight >> 8);
		return true;
	}
	
	public short getWidth() {
		return this.bcWidth;
	}
	
	public short getHeight() {
		return this.bcHeight;
	}
	
	public byte[] getData() {
		return this.data;
	}

	public boolean load(byte[] b)
	throws IncorrectDataFormatException {
		if (b == null)
			throw new NullPointerException("Data is null pointing");
		if (b.length < 26)
			throw new IncorrectDataFormatException("Data is not valid");
		
		this.bcWidth = 0;
		this.bcWidth |= (b[19] & 0x00ff);
		this.bcWidth <<= 8;
		this.bcWidth |= (b[18] & 0x00ff);
		this.bcHeight = 0;
		this.bcHeight |= (b[21] & 0x00ff);
		this.bcHeight <<= 8;
		this.bcHeight |= (b[20] & 0x00ff);
		
		try {
			setHeader(bcWidth, bcHeight);
		} catch (UninitializedDataException e) {
			System.err.println("Could not set bitmap header, memory is not allocated");
			return false;
		} catch (IncorrectSizeException e) {
			System.err.println("Wrong width or height");
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte b : this.data)
			sb.append(String.format("%02X", b));
		return sb.toString();
	}
}
