package common;

import java.util.Arrays;

public class Fourier {
	public static double[][] convertArrayToCompex(double[][] arr) {
		double[][] wynik = new double[arr.length][3];
		for(int i = 0; i < arr.length; i++) {
			wynik[i][0] = arr[i][0];
			wynik[i][1] = arr[i][1];
			wynik[i][2] = 0;
		}
		return wynik;
	}
	public static double[][] sortValuesFFT(double[][] signal) {
		int len = signal.length;
		System.out.println(len);
		if((len & -len) != len) {
			System.err.println("D³ugoœæ sygna³u musi byæ potêg¹ 2! np 8, 16, 32.... 1024 etc.");
			return null;
		}
		
		double[][] wynik = new double[len][];
		if(len <= 1) {
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
	
	public static double[][] DFT(Signal sig) {
	  // m - kolejny wspolczynnik przerobionego wektora
	  // n - przechodzimy po wszystkich probkach sygnalu.
	  double[][] signal = sig.getValues();
	  int N = signal.length;
	  int Xlen = N - 1;
	  double[][] wynik = new double[Xlen][3];
	  for(int m = 0; m < Xlen; m++) {
	    double sumaRzeczywista = 0;
	    double sumaUrojona = 0;
	    
	    for( int n = 0; n < Xlen; n++) {
	    	double angle = 2 * Math.PI * m * n / N;
	    	sumaRzeczywista += signal[n][1] * Math.cos(angle);
	    	sumaUrojona += -signal[n][1] * Math.sin(angle);
	    }
	    
	    double f0 = sig.getF()*1.0/N;
	    wynik[m][0] = m * f0;
	    wynik[m][1] = Math.abs(sumaRzeczywista/N);
	    wynik[m][2] = Math.abs(sumaUrojona/N);
	  }
	  return wynik;
	}
	
	public static double[][] FFT(double[][] sig) {
//		System.out.println(Arrays.deepToString(sig).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		int n = sig.length;
		
		//base Case
		if(n == 1) {
			System.out.println("n == 1");
			return sig;
		}
		
		if((n & -n) != n) {
			System.err.println("D³ugoœæ sygna³u musi byæ potêg¹ 2! np 8, 16, 32.... 1024 etc.");
			return null;
		}
		
		double[][] even = new double[n/2][3];
		for(int k = 0; k < n/2; k++) {
			even[k][0] = sig[2*k][0];
			even[k][1] = sig[2*k][1];
			even[k][2] = sig[2*k][2];
		}
//		System.arraycopy(sig, 0, even, 0, n/2);
//		System.out.println("even: ");
//		System.out.println(Arrays.deepToString(sig).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		double[][] q = FFT(even);
		
		double[][] odd = even;
		for(int k = 0; k < n/2; k++) {
			odd[k][0] = sig[2*k + 1][0];
			odd[k][1] = sig[2*k + 1][1];
			odd[k][2] = sig[2*k + 1][2];
		}
//		System.arraycopy(sig, n/2, odd, 0, n/2);
		
		double[][] r = FFT(odd);
		
		//combine
		double[][] y = new double[n][3];
		for(int k = 0; k < n/2; k++) {
			double kth = -2 * k * Math.PI / n;
			double[] wk = new double[2]; 
			wk[0] =  Math.cos(kth);
			wk[1] =  Math.sin(kth);
			
			double[] wkr = new double[2];
			

			wkr[0] = (wk[0] * r[k][1]) - (wk[1] * r[k][2]);
			wkr[1] = (wk[0] * r[k][2]) + (wk[1] * r[k][1]);
			
			//x
			y[k][0] = k;
			//re
			y[k][1] = Math.abs(q[k][1] + wkr[0]);
			//im
			y[k][2] = Math.abs(q[k][2] + wkr[1]);
			
			//x
			y[k + n/2][0] = k + n/2;
			//re
			y[k + n/2][1] = Math.abs(q[k][1] - wkr[0]);
			//im
			y[k + n/2][2] = Math.abs(q[k][2] - wkr[1]);
		}
		
		return y;
	}

}
