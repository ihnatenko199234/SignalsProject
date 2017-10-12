package common;

import java.util.Random;

public class ImpulsNoise extends Signal {
	
	private double p;
	public ImpulsNoise(double p, double A, int t1, int f, double d) {
		super(A, t1, f, d);
		this.p = p;
		name = "Impuls noise";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		Random rand = new Random();
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + ((double) 1.0/f * i);
			
			if(rand.nextDouble() > 1.0 - p) {
				tab[i][1] = A;
			}
			else {
				tab[i][1] = 0;
			}
			
		} 
		return tab;
	}

}
