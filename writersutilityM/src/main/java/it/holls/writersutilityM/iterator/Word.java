package it.holls.writersutilityM.iterator;

import java.util.Comparator;

public class Word {
	private String parola;
	private int position;
	private int length;
	private double jaro;
	private String color;

	// per invertire l'ordine: <comparator>.reversed();
	public static final Comparator<Word> BY_POSITION = (p1,p2)->Integer.compare(p1.position,p2.position);
	public static final Comparator<Word> BY_VALUE = (p1,p2)->p1.parola.compareTo(p2.parola);
	public static final Comparator<Word> BY_VALUE_CASE_INSENSITIVE = (p1,p2)->p1.parola.compareToIgnoreCase(p2.parola);
	public static final Comparator<Word> BY_JARO = (p1,p2)->Double.compare(p1.jaro,p2.jaro);

	public Word(String parola, int position) {
		this.parola = parola;
		this.position = position;
		this.length = parola.length();
	}
	public String getParola() {
		return this.parola;
	}
	public void setParola(String parola) {
		this.parola = parola;
		this.length = parola.length();
	}
	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getLength() {
		return this.length;
	}
	public double getJaro() {
		return this.jaro;
	}
	public void setJaro(double jaro) {
		this.jaro = jaro;
	}
	public String getColor() {
		return this.color;
	}
	public void setColor(String color) {
		this.color = color;
	}

}
