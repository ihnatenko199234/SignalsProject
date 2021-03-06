package common;
import java.io.Serializable;

public abstract class Signal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3491374478554105862L;
	protected int f; //ilosc probek (częstotliwosc probkowania???)  - is serializable
	protected double A; //amplituda
	protected int t1; //czas poczatkowy  - is serializable
	protected double d; // signal duration 
	protected String name;
	protected String valuesType = "real"; // type of signal   - is serializable
	
	protected double[][] values; //  - is serializable
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
	public Signal() {
		
	}
	
	/**
	 * Generates 2-dimentional Vector with function values 
	 * @return
	 */
	abstract public double[][] generateSignal();
	public String getName() {
		return name;		
	}
	public void setName(String name) {
		this.name = name;	
	}
	public boolean isImaginary() {
		if(valuesType.equals("real"))return false;
		return true;
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
	
	public void setA(double a) {
		A = a;
	}
	public void setD(double d) {
		this.d = d;
	}
	public double getD() {
		return d;
	}
 
	public String getValuesType() {
		return valuesType;
	}

	public void setValuesType(String valuesType) {
		this.valuesType = valuesType;
	}
 
	public double[][] getValues() {
		return values;
	}

	public void setValues(double[][] values) {
		this.values = values;
	}

	public void setF(int f) {
		this.f = f;
	}

	public void setT1(int t1) {
		this.t1 = t1;
	}
	
//	 private void writeObject(ObjectOutputStream out) throws IOException
//	  {
//	     out.writeInt(f);
//	     out.writeInt(t1);
//	     out.writeChars(valuesType);
//	     out.writeObject(values);
//
//	  }
//
//	  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
//	  {
//	    f = in.readInt();
//	    t1 = in.readInt();
//	    valuesType = in.readLine();
//	    values = 
//	  }
	
}
