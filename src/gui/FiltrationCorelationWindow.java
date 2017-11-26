package gui;

import java.awt.Frame;
import java.util.HashMap;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import common.ConvolutionFiltrationCorelationTools;
import common.Signal;
import utils.GraphManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class FiltrationCorelationWindow {

	protected Shell shell;
	private Frame frame;
	private Signal signal;
	private int M;
	private double cutoffFreaquency;
	private String filterType;
	private boolean hanning;	
		

	public FiltrationCorelationWindow(Signal signal) {
		super();
		this.signal = signal;
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
		shell.setSize(1013, 643);
//		if(signal.isImaginary())shell.setSize(1600, 1000);
//			else shell.setSize(1600, 520);
		shell.setText("Filtration and Corelation");
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(200, 0, 800, 600);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(0, 0, 201, 600);
		
		Label lblM = new Label(composite_1, SWT.NONE);
		lblM.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblM.setBounds(26, 21, 28, 20);
		lblM.setText("M:");
		
		Combo McomboBox = new Combo(composite_1, SWT.NONE);
		McomboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					M = Integer.valueOf(McomboBox.getText());
					updateSignalsPanel();
				}
			}
		});
		McomboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				M = Integer.valueOf(McomboBox.getText());
				updateSignalsPanel();
			}
		});
		McomboBox.setItems(new String[] {"10", "50", "100", "200", "300", "500", "1000"});
		McomboBox.setBounds(26, 47, 61, 23);
		McomboBox.select(5);
		
		Label f = new Label(composite_1, SWT.NONE);
		f.setText("Cutoff frequency:");
		f.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		f.setBounds(26, 90, 119, 20);
		
		Combo cutoffFrequencyCombo = new Combo(composite_1, SWT.NONE);
		cutoffFrequencyCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					cutoffFreaquency = Double.valueOf(cutoffFrequencyCombo.getText());
					updateSignalsPanel();
				}				
			}
		});
		cutoffFrequencyCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cutoffFreaquency = Double.valueOf(cutoffFrequencyCombo.getText());
				updateSignalsPanel();
				
			}
		});
		cutoffFrequencyCombo.setItems(new String[] {"10", "30", "50", "100", "300", "500", "700", "900"});
		cutoffFrequencyCombo.setBounds(26, 116, 75, 28);
		cutoffFrequencyCombo.select(4);
		
		Combo windowParamCombo = new Combo(composite_1, SWT.READ_ONLY);
		windowParamCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(windowParamCombo.getText().equals("Hanning")) {
					System.out.println("Hanning true");
					hanning = true;
				}
				else {
					System.out.println("Rectangle");
					hanning = false;
				}
				updateSignalsPanel();
				
			}
		});
		windowParamCombo.setItems(new String[] {"rectangle", "Hanning"});
		windowParamCombo.setBounds(26, 187, 97, 28);
		windowParamCombo.select(0);
		
		Label lblWindowFunction = new Label(composite_1, SWT.NONE);
		lblWindowFunction.setText("Window function:");
		lblWindowFunction.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblWindowFunction.setBounds(26, 161, 119, 20);
		
		Label lblFilterType = new Label(composite_1, SWT.NONE);
		lblFilterType.setText("Filter type:");
		lblFilterType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFilterType.setBounds(26, 235, 119, 20);
		
		Combo filterTypeCombo = new Combo(composite_1, SWT.READ_ONLY);
		filterTypeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filterType = filterTypeCombo.getText();
				updateSignalsPanel();
			}
		});
		filterTypeCombo.setItems(new String[] {"lowPass", "highPass"});
		filterTypeCombo.setBounds(26, 261, 97, 28);
		filterTypeCombo.select(0);
		
		M = Integer.valueOf(McomboBox.getText());
		cutoffFreaquency = Double.valueOf(cutoffFrequencyCombo.getText());
		filterType = filterTypeCombo.getText();
		if(windowParamCombo.getText().equals("Hanning")) hanning = true;
		else hanning = false;

		
		updateSignalsPanel();
	}
	private void updateSignalsPanel() {
		JPanel graphPanel = null;
		double[][] filtrationResult = null;
		String graphTitle = signal.getName();
		
		switch(filterType) {
		case "lowPass":
			filtrationResult = ConvolutionFiltrationCorelationTools.lowPassFilter(M, signal, cutoffFreaquency, hanning);
			graphTitle.concat("after low pass filtration");
			break;
		case "highPass":
			filtrationResult = ConvolutionFiltrationCorelationTools.highPassFilter(M, signal, cutoffFreaquency, hanning);
			graphTitle.concat("after high pass filtration");
			break;
		}

		graphPanel = GraphManager.createGraphPanel(filtrationResult, graphTitle);

		frame.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
}
