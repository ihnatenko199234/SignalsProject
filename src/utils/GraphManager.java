package utils;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import common.Signal;

public class GraphManager {
//extends JFrame {
	
	public GraphManager() {
		//super();

		//JPanel chartPanel = createGraphPanel(signal.getName(), dataset);
//		this.add(chartPanel, BorderLayout.CENTER);
//		setSize(640, 480);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
	}
	
	public static JPanel createGraphPanel(Signal signal) {
		
		double[][] array = signal.generateSignal();
		XYDataset dataset = createDataset(array); 	
		
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(signal.getName(), xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		return new ChartPanel(chart);
	}

	private static XYDataset createDataset(double[][] array) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Object 1");
		for(int i=0; i<array.length; i++) {
			series1.add(array[i][0],array[i][1]);
		}
//		series1.add(1.0, 2.0);
//        series1.add(2.0, 3.0);
//        series1.add(3.0, 2.5);
//        series1.add(3.5, 2.8);
//        series1.add(4.2, 6.0);
		dataset.addSeries(series1);
		return dataset;
	}
	
}
