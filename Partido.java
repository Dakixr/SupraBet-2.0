
public class Partido {
	private String nombre;
	private double a,b,c;
	
	public Partido(double a, double b,double c, String nombre) {
		this.nombre=nombre;
		this.a=a;
		this.b=b;
		this.c=c;
		}
	public double getA() {
		return this.a;
	}
	public double getB() {
		return this.b;
	}
	public double getC() {
		return this.c;
	}
	public String getNombre() {
		return this.nombre;
	}
	



}
