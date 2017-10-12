package common;
import java.util.Arrays;

import gui.MainWindow;
import utils.GraphManager;

public class Main {

	public static void main(String[] args) {
		double 	amplituda = 1,
				    czasTrwania = 2;
		int 	  czasPoczatkowy = 0,
				    ilProbek = 200,
				    okres = 1;
				
		ConstNoise n = new ConstNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		GaussNoise g = new GaussNoise(amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Sinus sin = new Sinus(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		SinusWyprostowanyJednopolowkowo sinProsty = new SinusWyprostowanyJednopolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		SinusWyprostowanyDwupolowkowo sinProstyDwa = new SinusWyprostowanyDwupolowkowo(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
		Triangle saw = new Triangle(okres, amplituda, czasPoczatkowy, ilProbek, czasTrwania);
//		double[][] array = n.generateSignal();
//		double[][] array = g.generateSignal();
//		double[][] array = sin.generateSignal();
//		double[][] array = sinProsty.generateSignal();
//		double[][] array = sinProstyDwa.generateSignal();
		double[][] array = saw.generateSignal();

		System.out.println(Arrays.deepToString(array).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		GraphManager graphManager = new GraphManager(1, saw);
		graphManager.setVisible(true);
		
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
