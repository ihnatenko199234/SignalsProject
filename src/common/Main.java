package common;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import gui.WindowsManager;
import utils.GraphManager;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, JAXBException {
		double 		amplituda = 1,
				    czasTrwania = 2,
		 			okres = 1;
		int 	  	czasPoczatkowy = 0,
				    ilProbek = 64;
				   
//				
		ConstNoise n = new ConstNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		GaussNoise g = new GaussNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin = new Sinus(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		SinusWyprostowanyJednopolowkowo sinProsty = new SinusWyprostowanyJednopolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyDwupolowkowo sinProstyDwa = new SinusWyprostowanyDwupolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Triangle triangle = new Triangle(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitJump unitJump = new UnitJump(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		UnitImpuls unitImpuls = new UnitImpuls(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		ImpulsNoise impulsNoise = new ImpulsNoise(0.01, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		PeriodicImpulses periodImpuls = new PeriodicImpulses(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		RectangleSymetrical rect = new RectangleSymetrical(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania, 0.5);
		double[][] array1 = n.generateSignal();
		double[][] ga = g.generateSignal();
		double[][] s = sin.generateSignal();
		double[][] sp = sinProsty.generateSignal();
//		double[][] array = sinProstyDwa.generateSignal();
		double[][] tr = triangle.generateSignal();
//		double[][] array = unitJump.generateSignal();
		double[][] ui = unitImpuls.generateSignal();
//		double[][] array = impulsNoise.generateSignal();
//		double[][] array = periodImpuls.generateSignal();
		double[][] rec = rect.generateSignal();
		
//		double[][] histogram = SignalTools.generateHistogram(array, 0.01);
		
//		double srednia = SignalTools.getWartoscSrednia(array, ilProbek, czasTrwania);
//		System.out.println("srednia: " + srednia);
//		double sredniaBezwzgledna = SignalTools.getWartoscSredniaBezwzgledna(array, ilProbek, czasTrwania);
//		System.out.println("srednia bezwzgledna: " + sredniaBezwzgledna);
//		double sredniaMoc = SignalTools.getWartoscSredniaMoc(array, ilProbek, czasTrwania);
//		System.out.println("srednia moc: " + sredniaMoc);
		
//		double wariancja = SignalTools.getWartoscWariancja(array, ilProbek, czasTrwania, srednia);
//		System.out.println("wariancja: " + wariancja);
		
//		double varSkuteczna = SignalTools.getWartoscSkuteczna(sredniaMoc);
//		System.out.println("wartosc skuteczna: " + varSkuteczna);
		
//		double[][] sampling = SamplingQuantizationTools.probkujSygnal(triangle, 20);
//		double[][] kwantyzacja = SamplingQuantizationTools.kwantyzacjaSygnalu(sampling, 4);
//		double[][] interpolacja0 = SamplingQuantizationTools.interpolacjaZerowegoRzedu(kwantyzacja, ilProbek);
//		double[][] interpolacja1 = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(sampling, ilProbek);
//		double[][] interpolacjaSinc = SamplingQuantizationTools.interpolacjaSinc(sampling, 1000);
		
//		double[][] splot = ConvolutionFiltrationCorelationTools.obliczSplot(array1, n);
//		double[][] korelacja = ConvolutionFiltrationCorelationTools.obliczKorelacje(array1, n);
//		double[][] lowPass = ConvolutionFiltrationCorelationTools.lowPassFilter(99, n, 500, false);
//		double[][] lowPassHanning = ConvolutionFiltrationCorelationTools.lowPassFilter(99, n, 500, true);
		//double[][] highPass = ConvolutionFiltrationCorelationTools.highPassFilter(11, triangle, 500, false);
		//double[][] highPassHanning = ConvolutionFiltrationCorelationTools.highPassFilter(11, triangle, 500, true);
//		System.out.println(Arrays.deepToString(kwantyzacja).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

		GraphManager.graphWindowForTesting(rect.getValues(), "sygnal");
//		GraphManager.graphWindowForTesting(sampling, "sampling");
//		GraphManager.graphWindowForTesting(kwantyzacja, "kwantyzacja");
//		GraphManager.graphWindowForTesting(interpolacja0, "interpolacja0");
//		GraphManager.graphWindowForTesting(interpolacja1, "interpolacja1");
//		GraphManager.graphWindowForTesting(interpolacjaSinc, "interpolacjaSinc");
//		GraphManager.graphWindowForTesting(splot, "splot");
//		GraphManager.graphWindowForTesting(korelacja, "korelacja");
//		GraphManager.graphWindowForTesting(lowPass, "lowPassFilter");
//		GraphManager.graphWindowForTesting(lowPassHanning, "lowPassHanningFilter");
		//GraphManager.graphWindowForTesting(highPass, "highPassFilter");
		//GraphManager.graphWindowForTesting(highPassHanning, "highPassHanningFilter");
//		
//		WindowsManager.createSignalChartsWindow(sin);
//		WindowsManager.createSignalSampleWindow(sin);

//		System.out.println("MSE: " + Measures.MSE(sin.getValues(), interpolacja1));
//		System.out.println("SNR: " + Measures.SNR(sin.getValues(), interpolacja1));
//		System.out.println("PSNR: " + Measures.PSNR(sin.getValues(), interpolacja1));
//		System.out.println("MD: " + Measures.MD(sin.getValues(), interpolacja1));
		
//		double[][] ss= new double[8][2];
//		for(int i = 0; i< 8; i++) {
//			ss[i][0] = i;
//			ss[i][1] = i;
//		}
//		System.out.println(Arrays.deepToString(ss).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
//		double[][] ww = Fourier.sortValuesFFT(ss);
		double[][] xx = Fourier.DFT(rect);
		double[][] testR = new double[xx.length][2];
		double[][] testI = new double[xx.length][2];
		
		for(int i = 0; i< xx.length; i++) {
			testR[i][0] = xx[i][0];
			testI[i][0] = xx[i][0];
			testR[i][1] = xx[i][1];
			testI[i][1] = xx[i][2];
		}
		
//		System.out.println(Arrays.deepToString(xx).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
//		GraphManager.graphWindowForTesting(ss, "ss");
//		GraphManager.graphWindowForTesting(ww, "ww");
		GraphManager.graphWindowForTesting(testR, "testR");
		GraphManager.graphWindowForTesting(testI, "testI");
		
//	    WindowsManager WM = new WindowsManager();
//	    WM.createMainWindow();
	}
}
