package Fertilizer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Row {
	private String name;
	private String N1, P1, K1;
	private String N2, P2, K2;
	private String N3, P3, K3;
	private String T1, T2, T3;
	public String getN1() {
		return N1;
	}
	public void setN1(String n1) {
		N1 = n1;
	}
	public String getP1() {
		return P1;
	}
	public void setP1(String p1) {
		P1 = p1;
	}
	public String getK1() {
		return K1;
	}
	public void setK1(String k1) {
		K1 = k1;
	}
	public String getN2() {
		return N2;
	}
	public void setN2(String n2) {
		N2 = n2;
	}
	public String getP2() {
		return P2;
	}
	public void setP2(String p2) {
		P2 = p2;
	}
	public String getK2() {
		return K2;
	}
	public void setK2(String k2) {
		K2 = k2;
	}
	public String getN3() {
		return N3;
	}
	public void setN3(String n3) {
		N3 = n3;
	}
	public String getP3() {
		return P3;
	}
	public void setP3(String p3) {
		P3 = p3;
	}
	public String getK3() {
		return K3;
	}
	public void setK3(String k3) {
		K3 = k3;
	}
	public String getT1() {
		return T1;
	}
	public void setT1(String t1) {
		T1 = t1;
	}
	public String getT2() {
		return T2;
	}
	public void setT2(String t2) {
		T2 = t2;
	}
	public String getT3() {
		return T3;
	}
	public void setT3(String t3) {
		T3 = t3;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Row(String n, double n1, double p1, double k1, double n2, double p2, double k2, double n3, double p3, double k3,
			double t1, double t2, double t3) {
		super();
		name = n;
		N1 = Double.toString(round(n1, 2));
		P1 = Double.toString(round(p1, 2));
		K1 = Double.toString(round(k1, 2));
		
		N2 = Double.toString(round(n2, 2));
		P2 = Double.toString(round(p2, 2));
		K2 = Double.toString(round(k2, 2));
		
		N3 = Double.toString(round(n3, 2));
		P3 = Double.toString(round(p3, 2));
		K3 = Double.toString(round(k3, 2));
		
		T1 = Double.toString(round(t1, 2));
		T2 = Double.toString(round(t2, 2));
		T3 = Double.toString(round(t3, 2));
	}
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
