public class BMPHeader {
	public static int SIZE = 26;
	/*
	struct BITMAPFILEHEADER
	sizeof 14
	*/
	private short bfType;
	private int   bfSize;
	private short bfReserved1;
	private short bfReserved2;
	private int   bfOffBits;
	/*
	struct BITMAPCOREHEADER
	sizeof 12
	*/
	private int   bcSize;
	private short bcWidth;
	private short bcHeight;
	private short bcPlanes;
	private short bcBitCount;
	/* data bytes */
	byte[] data = new byte[SIZE];
	
	
	
	public BMPHeader() {
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
	
	public void setHeader(int width, int height) {
		this.bfSize   = SIZE + width * height * 3 + height * 3; // 3 bytes per pixel  + 3 bytes line end
		this.bcWidth  = (short)width;
		this.bcHeight = (short)height;
		
		data[2]  = (byte)(bfSize);
		data[3]  = (byte)(bfSize >> 8);
		data[4]  = (byte)(bfSize >> 16);
		data[5]  = (byte)(bfSize >> 24);
		data[18] = (byte)(bcWidth);
		data[19] = (byte)(bcWidth >> 8);
		data[20] = (byte)(bcHeight);
		data[21] = (byte)(bcHeight >> 8);
	}
	
	public short getWidth() {
		return this.bcWidth;
	}
	
	public short getHeight() {
		return this.bcHeight;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte b : this.data)
			sb.append(String.format("%02X", b));
		return sb.toString();
	}
}
