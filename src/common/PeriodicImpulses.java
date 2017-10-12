package common;

public class PeriodicImpulses extends PeriodicSignal {

	public PeriodicImpulses(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
		name = "Periodic impuls";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i);
			
			//rounding because OP is stupid when it comes to modulo with floating points...
			double tmp = (double)Math.round(Math.IEEEremainder((1.0/f * i + t1), T) * 1000000000d) / 1000000000d;
			
			if(tmp == 0) {
				tab[i][1] = A;
			}
			else {
				tab[i][1] = 0;
			}
			
		} 
		return tab;
	}

}
