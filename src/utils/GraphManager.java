package utils;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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
	
//	private static XYDataset createDatasetInt(int[][] array) {
//		XYSeriesCollection dataset = new XYSeriesCollection();
//		XYSeries series1 = new XYSeries("Object 1");
//		for(int i=0; i<array.length; i++) {
//			series1.add(array[i][0],array[i][1]);
//		}
//		dataset.addSeries(series1);
//		return dataset;
//	}
	
	
	public static JPanel createHistogramPanel(double[][] values, int blockSize) {
		
		
		double[][] histogramValues = SignalTools.generateHistogram(values, blockSize);
		
		XYSeriesCollection histogramDataset= new XYSeriesCollection();
		double barWidth = (histogramValues[histogramValues.length -  1][0] - histogramValues[0][0]) / blockSize;
		histogramDataset.setIntervalWidth(barWidth);
		XYSeries xy= new XYSeries("");
		
		for(int i=0; i<histogramValues.length; i++) {
			xy.add(histogramValues[i][0], histogramValues[i][1]);
		}

//		xy.add(0,3);
//		xy.add(10,4);
//		xy.add(20,2);
//		xy.add(30,9);
//		xy.add(40,5);
//		xy.add(50,15);
		histogramDataset.addSeries(xy);

		final NumberAxis xAxis = new NumberAxis("Samples");
		//xAxis.setAutoRangeIncludesZero(false);
		final ValueAxis yAxis = new NumberAxis("Numbers");
		final XYBarRenderer renderer = new XYBarRenderer();

		final XYPlot plot = new XYPlot((XYDataset) histogramDataset, xAxis, yAxis, renderer);
		
		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		return new ChartPanel(chart);		
	}
	
}
