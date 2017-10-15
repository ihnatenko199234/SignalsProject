package utils;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import common.Signal;
import common.SignalTools;

public class GraphManager {
	
	public GraphManager() {
		
	}
	
	public static JPanel createGraphPanel(double[][] array, String signalName) {
		
//		double[][] array = signal.generateSignal();
		//ArrayList arrayList= new ArrayList(Arrays.asList(array));
		
		XYDataset dataset = createDatasetDouble(array); 	
		
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(signalName, xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		return new ChartPanel(chart);
	}

	private static XYDataset createDatasetDouble(double[][] array) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Object 1");
		for(int i=0; i<array.length; i++) {
			series1.add(array[i][0],array[i][1]);
		}
		dataset.addSeries(series1);
		return dataset;
	}
	
	private static XYDataset createDatasetInt(int[][] array) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Object 1");
		for(int i=0; i<array.length; i++) {
			series1.add(array[i][0],array[i][1]);
		}
		dataset.addSeries(series1);
		return dataset;
	}
	
	
	public static JPanel createHistogramPanel(double[][] values, int blockSize) {
		double[][] histogramValues = SignalTools.generateHistogram(values, blockSize);
		
	    HistogramDataset dataset = new HistogramDataset();
	    dataset.setType(HistogramType.RELATIVE_FREQUENCY);
	    //dataset.addSeries("Histogram",histogramValues,blockSize);
	    String plotTitle = "Histogram"; 
	    String xaxis = "number";
	    String yaxis = "value"; 
	    PlotOrientation orientation = PlotOrientation.VERTICAL; 
	    boolean show = false; 
	    boolean toolTips = false;
	    boolean urls = false; 
	    JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, dataset, orientation, show, toolTips, urls);
		
		return new ChartPanel(chart);		
	}
	
}
