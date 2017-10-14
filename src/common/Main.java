package common;
import java.util.Arrays;

import gui.WindowsManager;

public class Main {

	public static void main(String[] args) {
		double 		amplituda = 1,
				    czasTrwania = 2,
		 			okres = 1;
		int 	  	czasPoczatkowy = 0,
				    ilProbek = 10000;
				   
				
		ConstNoise n = new ConstNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		GaussNoise g = new GaussNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin = new Sinus(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyJednopolowkowo sinProsty = new SinusWyprostowanyJednopolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		SinusWyprostowanyDwupolowkowo sinProstyDwa = new SinusWyprostowanyDwupolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		Triangle triangle = new Triangle(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitJump unitJump = new UnitJump(1, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		UnitImpuls unitImpuls = new UnitImpuls(2, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		ImpulsNoise impulsNoise = new ImpulsNoise(0.01, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		PeriodicImpulses periodImpuls = new PeriodicImpulses(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		double[][] array = n.generateSignal();
		double[][] array = g.generateSignal();
//		double[][] array = sin.generateSignal();
//		double[][] array = sinProsty.generateSignal();
//		double[][] array = sinProstyDwa.generateSignal();
//		double[][] array = triangle.generateSignal();
//		double[][] array = unitJump.generateSignal();
//		double[][] array = unitImpuls.generateSignal();
//		double[][] array = impulsNoise.generateSignal();
//		double[][] array = periodImpuls.generateSignal();
		
		int[][] histogram = SignalTools.generateHistogram(array, 1);
		
		double srednia = SignalTools.getWartoscSrednia(array, ilProbek, czasTrwania);
		System.out.println("srednia: " + srednia);

//		System.out.println(Arrays.deepToString(histogram).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
	    WindowsManager WM = new WindowsManager();
		WM.createMainWindow();

	}

}
