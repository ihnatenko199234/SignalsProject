package common;

public class SamplingQuantizationTools {
	public static double[][] probkujSygnal(Signal sygnal, int freq) {
		PeriodicImpulses sygnalProbkujacy = new PeriodicImpulses((1.0/freq), sygnal.getA(), sygnal.getT1(), sygnal.getF(), sygnal.getD());
		sygnalProbkujacy.generateSignal();
		
		double[][] wynikMnozenia = SignalTools.multiplySignals(sygnal.getValues(), sygnalProbkujacy.getValues());

		double[][] wynik = new double[(int) Math.ceil(freq*sygnal.getD())][2];
		
		for(int i = 0; i < wynikMnozenia.length; i++) {
			if(i % 10 == 0) {
				wynik[i/10][0] = wynikMnozenia[i][0];
				wynik[i/10][1] = wynikMnozenia[i][1];
			}
		}
		
		return wynik;
	}
}
