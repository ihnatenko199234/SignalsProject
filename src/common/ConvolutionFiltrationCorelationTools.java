package common;

import java.util.Arrays;

import utils.GraphManager;

public class ConvolutionFiltrationCorelationTools {
  public static double[][] obliczSplot(double[][] h, Signal xSignal) {
	double[][] x = xSignal.getValues();
    int M = h.length;
    int N = x.length;
    int n = M + N - 1;
    double xSize = xSignal.getD()/xSignal.getF();
//    System.out.println("dlugosc 1. syg: "+M);
//    System.out.println("dlugosc 2. syg: "+N);
//    System.out.println("dlugosc: "+n);
    double suma = 0;
    double[][] wynik = new double[n][2];
    for(int i = 0; i < n; i++) {
      suma = 0;
      for(int k = 0; k < M; k++) {
    	  if(i-k >= 0 && i-k < N) {
//    		  System.out.println("i: "+i+" k: "+k+" n-k: "+(i-k));
    		 
    		  suma += h[k][1] * x[i-k][1];
//    		  System.out.println("suma: "+suma);
    	  }
      }
      wynik[i][0] = xSize * (i+1);
      wynik[i][1] = suma;
    }
//    System.out.println(Arrays.deepToString(h).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//    System.out.println(Arrays.deepToString(x).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//    System.out.println(Arrays.deepToString(wynik).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

    return wynik;
  }
  
  public static double[][] lowPassFilter(int M, Signal xSignal, double fOdciecia) {
	  double[][] x = xSignal.getValues();
	  if(M > x.length)
		  System.err.println("Dlugosc ciaglu sinc nie moze byc wieksza niz iloc probek x(n)!");
	  
	  double[][] h = new double[x.length][2];
	  double roznica = x[1][0] - x[0][0];
	  double przesuniecie = (M/2.0) * roznica;
	  
	  double K = xSignal.getF()/fOdciecia;
	  
	  for(int i = 0; i < x.length; i++) {
		  if(i < M ) {
			  h[i][0] = x[i][0];
			  System.out.println(sincLowPass(h[i][0], przesuniecie, K));
			  h[i][1] = sincLowPass(h[i][0], przesuniecie, K);
		  }
		  else {
			  h[i][0] = x[i][0];
			  h[i][1] = 0;
		  }
	  }
	  GraphManager.graphWindowForTesting(h, "h");
	  System.out.println(Arrays.deepToString(h).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
	  return ConvolutionFiltrationCorelationTools.obliczSplot(h, xSignal);
  }
  
  
  private static double sincLowPass(double n, double przesuniecie, double K) {
		if(n == przesuniecie) {
			return 2.0/K;
		}
		else {
			return (Math.sin((2.0*Math.PI * (n - przesuniecie)/K)) / (Math.PI * (n - przesuniecie)));
		}
	}
}