package common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class UnitImpuls extends Signal {
	@XStreamOmitField
	private double ns;
	public UnitImpuls(double ns, double A, int t1, int f, double d) {
		super(A, t1, f, d);
		this.ns = ns;
		name = "S10";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + ((double) 1.0/f * i);
			
			double tmp = (double) Math.round(((1.0/f * i) + t1) * 1000000000d) / 1000000000d;
			if( tmp  - ns == 0) {				
				tab[i][1] = A;
			}
			else {
				tab[i][1] = 0;
			}
			
		} 
		values = tab;
		return tab;
	}

}
