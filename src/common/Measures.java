package common;

public class Measures {

	public static double MSE(double[][] array1, double[][] array2) {
		double sum = 0;
		
		for(int i=0; i<array2.length; i++) {
			sum+= Math.pow((array1[i][1] - array2[i][1]), 2);
		}

		double result = sum/array1.length;

		return Math.round( result * 1000.0 ) / 1000.0;
	}
	
	public static double SNR(double[][] array1, double[][] array2) {
		double sum1 = 0;
		double sum2 = 0;
		for(int i=0; i<array2.length; i++) {
			sum1+= Math.pow(array1[i][1], 2);
			sum2+= Math.pow((array1[i][1] - array2[i][1]), 2);
		}
		double result = 10*Math.log10(sum1/sum2);
		
		return Math.round( result * 1000.0 ) / 1000.0;
	}
	
	public static double PSNR(double[][] array1, double[][] array2) {
		double max = array1[0][1];
		
		for(int i=1; i<array2.length; i++) {
			if(array1[i][1] > max) max = array1[i][1];
		}
		double result = 10*Math.log10(max/MSE(array1,array2));
		
		return Math.round( result * 1000.0 ) / 1000.0;
	}
	
	public static double MD(double[][] array1, double[][] array2) {
		double max = Math.abs(array1[0][1] - array2[0][1]);
		double current;
		
		for(int i=1; i<array2.length; i++) {
			current = Math.abs(array1[i][1] - array2[i][1]);
			if(current > max) max = current;
		}
		
		return Math.round( max * 1000.0 ) / 1000.0;
	}
}
