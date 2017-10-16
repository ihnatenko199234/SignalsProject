package common;
public class Triangle extends PeriodicSignal {

	public Triangle(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
		name = "S8";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i); 
			double p = 0.5 * T;
			
			tab[i][1] = (A/p) * (p - Math.abs((1.0/f * i) % (T) - p));
			
			
		} 
		values = tab;
		return tab;
	}

}
