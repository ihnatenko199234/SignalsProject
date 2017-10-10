package common;
public abstract class Signal {
	protected int f; //ilosc probek
	protected double A; //amplituda
	protected int t1; //czas poczatkowy
	protected double d; // signal duration 
	
	/**
	 * Constructor for this class.
	 * @param f - amount of samples used to mimic continuity of the function
	 * @param A - amplitude
	 * @param t1 - start time
	 * @param d - duration
	 */
	public Signal(double A, int t1, int f, double d) {
		this.f = f;
		this.A = A;
		this.t1 = t1;
		this.d = d;
	}
	
	/**
	 * Generates 2-dimentional Vector with function values 
	 * @return
	 */
	abstract protected double[][] generateSignal();
}
