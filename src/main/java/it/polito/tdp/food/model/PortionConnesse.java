package it.polito.tdp.food.model;

public class PortionConnesse {
	
	Portion p;
	double peso;
	public PortionConnesse(Portion p, double peso) {
		super();
		this.p = p;
		this.peso = peso;
	}
	public Portion getP() {
		return p;
	}
	public void setP(Portion p) {
		this.p = p;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return  p + " - " + peso ;
	}
	
	

}
