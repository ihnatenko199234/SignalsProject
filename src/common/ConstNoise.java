package common;
import java.io.Serializable;
import java.util.Random;

public class ConstNoise extends Signal implements Serializable {
	
	public ConstNoise(double A, int t1, int f, double d) {
		super(A, t1, f, d);
		name = "S1";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		Random rand = new Random();
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + ((double) 1/f * i);
			tab[i][1] = (rand.nextDouble() * (2 * A)) - A;
		} 
		values = tab;
		return tab;
	}

}
