package common;

public class SignalTools {
	public static int[][] generateHistogram(double[][] values, int blockSize) {
		
		//finding max Y value
		double min = values[1][0];
		double max = values[1][0];
		for(int i = 0; i < values.length; i++) {
			min = min < values[i][1] ? min : values[i][1];
			max = max > values[i][1] ? max : values[i][1];
		}
		
		double ySize = min < 0 ? max - min : max;
		
		double blockCount = ySize / blockSize;
		int roundBlockCount = blockCount % 1 == 0 ? (int) blockCount : (int) blockCount + 1;
		int firstBlock = min % blockSize == 0 ? (int) min : ((int) (min/blockSize) * blockSize) + blockSize; 
		
		int[][] tab = new int[roundBlockCount][2];
		
		for(int i = 0; i < roundBlockCount; i++) {
			tab[i][0] = firstBlock + i * blockSize;
			tab[i][1] = 0;
		}
		
		
		
//		
//		//creating tab with max/blockSize blocks
//		int size = (int) (Math.ceil(Math.abs(max)));
//		int startVal = 0;
//		//if min < 0 we add more blocks for negative values
//		if(min < 0) {
//			size = size + (int) (Math.floor(Math.abs(min)));
//			if(min % blockSize != 0) {
//				startVal = (int) (min / blockSize) * blockSize - blockSize;
//			}
//		}
//		size = size % blockSize == 0 ? size / blockSize : (int) (size / blockSize) * blockSize + blockSize;
//		int[][] tab = new int [size][2];
		
		
//		System.out.println(Math.ceil(Math.abs(max / blockSize)));
		//tab initialization;
//		for(int i = 0; i < tab.length; i++) {
//			tab[i][0] = startVal + i * blockSize;
//			tab[i][1] = 0;
//		}
		
		//Updating info in tab going through sent values
		for(int i = 0; i < values.length; i++) {
			double val = values[i][1];
			for(int j = tab.length - 1; j > 0 ; j--) {
				if(val > tab[j-1][0]) {
					tab[j][1]++;
					break;
				}
			}
//			int tmp = (int) Math.abs(values[i][1]) / blockSize;
//			System.out.println(Math.abs(values[1][i]) / blockSize);
//			tab[tmp][1]++;
		}
		return tab;
	}
	
	public static double getWartoscSrednia(double[][] sygnal, double f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += sygnal[n][1];
		}
		
		double n2 = f * d;
		System.out.println(n2);
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscSredniaBezwzgledna(double[][] sygnal, double f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += Math.abs(sygnal[n][1]);
		}
		
		double n2 = f * d;
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscSredniaMoc(double[][] sygnal, double f, double d) {
		double suma = 0;
		for(int n = 0; n < sygnal.length; n++) {
			suma += Math.pow((sygnal[n][1]), 2);
		}
		
		double n2 = f * d;
		
		double srednia = suma/n2;
		return srednia;
	}
	
	public static double getWartoscWariancja(double[][] sygnal, double f, double d, double sredniaSygnalu) {
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
