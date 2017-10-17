package common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class RectangleSymetrical extends PeriodicSignal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2651639841938328841L;
	@XStreamOmitField
	private double kw;
	public RectangleSymetrical(double T, double A, int t1, int f, double d, double kw) {
		super(T, A, t1, f, d);
		name = "S7";
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
			  tab[i][1] = -A;
			}
			
		} 
		values = tab;
		return tab;
	}

}
