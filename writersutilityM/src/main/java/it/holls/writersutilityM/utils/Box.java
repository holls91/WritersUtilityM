package it.holls.writersutilityM.utils;

import java.util.function.UnaryOperator;

public class Box<T> {
	private T data;
	public Box(T data) {
		this.data = data;
	}
	public T getData() {
		return data;
	}
	public void update(UnaryOperator<T> action) {
		data = action.apply(data);
	}
}
