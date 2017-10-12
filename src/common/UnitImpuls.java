package common;

public class UnitImpuls extends Signal {
	
	private double ns;
	public UnitImpuls(double ns, double A, int t1, int f, double d) {
		super(A, t1, f, d);
		this.ns = ns;
		name = "Unit impuls";
	}

	@Override
	public double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + ((double) 1.0/f * i);
			
			if(1.0/f * i + t1 - ns == 0) {
				tab[i][1] = A;
			}
			else {
				tab[i][1] = 0;
			}
			
		} 
		return tab;
	}

}
