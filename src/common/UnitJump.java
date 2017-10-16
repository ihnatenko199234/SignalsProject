package common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class UnitJump extends Signal {
	@XStreamOmitField
	private double ts;
	public UnitJump(double ts, double A, int t1, int f, double d) {
		super(A, t1, f, d);
		this.ts = ts;
		name = "S9";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + ((double) 1.0/f * i);
			
			if(1.0/f * i < ts - t1) {
				tab[i][1] = 0;
			}
			else if(1.0/f * i == ts - t1) {
				tab[i][1] = 0.5 * A;
			}
			else {
				tab[i][1] = A;
			}
			
		} 
		values = tab;
		return tab;
	}

}
