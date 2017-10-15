package common;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

public abstract class PeriodicSignal extends Signal {
	@XStreamOmitField
	protected double T;

	public PeriodicSignal(double T, double A, int t1, int f, double d) {
		super(A, t1, f, d);

		this.T = T;
	}


	@Override
	public abstract double[][] generateSignal();

}
