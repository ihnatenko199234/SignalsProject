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

public class SampleQuantizeWindow {

	protected Shell shell;
	protected Signal signal;
	private Frame frame;
	private Frame frame1;
	private double[][] samples;
	private int frequencySampling;
	private int bits;
	private double[][] reconstructedValues;
	private String reconstructionType;
	protected int frequencyReconstructing;
	
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
		shell.setSize(1419, 553);
//		if(signal.isImaginary())shell.setSize(1600, 1000);
//			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(200, 0, 600, 400);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(0, 0, 201, 400);
		
		
		Label lblFrequency = new Label(composite_1, SWT.NONE);
		lblFrequency.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFrequency.setLocation(24, 35);
		lblFrequency.setSize(107, 20);
		lblFrequency.setText("f (sampling)");
		
		Combo fSamplingCombo = new Combo(composite_1, SWT.NONE);
		
		fSamplingCombo.setItems(new String[] {"5", "10", "50", "100", "300", "500", "700", "900", "1000", "10000"});
		fSamplingCombo.setBounds(24, 61, 107, 28);
		fSamplingCombo.select(0);

		
		Label lblSetBits = new Label(composite_1, SWT.NONE);
		lblSetBits.setText("Bits");
		lblSetBits.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSetBits.setBounds(24, 176, 107, 20);
		
		Combo bitsCombo = new Combo(composite_1, SWT.NONE);
		bitsCombo.setItems(new String[] {"1", "3", "5", "7", "9"});
		bitsCombo.setBounds(24, 202, 107, 28);
		bitsCombo.select(0);
		
		Combo reconstructionCombo = new Combo(composite_1, SWT.READ_ONLY );
		reconstructionCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		reconstructionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reconstructionType = reconstructionCombo.getText();
				updateSampledSignalPanel();
			}
		});
		reconstructionCombo.setItems(new String[] {"ekstrapolation", "0-order interpolation", "1-order interpolation", "based on sinc func"});
		reconstructionCombo.setBounds(24, 279, 167, 28);
		reconstructionCombo.select(1);
		
		reconstructionType = reconstructionCombo.getText();
		
		Label lblReconstructionType = new Label(composite_1, SWT.NONE);
		lblReconstructionType.setText("Reconstruction");
		lblReconstructionType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblReconstructionType.setBounds(24, 250, 107, 20);
		
		Combo fReconstructingCombo = new Combo(composite_1, SWT.NONE);
		fReconstructingCombo.addKeyListener(new KeyAdapter() {
			@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == 13) {
						frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
						updateSampledSignalPanel();
						updateQuantedSignalPanel();
				}
			}
		});
		fReconstructingCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
				updateSampledSignalPanel();
				updateQuantedSignalPanel();
			}
		});
		fReconstructingCombo.setItems(new String[] {"100", "300", "500", "700", "900", "1000", "10000"});
		fReconstructingCombo.setBounds(24, 132, 107, 28);
		fReconstructingCombo.select(5);
		
		Label lblFreconstructing = new Label(composite_1, SWT.NONE);
		lblFreconstructing.setText("f (reconstructing)");
		lblFreconstructing.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFreconstructing.setBounds(24, 106, 113, 20);
		bitsCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					bits=Integer.valueOf(bitsCombo.getText());
					updateQuantedSignalPanel();
				}
			}
		});
		bitsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bits = Integer.valueOf(bitsCombo.getText());
				updateQuantedSignalPanel();
			}
		});
		
		
		Composite composite_2 = new Composite(shell, SWT.EMBEDDED);
		composite_2.setBounds(806, 0, 600, 400);
		composite_2.setLayout(new RowLayout());
		frame1 = SWT_AWT.new_Frame(composite_2);
		
		fSamplingCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequencySampling = Integer.valueOf(fSamplingCombo.getText());
				updateSampledSignalPanel();
				updateQuantedSignalPanel();
			}
		});
		fSamplingCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					frequencySampling = Integer.valueOf(fSamplingCombo.getText());
					updateSampledSignalPanel();
					updateQuantedSignalPanel();
				}
			}
		});			
		
		frequencySampling = Integer.valueOf(fSamplingCombo.getText());
		frequencyReconstructing = Integer.valueOf(fReconstructingCombo.getText());
		bits = Integer.valueOf(bitsCombo.getText());
		
		updateSampledSignalPanel();
		updateQuantedSignalPanel();
	}
	
	private void updateSampledSignalPanel() {
		samples = SamplingQuantizationTools.probkujSygnal(signal, frequencySampling);
		switch(reconstructionType) {
		case "1-order interpolation":
			reconstructedValues = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(samples, frequencyReconstructing);
			break;
		case "0-order interpolation":
			reconstructedValues = SamplingQuantizationTools.interpolacjaZerowegoRzedu(samples, frequencyReconstructing);
			break;
		case "ekstrapolation":
			break;
		case "based on sinc func":
			break;
			
		}
		
		//original + sampled + reconstructed
		JPanel graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", samples, "Sampled", reconstructedValues, "Reconstructed");
		//JPanel graphPanel = GraphManager.createGraphPanel(samples, "Sampling of " + signal.getName());
		frame.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
	private void updateQuantedSignalPanel() {
		double[][] quants = SamplingQuantizationTools.kwantyzacjaSygnalu(samples, bits);
		//original + sampled + quantized
		JPanel graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", samples, "Sampled", quants, "Quantized");
		//JPanel graphPanel = GraphManager.createGraphPanel(quants, "Quantization of "+signal.getName());
		frame1.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
}
