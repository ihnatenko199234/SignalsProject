package common;

import java.util.ArrayList;
import java.util.Arrays;

import utils.GraphManager;

public class SamplingQuantizationTools {
	public static double[][] probkujSygnal(Signal sygnal, int freq) {
		PeriodicImpulses sygnalProbkujacy = new PeriodicImpulses((1.0/freq), sygnal.getA(), sygnal.getT1(), sygnal.getF(), sygnal.getD());
		sygnalProbkujacy.generateSignal();
		
		double[][] wynikMnozenia = SignalTools.multiplySignals(sygnal.getValues(), sygnalProbkujacy.getValues());
		int tabWynikDlugosc = (int) Math.ceil(freq*sygnal.getD());
		ArrayList<double[]> wynik = new ArrayList<>();
		
		double coKtoraProbka = wynikMnozenia.length / tabWynikDlugosc;
		for(int i = 0; i < wynikMnozenia.length; i++) {
			if(i % coKtoraProbka == 0) {
				double[] tmp = new double[2];
				tmp[0] = wynikMnozenia[i][0];
				tmp[1] = wynikMnozenia[i][1];
				wynik.add(tmp);
			}
		}
		double[][] wynikArr = new double[wynik.size()][];
		wynikArr = wynik.toArray(wynikArr);

		return wynikArr;
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
//		System.out.println(Arrays.deepToString(wynik).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		return wynik;
	}
	
	
	public static double[][] interpolacjaZerowegoRzedu(double[][] signal, int freq) {
		double minX = signal[0][0];
		double maxX = signal[signal.length-1][0];
		
		//czas = max X probki - min X probki
		double d = maxX - minX;
		//czestotliwosc = ilosc probek / czas trwania sygnalu
		int f = (int) (signal.length/d);
		int multipiler = freq/f;
		double dt = d/freq;
		int newSignalLength = (int) (freq*d);
		
		double[][] wynik = new double[newSignalLength][2];
		
		if(multipiler == 1)
			System.err.println("Czestotliwoœæ probkowanego i odtwarzanego sygnalu sa takie same!");
		
		for(int i = 0; i < signal.length - 1; i++) {
			double val = signal[i][1];
			double x = signal[i][0];
			for(int j = 0; j < multipiler/2; j++) {
				wynik[j + i*multipiler][0] = x + (j * dt);
				wynik[j + i*multipiler][1] = val;
			}
		}
		
//		System.out.println(Arrays.deepToString(wynik).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	
		return wynik;
	}
	
	public static double[][] interpolacjaPierwszegoRzedu(double[][] signal, int freq) {
		double minX = signal[0][0];
		double maxX = signal[signal.length-1][0];
		
		//czas = max X probki - min X probki
		double d = maxX - minX;
		//czestotliwosc = ilosc probek / czas trwania sygnalu
		int f = (int) (signal.length/d);
		int multipiler = freq/f;
		double dt = d/freq;
		int newSignalLength = (int) (freq*d);
		
		double[][] wynik = new double[newSignalLength][2];
		
		for(int i = 0; i < signal.length - 2; i++) {
			double val = signal[i][1];
			double x = signal[i][0];
			
			double a = (signal[i+1][1] - signal[i][1]) / (signal[i+1][0] - signal[i][0]);
			
			for(int j = 0; j < multipiler/2; j++) {
				
				wynik[j + i*multipiler][0] = x + (j * dt);
				
				if(j == 0) {
					wynik[j + i*multipiler][1] = val;
				}
				else {
					wynik[j + i*multipiler][1] = (a * (x + (j * dt) - signal[i][0])) + signal[i][1];
				}
			}
		}
	
		return wynik;
	}
	
	public static double[][] interpolacjaSinc(double[][] signal, int freq) {
		double minX = signal[0][0];
		double maxX = signal[signal.length-1][0];
		
		//czas = max X probki - min X probki
		double d = maxX - minX;
		//czestotliwosc = ilosc probek / czas trwania sygnalu
		int f = (int) (signal.length/d);
		int multipiler = freq/f;
		System.out.println(freq);
		System.out.println(f);
		double dt = d/freq;
		int newSignalLength = (int) (freq*d);
		
		double[][] wynik = new double[newSignalLength][2];
		
		for(int i = 0; i < signal.length - 2; i++) {
			double val = signal[i][1];
			double x = signal[i][0];
			
			for(int j = 0; j < multipiler/2; j++) {
				if(j == 0) {
					wynik[j + i*multipiler][1] = val;
//					System.out.println("j = 0: "+(i*multipiler));
//					System.out.println(val);
				}
				else {
					//wartosc x
					double xt = x + (j * dt);
					wynik[j + i*multipiler][0] = xt;
					
					//Ts = przedzial czasu miedzy probkami. 1/Ts - freq
					double Ts = signal[1][0] - signal[0][0];
					double sumaSinc = 0;
					
					for(int n = 0; n <= signal.length; n++) {
						double t = xt/Ts - n;
						sumaSinc += signal[(int) (n*Ts)][1] * sinc(t);
					}
					
					wynik[j + i*multipiler][1] = sumaSinc;
				}
			}
		}
	
		return wynik;
	}
	
	private static double sinc(double t) {
		if(t == 0) {
			return 1;
		}
		else {
			return (Math.sin(Math.PI * t) / (Math.PI * t));
		}
	}
	
}
