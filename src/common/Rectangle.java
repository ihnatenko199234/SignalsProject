package common;
public class Rectangle extends PeriodicSignal {

  private double kw;
	public Rectangle(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
		this.kw = 1.0/2;
	}
	
	public Rectangle(double kw, double T, double A, int t1, int f, double d) {
    super(T, A, t1, f, d);
    this.kw = kw;
  }

	@Override
	protected double[][] generateSignal() {
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i); 
			
			if((1.0/f * i) % T >= t1 && (1.0/f * i) % T < kw * T + t1) {
			  tab[i][1] = A;
			}
			else {
			  tab[i][1] = 0;
			}
			
		} 
		return tab;
	}

}
