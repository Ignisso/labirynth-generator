package com.labirynth.core;

public class UninitializedDataException
extends Exception {
	public UninitializedDataException(String errorMessage) {
		super(errorMessage);
	}
}
