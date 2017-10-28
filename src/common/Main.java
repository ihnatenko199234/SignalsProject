package common;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

import gui.WindowsManager;
import utils.GraphManager;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, JAXBException {
		double 		amplituda = 1,
				    czasTrwania = 2,
		 			okres = 1;
		int 	  	czasPoczatkowy = 0,
				    ilProbek = 1000;
				   
//				
//		ConstNoise n = new ConstNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		GaussNoise g = new GaussNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin = new Sinus(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyJednopolowkowo sinProsty = new SinusWyprostowanyJednopolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyDwupolowkowo sinProstyDwa = new SinusWyprostowanyDwupolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		Triangle triangle = new Triangle(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitJump unitJump = new UnitJump(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitImpuls unitImpuls = new UnitImpuls(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		ImpulsNoise impulsNoise = new ImpulsNoise(0.01, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		PeriodicImpulses periodImpuls = new PeriodicImpulses(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		double[][] array1 = n.generateSignal();
//		double[][] array = g.generateSignal();
		double[][] array = sin.generateSignal();
//		double[][] array = sinProsty.generateSignal();
//		double[][] array = sinProstyDwa.generateSignal();
//		double[][] array = triangle.generateSignal();
//		double[][] array = unitJump.generateSignal();
//		double[][] array = unitImpuls.generateSignal();
//		double[][] array = impulsNoise.generateSignal();
//		double[][] array = periodImpuls.generateSignal();
		
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
		
		double[][] sampling = SamplingQuantizationTools.probkujSygnal(sin, 10);
		double[][] kwantyzacja = SamplingQuantizationTools.kwantyzacjaSygnalu(sampling, 4);
		double[][] interpolacja0 = SamplingQuantizationTools.interpolacjaZerowegoRzedu(kwantyzacja, ilProbek);
		double[][] interpolacja1 = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(sampling, ilProbek);
//		System.out.println(Arrays.deepToString(kwantyzacja).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

//		GraphManager.graphWindowForTesting(sin.getValues(), "sygnal");
//		GraphManager.graphWindowForTesting(sampling, "sampling");
//		GraphManager.graphWindowForTesting(kwantyzacja, "kwantyzacja");
//		GraphManager.graphWindowForTesting(interpolacja0, "interpolacja0");
//		GraphManager.graphWindowForTesting(interpolacja1, "interpolacja1");
//		
//		WindowsManager.createSignalChartsWindow(sin);
//		WindowsManager.createSignalSampleWindow(sin);

		
	    WindowsManager WM = new WindowsManager();
		WM.createMainWindow();
		
		
//		System.out.println("MSE: " + Measures.MSE(sin.getValues(), interpolacja1));
//		System.out.println("SNR: " + Measures.SNR(sin.getValues(), interpolacja1));
//		System.out.println("PSNR: " + Measures.PSNR(sin.getValues(), interpolacja1));
//		System.out.println("MD: " + Measures.MD(sin.getValues(), interpolacja1));

	}

}
