package gui;

import java.awt.Frame;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import common.Measures;
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
	private int frequencyOfSampledSignal;
	private int bits;
	private double[][] reconstructedValues;
	private String reconstructionType;
	private String displayedSignals;
	protected int frequencyOfReconstructedSignal;
	private double[][] quants;
	private Text mseText;
	private Text snrText;
	private Text psnrText;
	private Text mdText;
	private Text originalFrequency;
	
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
		lblFrequency.setLocation(18, 88);
		lblFrequency.setSize(78, 20);
		lblFrequency.setText("sampled");
		
		Combo fSamplingCombo = new Combo(composite_1, SWT.NONE);
		
		fSamplingCombo.setItems(new String[] {"5", "10", "20", "50", "100", "300", "500", "700", "900", "1000", "10000"});
		fSamplingCombo.setBounds(113, 85, 78, 28);
		fSamplingCombo.select(0);

		
		Label lblSetBits = new Label(composite_1, SWT.NONE);
		lblSetBits.setText("Bits");
		lblSetBits.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSetBits.setBounds(18, 175, 87, 20);
		
		Combo bitsCombo = new Combo(composite_1, SWT.NONE);
		bitsCombo.setItems(new String[] {"1", "3", "5", "7", "9"});
		bitsCombo.setBounds(113, 172, 78, 28);
		bitsCombo.select(1);
		
		Combo reconstructionCombo = new Combo(composite_1, SWT.READ_ONLY );
		reconstructionCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		reconstructionCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				reconstructionType = reconstructionCombo.getText();
				updateSignals();
				updateSignalsPanel();
			}
		});
		reconstructionCombo.setItems(new String[] {"zero-order hold", "first-order hold", "sinc"});
		reconstructionCombo.setBounds(18, 246, 145, 28);
		reconstructionCombo.select(0);
		
		reconstructionType = reconstructionCombo.getText();
		
		Label lblReconstructionType = new Label(composite_1, SWT.NONE);
		lblReconstructionType.setText("Reconstruction:");
		lblReconstructionType.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblReconstructionType.setBounds(18, 217, 107, 20);
		
		Combo fReconstructingCombo = new Combo(composite_1, SWT.NONE);
		fReconstructingCombo.addKeyListener(new KeyAdapter() {
			@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == 13) {
						frequencyOfReconstructedSignal = Integer.valueOf(fReconstructingCombo.getText());
						updateSignals();
						updateSignalsPanel();
				}
			}
		});
		fReconstructingCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequencyOfReconstructedSignal = Integer.valueOf(fReconstructingCombo.getText());
				updateSignals();
				updateSignalsPanel();

			}
		});

		fReconstructingCombo.setItems(new String[] {"5", "10", "20", "50", "100", "300", "500", "700", "900", "1000", "10000"});
		fReconstructingCombo.setBounds(113, 123, 78, 28);
		fReconstructingCombo.select(5);
		
		Label lblFreconstructing = new Label(composite_1, SWT.NONE);
		lblFreconstructing.setText("reconstructed");
		lblFreconstructing.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFreconstructing.setBounds(18, 126, 93, 20);
		
		Label lblDisplayedSignals = new Label(composite_1, SWT.NONE);
		lblDisplayedSignals.setText("Displayed signals:");
		lblDisplayedSignals.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblDisplayedSignals.setBounds(18, 315, 118, 23);
		
		Combo displayedSignalsCombo = new Combo(composite_1, SWT.READ_ONLY);
		displayedSignalsCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayedSignals = displayedSignalsCombo.getText();
				updateSignalsPanel();
			}
		});
		displayedSignalsCombo.setItems(new String[] {"O","S","Q","R","S & R","S & Q", "O & S", "O & Q", "O & S & R", "O & S & Q"});
		displayedSignalsCombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		displayedSignalsCombo.setBounds(18, 344, 118, 28);
		displayedSignalsCombo.select(4);
		
		Label lblMse = new Label(composite_1, SWT.NONE);
		lblMse.setText("MSE:");
		lblMse.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMse.setBounds(18, 427, 47, 20);
		
		mseText = new Text(composite_1, SWT.BORDER);
		mseText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		mseText.setEditable(false);
		mseText.setBounds(71, 424, 78, 26);
		
		snrText = new Text(composite_1, SWT.BORDER);
		snrText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		snrText.setEditable(false);
		snrText.setBounds(71, 456, 78, 26);
		
		Label lblSnr = new Label(composite_1, SWT.NONE);
		lblSnr.setText("SNR:");
		lblSnr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSnr.setBounds(18, 459, 47, 20);
		
		Label lblPsnr = new Label(composite_1, SWT.NONE);
		lblPsnr.setText("PSNR:");
		lblPsnr.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblPsnr.setBounds(18, 495, 47, 20);
		
		psnrText = new Text(composite_1, SWT.BORDER);
		psnrText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		psnrText.setEditable(false);
		psnrText.setBounds(71, 492, 78, 26);
		
		Label lblMd = new Label(composite_1, SWT.NONE);
		lblMd.setText("MD:");
		lblMd.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblMd.setBounds(18, 530, 47, 20);
		
		mdText = new Text(composite_1, SWT.BORDER);
		mdText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		mdText.setEditable(false);
		mdText.setBounds(71, 527, 78, 26);
		
		Label lblOriginal = new Label(composite_1, SWT.NONE);
		lblOriginal.setText("original");
		lblOriginal.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblOriginal.setBounds(18, 53, 87, 20);
		
		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(18, 21, 173, 20);
		lblNewLabel.setText("Signals frequencies (Hz):");
		
		originalFrequency = new Text(composite_1, SWT.BORDER);
		originalFrequency.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		originalFrequency.setText(String.valueOf(signal.getF()));
		originalFrequency.setEditable(false);
		originalFrequency.setBounds(113, 50, 78, 26);
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
				frequencyOfSampledSignal = Integer.valueOf(fSamplingCombo.getText());
				updateSignals();
				updateSignalsPanel();
			}
		});
		fSamplingCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					frequencyOfSampledSignal = Integer.valueOf(fSamplingCombo.getText());
					updateSignals();
					updateSignalsPanel();
				}
			}
		});			
		
		
		frequencyOfSampledSignal = Integer.valueOf(fSamplingCombo.getText());
		frequencyOfReconstructedSignal = Integer.valueOf(fReconstructingCombo.getText());
		bits = Integer.valueOf(bitsCombo.getText());
		displayedSignals = displayedSignalsCombo.getText();
		
		updateSignals();
		updateSignalsPanel();

	}
	
	private void updateSignals() {
		samples = SamplingQuantizationTools.probkujSygnal(signal, frequencyOfSampledSignal);

		quants = SamplingQuantizationTools.kwantyzacjaSygnalu(samples, bits);
		
		switch(reconstructionType) {
		case "first-order hold":
			reconstructedValues = SamplingQuantizationTools.interpolacjaPierwszegoRzedu(samples, frequencyOfReconstructedSignal);
			break;
		case "zero-order hold":
			reconstructedValues = SamplingQuantizationTools.interpolacjaZerowegoRzedu(samples, frequencyOfReconstructedSignal);
			break;
		case "sinc":
			reconstructedValues = SamplingQuantizationTools.interpolacjaSinc(quants, frequencyOfReconstructedSignal);
			break;
		}
		
		updateMeasures();
		
	}
	
	private void updateMeasures() {
		mseText.setText(String.valueOf(Measures.MSE(signal.getValues(), reconstructedValues)));
		snrText.setText(String.valueOf(Measures.SNR(signal.getValues(), reconstructedValues)));
		psnrText.setText(String.valueOf(Measures.PSNR(signal.getValues(), reconstructedValues)));
		mdText.setText(String.valueOf(Measures.MD(signal.getValues(), reconstructedValues)));
	}
	
	private void updateSignalsPanel() {
		JPanel graphPanel = null;
		HashMap<String, double[][]> hm = new HashMap<String, double[][]>();
		hm.put("Original", signal.getValues());
		hm.put("Sampled", samples);
		hm.put("Quantized", quants);
		
//		System.out.println("Before:");
//		double test[][] = hm.get("Reconstructed");
//		System.out.println(Arrays.deepToString(test).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		hm.put("Reconstructed", reconstructedValues);
		
//		test = hm.get("Reconstructed");
//		System.out.println("After:");
//		System.out.println(Arrays.deepToString(test).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		
		graphPanel = GraphManager.createComplexGraphPanel(hm, displayedSignals);
//		switch(displayedSignals) {
//		//"O & S", "O & Q", "O & S & R", "O & S & Q"
//		case "O & S":
//			graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", samples, "Sampled");
//			break;
//		case "O & Q":
//			graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", quants, "Quantized");
//			break;
//		case "O & S & R":
//			graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", samples, "Sampled", reconstructedValues, "Reconstructed");
//			break;
//		case "O & S & Q":
//			graphPanel = GraphManager.createMultiGraphPanel(signal.getValues(), "Original", samples, "Sampled", quants, "Quantized");
//			break;
//		
//		}

		frame.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
}
