package common;

import java.util.ArrayList;
import java.util.Arrays;

public class Fourier {
	
	public static double[][] sortValuesFFT(double[][] signal) {
//		ArrayList<double[]> np = new ArrayList();
//		ArrayList<double[]> p = new ArrayList();
		int len = signal.length;
		if((len & -len) != len) {
			System.err.println("D³ugoœæ sygna³u musi byæ potêg¹ 2! np 8, 16, 32.... 1024 etc.");
			return null;
		}
		
		double[][] wynik = new double[len][];
		if(len <= 2) {
			return signal;
		}
		else {
			double[][] np = new double[len/2][2];
			double[][] p = new double[len/2][2];
			
			int a = 0;
			int b = 0;
			for(int i = 0; i < len; i++) {
				if(i % 2 == 0) {
					p[a][0] = signal[i][0];
					p[a][1] = signal[i][1];
					a++;
				}
				else {
					np[b][0] = signal[i][0];
					np[b][1] = signal[i][1];
					b++;
				}
			}
			System.arraycopy(sortValuesFFT(p), 0, wynik, 0, p.length);
			System.arraycopy(sortValuesFFT(np), 0, wynik, p.length, np.length);
		}
		return wynik;
	}
	
	public static double[][] DFT(double[][] signal) {
	  // m - kolejny wspolczynnik przerobionego wektora
	  // n - przechodzimy po wszystkich probkach sygnalu.
	  int N = signal.length;
	  int Xlen = N - 1;
	  double[][] wynik = new double[Xlen][3];
	  for(int m = 0; m < Xlen; m++) {
	    double sumaRzeczywista = 0;
	    double sumaUrojona = 0;
	    
	    for( int n = 0; n < Xlen; n++) {
	      sumaRzeczywista += signal[n][1] * Math.cos( (2 * Math.PI * m * n) / N );
	      sumaUrojona += signal[n][1] * - Math.sin( (2 * Math.PI * m * n) / N );
	    }
	    
	    wynik[m][0] = m;
	    wynik[m][1] = sumaRzeczywista;
	    wynik[m][2] = sumaUrojona;
	  }
	  return wynik;
	}

}
