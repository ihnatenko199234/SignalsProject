package common;
public abstract class PeriodicSignal extends Signal {

	protected double T;

	public PeriodicSignal(double T, double A, int t1, int f, double d) {
		super(A, t1, f, d);

		this.T = T;
	}


	@Override
	abstract protected double[][] generateSignal();

}
