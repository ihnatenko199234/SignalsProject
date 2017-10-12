package common;

public class SignalFactory {
	
	public static Signal getSignal(String signalType, int f, double A, int t1, 
			double d, double T, double ts, double ns, double p) {
		switch(signalType) {
		case "(S1) szum o rozk\u0142adzie jednostajnym":
			return new ConstNoise(A, t1, f, d);		
		case "(S2) szum gaussowski":
			return new GaussNoise(A, t1, f, d);
		case "(S3) sygna\u0142 sinusoidalny":
			return new Sinus(T, A, t1, f, d);
		case "(S4) sygna\u0142 sinusoidalny wyprostowany jednopo\u0142\u00F3wkowo":
			return new SinusWyprostowanyJednopolowkowo(T, A, t1, f, d);
		case "(S5) sygna\u0142 sinusoidalny wyprostowany dwupo\u0142\u00F3wkowo":
			return new SinusWyprostowanyDwupolowkowo(T, A, t1, f, d);
		case "(S6) sygna\u0142 prostok\u0105tny":
			return new Rectangle(T, A, t1, f, d);
		case "(S7) sygna\u0142 prostok\u0105tny symetryczny":
			return new RectangleSymetrical(T, A, t1, f, d);
		case "(S8) sygna\u0142 tr\u00F3jk\u0105tny":
			return new Triangle(T, A, t1, f, d);
		case "(S9) skok jednostkowy":
			return new UnitJump(ts, A, t1, f, d);
		case "(S10) impuls jednostkowy":
			return new UnitImpuls(ts, A, t1, f, d);
		case "(S11) szum impulsowy":
			return new ImpulsNoise(p, A, t1, f, d);
		default:
			return null;
		}
		
		
	}

}
