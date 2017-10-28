package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import common.SignalTools;

public class GraphManager {
	
	public GraphManager() {
		
	}
	
	public static JPanel createGraphPanel(double[][] array, String signalName) {

		XYSeriesCollection seriesCollecion = new XYSeriesCollection();
		seriesCollecion.addSeries(createSeriesDouble(array, ""));
		XYDataset dataset = seriesCollecion; 	
		
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(signalName, xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		return new ChartPanel(chart);
	}
	
	public static JPanel createMultiGraphPanel(double[][] array1, String descriptionArray1, double[][] array2, String descriptionArray2, double[][] array3, String descriptionArray3) {
		
		XYSeriesCollection seriesCollection = new XYSeriesCollection();
		seriesCollection.addSeries(createSeriesDouble(array1, descriptionArray1));
		seriesCollection.addSeries(createSeriesDouble(array2, descriptionArray2));
		seriesCollection.addSeries(createSeriesDouble(array3, descriptionArray3));
		XYDataset dataset = seriesCollection; 	
		
		
		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(descriptionArray1+" & "+descriptionArray2+" & "+descriptionArray3, 
				xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		LegendTitle lt = new LegendTitle(plot);
		lt.setItemFont(new Font("Dialog", Font.PLAIN, 15));
		lt.setBackgroundPaint(new Color(255, 255, 255, 200));
		//lt.setBackgroundPaint(Color.WHITE);
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0, lt,RectangleAnchor.BOTTOM_RIGHT);

		ta.setMaxWidth(1);
		plot.addAnnotation(ta);
		
		
//		LegendItemCollection legend = new LegendItemCollection();  
//		ArrayList<Color> colorPalette = new ArrayList<Color>();
//		colorPalette.add(Color.CYAN);
//		colorPalette.add(Color.MAGENTA);
//		colorPalette.add(Color.YELLOW);
// 
//		for (int i = 0; i < seriesCollection.getSeriesCount(); ++i) {
//			chart.getXYPlot().getRenderer().setSeriesPaint(i, colorPalette.get(i));
//		    LegendItem li = new LegendItem(seriesCollection.getSeries(i).getDescription(), "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, colorPalette.get(i));
//		    legend.add(li);
//		}  
//		chart.getXYPlot().setFixedLegendItems(legend);
		
		return new ChartPanel(chart);
	}
	
	public static JPanel createMultiGraphPanel(double[][] array1, String descriptionArray1, double[][] array2, String descriptionArray2) {
		
		XYSeriesCollection seriesCollecion = new XYSeriesCollection();
//		XYSeries series1= createSeriesDouble(array1, descriptionArray1);
//		XYSeries series2= createSeriesDouble(array2, descriptionArray2);
//
//		seriesCollecion.addSeries(series1);
//		seriesCollecion.addSeries(series2);
		seriesCollecion.addSeries(createSeriesDouble(array1, descriptionArray1));
		seriesCollecion.addSeries(createSeriesDouble(array2, descriptionArray2));
		XYDataset dataset = seriesCollecion; 	

		String xAxisLabel = "X";
		String yAxisLabel = "Y";
		
		JFreeChart chart = ChartFactory.createXYLineChart(descriptionArray1+" & "+descriptionArray2, 
				xAxisLabel, yAxisLabel, dataset,
				PlotOrientation.VERTICAL, false, false, false);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		LegendTitle lt = new LegendTitle(plot);
		lt.setItemFont(new Font("Dialog", Font.PLAIN, 15));
		lt.setBackgroundPaint(new Color(255, 255, 255, 200));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		XYTitleAnnotation ta = new XYTitleAnnotation(0.98, 0, lt,RectangleAnchor.BOTTOM_RIGHT);

		ta.setMaxWidth(0.48);
		plot.addAnnotation(ta);
		
		return new ChartPanel(chart);
	}

	private static XYSeries createSeriesDouble(double[][] array, String seriesName) {
		
		XYSeries series1 = new XYSeries(seriesName);
		for(int i=0; i<array.length; i++) {
			series1.add(array[i][0],array[i][1]);
		}
		series1.setDescription(seriesName);
		return series1;
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
	
	public static void graphWindowForTesting(double[][] array) {
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		JPanel panel = GraphManager.createGraphPanel(array, "test");
		frame.add(panel);
		frame.setVisible(true);
	}
	

	
	
	
	
}
