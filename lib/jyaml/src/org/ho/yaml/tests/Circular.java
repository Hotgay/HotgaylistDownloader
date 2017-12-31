package org.ho.yaml.tests;

public class Circular {
	int i;
	Circular other;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public Circular getOther() {
		return other;
	}
	public void setOther(Circular other) {
		this.other = other;
	}
}
