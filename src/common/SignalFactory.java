package common;

public class SignalFactory {
	
	public static Signal getSignal(int signalType, int f, double A, int t1, 
			double d, double T) {
		switch(signalType) {
		case 0:
			return new ConstNoise(A, t1, f, d);		
		case 1:
			return new GaussNoise(A, t1, f, d);
		case 2:
			return new Sinus(T, A, t1, f, d);
		case 3:
			return new SinusWyprostowanyJednopolowkowo(T, A, t1, f, d);
		default:
			return null;
		}
		
		
	}

}
