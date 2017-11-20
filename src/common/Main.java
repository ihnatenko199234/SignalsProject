package common;
import java.io.IOException;
import javax.xml.bind.JAXBException;

import gui.WindowsManager;
import utils.GraphManager;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, JAXBException {
		double 		amplituda = 1,
				    czasTrwania = 5,
		 			okres = 2;
		int 	  	czasPoczatkowy = 0,
				    ilProbek = 100;
				   
//				
		ConstNoise n = new ConstNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		GaussNoise g = new GaussNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin = new Sinus(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin2 = new Sinus(okres, amplituda, czasPoczatkowy+1, ilProbek, czasTrwania);
		SinusWyprostowanyJednopolowkowo sinProsty = new SinusWyprostowanyJednopolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyDwupolowkowo sinProstyDwa = new SinusWyprostowanyDwupolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Triangle triangle = new Triangle(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Rectangle rect = new Rectangle(0.5, okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitJump unitJump = new UnitJump(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitImpuls unitImpuls = new UnitImpuls(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		ImpulsNoise impulsNoise = new ImpulsNoise(0.01, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		PeriodicImpulses periodImpuls = new PeriodicImpulses(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		double[][] array1 = n.generateSignal();
//		double[][] array = g.generateSignal();
		double[][] array2 = sin.generateSignal();
		double[][] array3 = sin2.generateSignal();
//		double[][] array = sinProsty.generateSignal();
//		double[][] array = sinProstyDwa.generateSignal();
		double[][] array = triangle.generateSignal();
//		double[][] array = unitJump.generateSignal();
//		double[][] array = unitImpuls.generateSignal();
//		double[][] array = impulsNoise.generateSignal();
//		double[][] array = periodImpuls.generateSignal();
		double[][] recta = rect.generateSignal();
		
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
		
//		double[][] splot = ConvolutionFiltrationCorelationTools.obliczSplot(array2, sin);
		double[][] korelacja = ConvolutionFiltrationCorelationTools.obliczKorelacje(array2, sin);
//		double[][] lowPass = ConvolutionFiltrationCorelationTools.lowPassFilter(99, n, 500, false);
//		double[][] lowPassHanning = ConvolutionFiltrationCorelationTools.lowPassFilter(99, n, 500, true);
//		double[][] highPass = ConvolutionFiltrationCorelationTools.highPassFilter(11, triangle, 500, false);
//		double[][] highPassHanning = ConvolutionFiltrationCorelationTools.highPassFilter(11, triangle, 500, true);
//		System.out.println(Arrays.deepToString(kwantyzacja).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

//		GraphManager.graphWindowForTesting(sin.getValues(), "sygnal1");
//		GraphManager.graphWindowForTesting(sin2.getValues(), "sygnal2");
//		GraphManager.graphWindowForTesting(sampling, "sampling");
//		GraphManager.graphWindowForTesting(kwantyzacja, "kwantyzacja");
//		GraphManager.graphWindowForTesting(interpolacja0, "interpolacja0");
//		GraphManager.graphWindowForTesting(interpolacja1, "interpolacja1");
//		GraphManager.graphWindowForTesting(interpolacjaSinc, "interpolacjaSinc");
//		GraphManager.graphWindowForTesting(splot, "splot");
//		GraphManager.graphWindowForTesting(lowPass, "lowPassFilter");
//		GraphManager.graphWindowForTesting(lowPassHanning, "lowPassHanningFilter");
//		GraphManager.graphWindowForTesting(highPass, "highPassFilter");
//		GraphManager.graphWindowForTesting(highPassHanning, "highPassHanningFilter");
		GraphManager.graphWindowForTesting(korelacja, "korelacja");
//		
//		WindowsManager.createSignalChartsWindow(sin);
//		WindowsManager.createSignalSampleWindow(sin);

//		
//	    WindowsManager WM = new WindowsManager();
//		WM.createMainWindow();
////		
		
//		System.out.println("MSE: " + Measures.MSE(sin.getValues(), interpolacja1));
//		System.out.println("SNR: " + Measures.SNR(sin.getValues(), interpolacja1));
//		System.out.println("PSNR: " + Measures.PSNR(sin.getValues(), interpolacja1));
//		System.out.println("MD: " + Measures.MD(sin.getValues(), interpolacja1));
	}
}
