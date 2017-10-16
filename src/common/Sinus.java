package common;
public class Sinus extends PeriodicSignal {
	
	public Sinus(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
		name = "S3";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i);
			tab[i][1] = A * Math.sin( (2*Math.PI / T) * ( 1.0/f * i - t1));
		} 
		values = tab;
		return tab;
	}

}
