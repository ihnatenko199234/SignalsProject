package common;
public class SinusWyprostowanyDwupolowkowo extends PeriodicSignal {

	public SinusWyprostowanyDwupolowkowo(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
	}

	@Override
	protected double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i);
			tab[i][1] = A * Math.abs( Math.sin( (2*Math.PI / T) * ( 1.0/f * i - t1)) );
		} 
		return tab;
	}

}
