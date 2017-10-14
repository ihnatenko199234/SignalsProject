package common;
public abstract class Signal {
	protected int f; //ilosc probek
	protected double A; //amplituda
	protected int t1; //czas poczatkowy
	protected double d; // signal duration 
	protected String name;
	protected boolean composite = false; // type of signal
	
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
	abstract public double[][] generateSignal();
	public String getName() {
		return name;		
	}
	public boolean isComposite() {
		return composite;
	}

	public int getF() {
		return f;
	}

	public double getA() {
		return A;
	}

	public int getT1() {
		return t1;
	}

	public double getD() {
		return d;
	}
	
	
}
