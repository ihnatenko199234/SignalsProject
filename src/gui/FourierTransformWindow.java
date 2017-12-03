package gui;

import java.awt.Frame;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import common.ConvolutionFiltrationCorelationTools;
import common.Fourier;
import common.Signal;
import common.SignalTools;
import test.Complex;
import test.FFT;
import utils.GraphManager;

import org.eclipse.swt.widgets.Text;

public class FourierTransformWindow {

	protected Shell shell;
	private Signal signal;
	private Frame frame_1;
	private Text timeText;
	private String type;
	private Frame frame_2;

	


	public FourierTransformWindow(Signal signal, String type) {
		super();
		this.signal = signal;
		this.type = type;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Fourier transform: " + type);
		shell.setSize(850, 850);

		
		Composite composite_1 = new Composite(shell, SWT.EMBEDDED);
		composite_1.setBounds(130, 0, 700, 400);
		composite_1.setLayout(new RowLayout( ));	
		frame_1 = SWT_AWT.new_Frame(composite_1);
		
		Composite composite_2 = new Composite(shell, SWT.EMBEDDED);
		composite_2.setBounds(130, 400, 700, 400);
		composite_2.setLayout(new RowLayout( ));	
		frame_2 = SWT_AWT.new_Frame(composite_2);
		
		Composite timeDisplay = new Composite(shell, SWT.NONE);
		timeDisplay.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		timeDisplay.setBounds(0, 0, 130, 850);
		
		Label lblM = new Label(timeDisplay, SWT.NONE);
		lblM.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblM.setBounds(26, 21, 57, 20);
		lblM.setText("Time:");
		
		timeText = new Text(timeDisplay, SWT.BORDER);
		timeText.setBounds(26, 47, 78, 26);
		
		updateSignalsPanel();

	}
	
	private void updateSignalsPanel() {
		JPanel graphPanel_1 = null;
		JPanel graphPanel_2 = null;
		
		long startTime = 0;
		long endTime = 0;
		long duration = 0;

		double[][] sig = signal.getValues();
		double[][] re = new double[sig.length][2];
		double[][] im = new double[sig.length][2];
		Complex[] x = Fourier.convertToComplex(sig);
		
		Complex[] y = null;
		
		switch(type) {
		case"DFT":
			startTime = System.nanoTime();
			y = Fourier.DFT(x); 
			endTime = System.nanoTime();
			duration = (endTime - startTime)/1000;  //divide by 1000000 to get milliseconds.
			break;
		case"FFT":
			startTime = System.nanoTime();
			y = FFT.fft(x);
			endTime = System.nanoTime();
			duration = (endTime - startTime)/1000;
			break;
		}
		timeText.setText(Long.toString(duration));
		
		for (int i = 0; i < sig.length; i++) {
			re[i][0] = sig[i][0];
			re[i][1] = y[i].re();
			
			im[i][0] = sig[i][0];
			im[i][1] = y[i].im();
		}
		
		graphPanel_1 = GraphManager.createGraphPanel(re, "Real");
		graphPanel_2 = GraphManager.createGraphPanel(im, "Imaginary");

		frame_1.add(graphPanel_1);	
		graphPanel_1.revalidate();
		graphPanel_1.repaint();
		
		frame_2.add(graphPanel_2);	
		graphPanel_2.revalidate();
		graphPanel_2.repaint();
		
	}
}
