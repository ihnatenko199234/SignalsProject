package gui;

import java.awt.Frame;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import common.SamplingQuantizationTools;
import common.Signal;
import utils.GraphManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;

public class SampleQuantizeWindow {

	protected Shell shell;
	protected Signal signal;
	private Frame frame;
	private double[][] samples;
	private int frequencySampling;
	private int bits;
	private double[][] reconstructedValues;
	private String reconstructionType;
	private String displayedSignals;
	protected int frequencyReconstructing;
	private double[][] quants;
	
	public SampleQuantizeWindow(Signal signal) {
		this.signal = signal;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents(signal.getName());
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y;
		shell.setLocation(x, y+10);
		    
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(String title) {
		shell = new Shell();
		shell.setSize(1013, 638);
//		if(signal.isImaginary())shell.setSize(1600, 1000);
//			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(200, 0, 800, 600);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(0, 0, 201, 600);
		
		
		Label lblFrequency = new Label(composite_1, SWT.NONE);
		lblFrequency.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFrequency.setLocation(18, 26);
		lblFrequency.setSize(107, 20);
		lblFrequency.setText("f (sampling)");
		
		Combo fSamplingCombo = new Combo(composite_1, SWT.NONE);
		
		fSamplingCombo.setItems(new String[] {"100", "300", "500", "700", "900", "1000"});
		fSamplingCombo.setBounds(18, 52, 107, 28);
		fSamplingCombo.select(0);
		
		Label lblSetBits = new Label(composite_1, SWT.NONE);
		lblSetBits.setText("Bits");
		lblSetBits.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSetBits.setBounds(18, 167, 107, 20);
		
		Combo bitsCombo = new Combo(composite_1, SWT.NONE);
		bitsCombo.setItems(new String[] {"1", "3", "5", "7", "9"});
		bitsCombo.setBounds(18, 193, 107, 28);
		bitsCombo.select(0);
		
		Combo reconstructionCombo = new Combo(composite_1, SWT.READ_ONLY );
		reconstructionCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		reconstructionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reconstructionType = reconstructionCombo.getText();
				updateSignalsPanel();
			}
		});
		reconstructionCombo.setItems(new String[] {"ekstrapolation", "0-order interpolation", "1-order interpolation", "based on sinc func"});
		reconstructionCombo.setBounds(18, 270, 167, 28);
		reconstructionCombo.select(1);
		
		reconstructionType = reconstructionCombo.getText();
		
		Label lblReconstructionType = new Label(composite_1, SWT.NONE);
		lblReconstructionType.setText("Reconstruction");
		lblReconstructionType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblReconstructionType.setBounds(18, 241, 107, 20);
		
		Combo fReconstructingCombo = new Combo(composite_1, SWT.NONE);
		fReconstructingCombo.addKeyListener(new KeyAdapter() {
			@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == 13) {
						frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
						updateSignals();
						updateSignalsPanel();
				}
			}
		});
		fReconstructingCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
				updateSignals();
				updateSignalsPanel();

			}
		});
		fReconstructingCombo.setItems(new String[] {"100", "300", "500", "700", "900", "1000"});
		fReconstructingCombo.setBounds(18, 123, 107, 28);
		fReconstructingCombo.select(5);
		
		Label lblFreconstructing = new Label(composite_1, SWT.NONE);
		lblFreconstructing.setText("f (reconstructing)");
		lblFreconstructing.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFreconstructing.setBounds(18, 97, 113, 20);
		
		Label lblDisplayedSignals = new Label(composite_1, SWT.NONE);
		lblDisplayedSignals.setText("Displayed signals:");
		lblDisplayedSignals.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDisplayedSignals.setBounds(18, 335, 118, 23);
		
		Combo displayedSignalsCombo = new Combo(composite_1, SWT.READ_ONLY);
		displayedSignalsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayedSignals = displayedSignalsCombo.getText();
				updateSignalsPanel();
			}
		});
		displayedSignalsCombo.setItems(new String[] {"O", "S", "S & Q", "S & Q & R"});
		displayedSignalsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		displayedSignalsCombo.setBounds(18, 364, 118, 28);
		displayedSignalsCombo.select(3);
		bitsCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					bits=Integer.valueOf(bitsCombo.getText());
					updateSignals();
					updateSignalsPanel();
				}
			}
		});
		bitsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bits = Integer.valueOf(bitsCombo.getText());
				updateSignals();
				updateSignalsPanel();
			}
		});
		
		fSamplingCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequencySampling = Integer.valueOf(fSamplingCombo.getText());
				updateSignals();
				updateSignalsPanel();
			}
		});
		fSamplingCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					frequencySampling = Integer.valueOf(fSamplingCombo.getText());
					updateSignals();
					updateSignalsPanel();
				}
			}
		});			
		
		frequencySampling = Integer.valueOf(fSamplingCombo.getText());
		frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
		bits = Integer.valueOf(bitsCombo.getText());
		displayedSignals = displayedSignalsCombo.getText();
		
		updateSignals();
		updateSignalsPanel();

	}
	
	private void updateSignals() {
		samples = SamplingQuantizationTools.probkujSygnal(signal, frequencySampling);

		quants = SamplingQuantizationTools.kwantyzacjaSygnalu(samples, bits);
		
		switch(reconstructionType) {
		case "1-order interpolation":
			reconstructedValues = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(quants, frequencyReconstructing);
			break;
		case "0-order interpolation":
			reconstructedValues = SamplingQuantizationTools.interpolacjaZerowegoRzedu(quants, frequencyReconstructing);
			break;
		case "ekstrapolation":
			break;
		case "based on sinc func":
			break;
			
		}
	}

	
	private void updateSignalsPanel() {
		JPanel graphPanel = null;
		switch(displayedSignals) {
		case "O":
			graphPanel = GraphManager.createGraphPanel(signal.getValues(), "Original");
			break;
		case "S":
			graphPanel = GraphManager.createGraphPanel(samples, "Sampled");
			break;
		case "S & Q":
			graphPanel = GraphManager.createMultiGraphPanel(samples, "Sampled", quants, "Quantized");
			break;
		case "S & Q & R":
			graphPanel = GraphManager.createMultiGraphPanel(samples, "Sampled", quants, "Quantized", reconstructedValues, "Reconstructed");
			break;
		}

		frame.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
}
