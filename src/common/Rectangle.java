package common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class Rectangle extends PeriodicSignal {
	@XStreamOmitField
	private double kw;
	public Rectangle(double T, double A, int t1, int f, double d) {
		super(T, A, t1, f, d);
		this.kw = 1.0/2;
		name = "Rectangle";
	}
	
	public Rectangle(double kw, double T, double A, int t1, int f, double d) {
    super(T, A, t1, f, d);
    this.kw = kw;
  }

	@Override
	public double[][] generateSignal() {
	  
	  //TODO dodac wspolczynnik wypelnienia kw
	  
		double[][] tab = new double[(int)(d*f)][2];
		
		for(int i = 0; i < d*f; i++) {
			tab[i][0] = t1 + (1.0/f * i); 
			
			if((1.0/f * i) % T >= t1 && (1.0/f * i) % T < kw * T + t1) {
			  tab[i][1] = A;// * Math.sin( (2*Math.PI / T) * ( 1.0/f * i - t1));
			}
			else {
			  tab[i][1] = 0;
			}
			
		} 
		values = tab;
		return tab;
	}

}
