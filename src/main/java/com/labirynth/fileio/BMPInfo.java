package com.labirynth.fileio;

import com.labirynth.core.*;

public class BMPInfo {
	private BMPHeader header;
	private RGB[]     bitmap;
	
	BMPInfo() {
		this.header = null;
		this.bitmap = null;
	}

	/**
    * Set header of bitmap file
    * @param width width of the labirynth
    * @param height height of the labirynth
    * @return true if everything went succesfully
    * @throws IncorrectSizeException if tried to pass wrong width and height
    * @throws UninitializedDataException if bitmap header wasn't created
    */
	public boolean setInfo(int width, int height) {
		this.header = new BMPHeader();
		try {
			this.header.setHeader(width, height);
		} catch (IncorrectSizeException e) {
			System.err.println("Wrong width or height");
			return false;
		} catch (UninitializedDataException e) {
			System.err.println("Bitmap header was not created");
			return false;
		} 
		final int size = (width + 1) * height;
		this.bitmap = new RGB[size];
		for (int i = 0; i < size; i++)
			this.bitmap[i] = new RGB();
		return true;
	}

	/**
    * Set color of pixel at cords (x,y)
    * @param x x coordinate
    * @param y y coordinate
    * @param color pixel color
    * @return true if everything went succesfully
    * @throws UninitializedDataException if header wasn't initialized
    * @throws IncorrectCoordsException if x or y coordinate is invalid
    * @throws ArrayIndexOutOfBoundsException if bitmap is too small
    */
	public boolean setPixelAt(int x, int y, int color)
	throws UninitializedDataException, IncorrectCoordsException {
		if (this.header == null || this.bitmap == null)
			throw new UninitializedDataException("Header of bitmap uninitialized");
		if (x >= header.getWidth() || y >= header.getHeight())
			throw new IncorrectCoordsException("X or Y coords is not valid");
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		if (offset >= this.bitmap.length)
			throw new ArrayIndexOutOfBoundsException("Not enough bitmap memory");
		this.bitmap[offset].setColor(color);
		return true;
	}
	
	/**
    * Get color of pixel at cords (x,y)
    * @param x x coordinate
    * @param y y coordinate
    * @return color of pixel at coords (x,y)
    * @throws UninitializedDataException if header wasn't initialized
    * @throws IncorrectCoordsException if x or y coordinate is invalid
    * @throws ArrayIndexOutOfBoundsException if bitmap is too small
    */
	public int getPixelAt(int x, int y)
	throws UninitializedDataException, IncorrectCoordsException {
		if (this.header == null)
			throw new UninitializedDataException("Header of bitmap uninitialized");
		if (x >= header.getWidth() || y >= header.getHeight())
			throw new IncorrectCoordsException("X or Y coords is not valid");
		int offset = (header.getWidth() + 1) * (header.getHeight() - y - 1) + x;
		if (offset >= this.bitmap.length)
			throw new ArrayIndexOutOfBoundsException("Not enough bitmap memory");
		return this.bitmap[offset].getColor();
	}
	/**
    * Get bitmap data
    * @return array containing bitmap data
    * @throws NullPointerException if data couldn't be initialized
    * @throws ArrayIndexOutOfBoundsException if exceeded array size
    */
	public byte[] getBytes() {
		int size = BMPHeader.SIZE + this.bitmap.length * 3;
		byte[] data = new byte[size];
		if (data == null)
			throw new NullPointerException("Could not allocate memory");
		for (int i = 0; i < BMPHeader.SIZE; i++) {
			data[i] = this.header.getData()[i];
		}
		int offset = BMPHeader.SIZE;
		for (RGB p : this.bitmap) {
			try {
				p.setBytes(data, offset);
			} catch (NullPointerException e) {
				System.err.println("Data could not be initialized");
				return null;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("An error occured during data initialization");
				return null;
			}
			offset += 3;
		}
		return data;
	}

	/**
    * Get data width
    * @return width of data
    */
	public short getWidth() {
		return this.header.getWidth();
	}
	
	/**
    * Get data height
    * @return height of data
    */
	public short getHeight() {
		return this.header.getHeight();
	}

	/**
    * Load bitmap header
    * @param b data to load
    * @return true if everything went succesfully
    * @throws NullPointerException if header is null
    * @throws IncorrectDataFormatException if not enough data
    */
	public boolean loadHeader(byte[] b) {
		if (b == null)
			throw new NullPointerException("Can not load header from null");
		this.header = new BMPHeader();
		try {
			this.header.load(b);
		} catch (NullPointerException e) {
			System.err.println("Can not load bitmap header from null");
			return false;
		} catch (IncorrectDataFormatException e) {
			System.err.println("Too few data");
			return false;
		}
		this.setInfo(header.getWidth(), header.getHeight());
		return true;
	}
	/**
    * Load bitmap
    * @param b data to load
    * @return true if everything went succesfully
    * @throws UninitializedDataException if header isn't initialized
    * @throws NullPointerException if data is null
    * @throws IncorrectDataFormatException if not enough data
    */
	public boolean load(byte[] b)
	throws UninitializedDataException, IncorrectDataFormatException {
		if (this.header == null)
			throw new UninitializedDataException("Header of bitmap uninitialized");
		if (b == null)
			throw new NullPointerException("Read data from null");
		int size = (this.header.getWidth() + 1) * this.header.getHeight();
		if (b.length < BMPHeader.SIZE + size)
			throw new IncorrectDataFormatException("Too few bytes to read");
		for (int i = 0; i < size; i++) {
			int color = 0;
			color |= (b[BMPHeader.SIZE + i * 3 + 2] & 0xff);
			color <<= 8;
			color |= (b[BMPHeader.SIZE + i * 3 + 1] & 0xff);
			color <<= 8;
			color |= (b[BMPHeader.SIZE + i * 3] & 0xff);
			this.bitmap[i].setColor(color);
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.header.toString());
		for (RGB pixel : this.bitmap)
			sb.append(pixel);
		return sb.toString();
	}
}
