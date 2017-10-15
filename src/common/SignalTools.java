package common;

import java.util.Arrays;

public class SignalTools {
	
	public static double[][] generateHistogram(double[][] values, int blockCount) {
		//finding max Y value
		double min = values[0][1];
		double max = values[0][1];
		
		for(int i = 0; i < values.length; i++) {
			min = min < values[i][1] ? min : values[i][1];
			max = max > values[i][1] ? max : values[i][1];			
		}
		
		double size = Math.abs((max - min) / blockCount);
		
		double tab[][] = new double[blockCount][2];
		for(int i = 0; i < blockCount; i++) {
			tab[i][0] = min + (i * size);
			tab[i][1] = 0;
		}
		for(int i = 0; i < values.length; i++) {
			double val = values[i][1];
			for(int j = 1; j < blockCount; j++) {
				if(val < tab[j][0]) {
					tab[j-1][1]++;
					System.out.println(i + " + " + j);
					break;
				}
			}
		}
//		System.out.println(Arrays.deepToString(tab).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		return tab;
	}
	
	
	public static double[][] generateHistogram1(double[][] values, double blockSize) {
		
		//finding max Y value
		double min = values[0][1];
		double max = values[0][1];
		
		for(int i = 0; i < values.length; i++) {
			min = min < values[i][1] ? min : values[i][1];
			max = max > values[i][1] ? max : values[i][1];			
		}
		
		double ySize = min < 0 ? max - min : max;
//		System.out.println("ySize: " + ySize);
		double blockCount = (ySize / blockSize);
//		System.out.println("blockCount: " + blockCount);
//		System.out.println("blockCount % 1: " + blockCount % 1);
		int roundBlockCount = blockCount % 1 == 0 ? (int) blockCount : (int) blockCount + 1;
		double firstBlock = min % blockSize == 0 ? min : (min/blockSize * blockSize); 
		
		double[][] tab = new double[roundBlockCount][2];
		
		for(int i = 0; i < roundBlockCount; i++) {
			tab[i][0] = (firstBlock + i * blockSize);
//			System.out.println(tab[i][0]);
			tab[i][1] = 0;
		}
		
		//Updating info in tab going through sent values
		for(int i = 0; i < values.length; i++) {
			double val = values[i][1];
			for(int j = tab.length-1; j >= 0 ; j--) {
				if(val >= tab[j][0]) {
					tab[j][1]++;
					break;
				}
			}
		}
		return tab;
	}
	
	public static double getWartoscSrednia(double[][] sygnal, int f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += sygnal[n][1];
		}
		
		double n2 = f * d;
		System.out.println(n2);
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscSredniaBezwzgledna(double[][] sygnal, int f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += Math.abs(sygnal[n][1]);
		}
		
		double n2 = f * d;
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscSredniaMoc(double[][] sygnal, int f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += Math.pow((sygnal[n][1]), 2);
		}
		
		double n2 = f * d;
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscWariancja(double[][] sygnal, int f, double d, double sredniaSygnalu) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += Math.pow((sygnal[n][1] - sredniaSygnalu), 2);
		}
		
		double n2 = f * d;
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscSkuteczna(double mocSrednia) {
		return Math.sqrt(mocSrednia);
	}
	
	public static double[][] addSignals(double[][] syg1, double[][] syg2) {		
		int length = syg1.length <= syg2.length ? syg1.length : syg2.length;
		double[][] tab = new double[length][2];
		
		for(int i = 0; i < length; i++) {
			tab[i][0] = syg1[i][0];
			tab[i][1] = syg1[i][1] + syg2[i][1];
		}
		
		return tab;
	}
	
	public static double[][] substractSignals(double[][] syg1, double[][] syg2) {		
		int length = syg1.length <= syg2.length ? syg1.length : syg2.length;
		double[][] tab = new double[length][2];
		
		for(int i = 0; i < length; i++) {
			tab[i][0] = syg1[i][0];
			tab[i][1] = syg1[i][1] - syg2[i][1];
		}
		
		return tab;
	}
	
	public static double[][] multiplySignals(double[][] syg1, double[][] syg2) {		
		int length = syg1.length <= syg2.length ? syg1.length : syg2.length;
		double[][] tab = new double[length][2];
		
		for(int i = 0; i < length; i++) {
			tab[i][0] = syg1[i][0];
			tab[i][1] = syg1[i][1] * syg2[i][1];
		}
		
		return tab;
	}
	
	public static double[][] divideSignals(double[][] syg1, double[][] syg2) {		
		int length = syg1.length <= syg2.length ? syg1.length : syg2.length;
		double[][] tab = new double[length][2];
		
		for(int i = 0; i < length; i++) {
			tab[i][0] = syg1[i][0];
			tab[i][1] = syg2[i][1] != 0 ? syg1[i][1] / syg2[i][1] : syg1[i][1];
		}
		
		return tab;
	}
}
