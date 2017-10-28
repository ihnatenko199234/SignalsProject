package common;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import gui.WindowsManager;

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
		
		double[][] sampling = SamplingQuantizationTools.probkujSygnal(sin, 100);
		double[][] kwantyzacja = SamplingQuantizationTools.kwantyzacjaSygnalu(sampling, 3);
		double[][] interpolacja0 = SamplingQuantizationTools.interpolacjaZerowegoRzedu(kwantyzacja, 100);
		double[][] interpolacja1 = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(kwantyzacja, 100);
//		System.out.println(Arrays.deepToString(kwantyzacja).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));



//	    WindowsManager WM = new WindowsManager();
//		WM.createMainWindow();
	}

}
