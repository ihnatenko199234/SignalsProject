package common;

import java.util.Arrays;

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
	
	
	public static double[][] kwantyzacjaSygnalu(double[][] signal, int bits) {
		//finding max Y value
		double min = signal[0][1];
		double max = signal[0][1];
		
		for(int i = 0; i < signal.length; i++) {
			min = min < signal[i][1] ? min : signal[i][1];
			max = max > signal[i][1] ? max : signal[i][1];			
		}
		
		
		int blockCount = (int) Math.pow(2, bits);
		double blockSize = (max-min)/blockCount;
		
		double[][] tabKwantyzacji = new double[blockCount][3];
		for(int i = 0; i < tabKwantyzacji.length; i++) {
			// min
			tabKwantyzacji[i][0] = min + i *  blockSize;
			// max = min bloku + rozmiar bloku
			tabKwantyzacji[i][1] = tabKwantyzacji[i][0] + blockSize;
			// srednia = min bloku + pol rozmiaru bloku
			tabKwantyzacji[i][2] = tabKwantyzacji[i][0] + (blockSize/2);
		}
		
		//zeby wszystkie wartosci w ostatnim przedziale wpadly do tego worka
		tabKwantyzacji[tabKwantyzacji.length-1][1] = ++max;
		
		double[][] wynik = new double[signal.length][2];
		
		for(int i = 0; i < signal.length; i++) {
			double val = signal[i][1];
			for(int j = 0; j <  tabKwantyzacji.length; j++) {
				if(val >= tabKwantyzacji[j][0] && val < tabKwantyzacji[j][1]) {
					wynik[i][0] = signal[i][0];
					wynik[i][1] = tabKwantyzacji[j][2];
				}
			}
		}
		
		return wynik;
	}
}
