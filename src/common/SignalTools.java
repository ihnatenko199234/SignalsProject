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
		
		//creating tab with max/blockSize blocks
		int size = (int) (Math.ceil(Math.abs(max)));
		int startVal = 0;
		//if min < 0 we add more blocks for negative values
		if(min < 0) {
			size = size + (int) (Math.floor(Math.abs(min)));
			if(min % blockSize != 0) {
				startVal = (int) (min / blockSize) * blockSize - blockSize;
			}
		}
		size = size % blockSize == 0 ? size / blockSize : (int) (size / blockSize) * blockSize + blockSize;
		int[][] tab = new int [size][2];
//		System.out.println(Math.ceil(Math.abs(max / blockSize)));
		//tab initialization;
		for(int i = 0; i < tab.length; i++) {
			tab[i][0] = startVal + i * blockSize;
			tab[i][1] = 0;
		}
		
		//Updating info in tab going through sent values
		for(int i = 0; i < values.length; i++) {
			int tmp = (int) Math.abs(values[i][1]) / blockSize;
//			System.out.println(Math.abs(values[1][i]) / blockSize);
			tab[tmp][1]++;
		}
		return tab;
	}
}
