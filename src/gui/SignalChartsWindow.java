package gui;

import java.awt.Frame;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import common.ConvolutionFiltrationCorelationTools;
import common.Signal;
import common.SignalTools;
import utils.GraphManager;
import utils.SerializationManager;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class SignalChartsWindow {

	protected Shell shell;
	private Signal signal;
	private MainWindow MW;
	
	private int blockSize = 5;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Label averageInfLbl;
	private Label averageAbsoluteInfLbl;
	private Label averagePowerInfLbl;
	private Label varianceInfLbl;
	private Label effectiveValueInfLbl;
	//private double[][] graphValues;
	private Frame frame2;
	private Composite composite2;

	public SignalChartsWindow(MainWindow MW, Signal signal) {
		this.MW = MW;
		this.signal = signal;
	}

	public void open() {
		Display display = Display.getDefault();
		createContents(signal.getName());
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height);
		shell.setLocation(x, y-80);
		    
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
		if(signal.isImaginary())shell.setSize(1600, 1000);
			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(320, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);
		
		composite2 = new Composite(shell, SWT.EMBEDDED);
		composite2.setBounds(940, 20, 600, 400);
		composite2.setLayout(new RowLayout( ));	
		frame2 = SWT_AWT.new_Frame(composite2);
		
		
		/////////// graph and histogram
		signal.generateSignal();
		JPanel graphPanel = GraphManager.createGraphPanel(signal.getValues(), signal.getName());
		frame.add(graphPanel);		
		
		updateHistogramPanel();
		
		//////////
		
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					SerializationManager.exportSignal(signal);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmNewItem.setText("Export");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("+",signal);
			}
		});
		mntmNewItem_1.setText("+ add");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("-",signal);
			}
		});
		mntmNewItem_2.setText("- substract");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("*",signal);
			}
		});
		mntmNewItem_3.setText("* multiply");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("/",signal);
			}
		});
		mntmNewItem_4.setText("/ divide");
		
		MenuItem mntmNewItem_5 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WindowsManager.createSignalSampleWindow(signal);
				
			}
		});
		mntmNewItem_5.setText("Sample and Quantize");
		
		MenuItem mntmFiltrationAndCorelation = new MenuItem(menu, SWT.NONE);
		mntmFiltrationAndCorelation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WindowsManager.createFiltrationCorelationWindow(signal);
			}
		});
		mntmFiltrationAndCorelation.setText("Filtration");
		
		MenuItem mntmSplot = new MenuItem(menu, SWT.NONE);
		mntmSplot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("Convolution", signal);
			}
		});
		mntmSplot.setText("Convolution");
		
		MenuItem mntmCorrelation = new MenuItem(menu, SWT.NONE);
		mntmCorrelation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("Correlation",signal);
			}
		});
		mntmCorrelation.setText("Correlation");
		
		MenuItem mntmDft = new MenuItem(menu, SWT.NONE);
		mntmDft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WindowsManager.createFourierTransformWindow(signal, "DFT");
			}
		});
		mntmDft.setText("DFT");
		
		MenuItem mntmFft = new MenuItem(menu, SWT.NONE);
		mntmFft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WindowsManager.createFourierTransformWindow(signal, "FFT");
			}
		});
		mntmFft.setText("FFT");
		
		Label lblA = formToolkit.createLabel(shell, "Average: ", SWT.NONE);
		lblA.setBounds(33, 20, 70, 20);	
		
		Label lblNewLabel = formToolkit.createLabel(shell, "Average absolute:", SWT.NONE);
		lblNewLabel.setBounds(33, 57, 124, 20);
		
		Label lblNewLabel_1 = formToolkit.createLabel(shell, "Average power:", SWT.NONE);
		lblNewLabel_1.setBounds(33, 92, 110, 20);
		
		Label lblNewLabel_2 = formToolkit.createLabel(shell, "Variance:", SWT.NONE);
		lblNewLabel_2.setBounds(33, 129, 70, 20);
		
		Label lblNewLabel_3 = formToolkit.createLabel(shell, "Effective value:", SWT.NONE);
		lblNewLabel_3.setBounds(33, 165, 99, 20);
		
		averageInfLbl = formToolkit.createLabel(shell, "", SWT.NONE);
		averageInfLbl.setBounds(109, 20, 70, 20);
		
		averageAbsoluteInfLbl = formToolkit.createLabel(shell, "", SWT.NONE);
		averageAbsoluteInfLbl.setBounds(163, 57, 70, 20);
		
		averagePowerInfLbl = formToolkit.createLabel(shell, "", SWT.NONE);
		averagePowerInfLbl.setBounds(149, 92, 70, 20);
		
		varianceInfLbl = formToolkit.createLabel(shell, "", SWT.NONE);
		varianceInfLbl.setBounds(109, 129, 70, 20);
		
		effectiveValueInfLbl = formToolkit.createLabel(shell, "", SWT.NONE);
		effectiveValueInfLbl.setBounds(138, 165, 70, 20);
		
		Label lblNewLabel_4 = formToolkit.createLabel(shell, "Block count for histogram:", SWT.NONE);
		lblNewLabel_4.setBounds(33, 243, 178, 20);
		
		Combo blockSizeComboBox = new Combo(shell, SWT.NONE);
		blockSizeComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==13) {
					blockSize = Integer.parseInt(blockSizeComboBox.getText());
					updateHistogramPanel();
					//System.out.println("Typed text in combo box");
				}
			}
		});
	
		blockSizeComboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				blockSize = Integer.parseInt(blockSizeComboBox.getText());
				updateHistogramPanel();
			}
		});
		blockSizeComboBox.setItems(new String[] {"5", "10", "15", "20"});
		blockSizeComboBox.setBounds(212, 239, 52, 28);
		formToolkit.adapt(blockSizeComboBox);
		formToolkit.paintBordersFor(blockSizeComboBox);
		blockSizeComboBox.select(0);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(0, 0, 1600, 460);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		
		updateInfoLabels();
	}
	
	private void updateInfoLabels() {
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		
		int f = signal.getF();
		double d = signal.getD();
		double average = SignalTools.getWartoscSrednia(signal.getValues(), f, d);
		double averagePower = SignalTools.getWartoscSredniaMoc(signal.getValues(), f, d);
		averageInfLbl.setText(df.format(average));
		averageAbsoluteInfLbl.setText(df.format(SignalTools.getWartoscSredniaBezwzgledna(signal.getValues(), f, d)));
		averagePowerInfLbl.setText(df.format(averagePower));
		varianceInfLbl.setText(df.format(SignalTools.getWartoscWariancja(signal.getValues(), f, d, average)));
		effectiveValueInfLbl.setText(df.format(SignalTools.getWartoscSkuteczna(averagePower)));		
	}
	private void updateHistogramPanel() {
		frame2.removeAll();
		JPanel histogramPanel = GraphManager.createHistogramPanel(signal.getValues(), blockSize);
		frame2.add(histogramPanel);
		histogramPanel.revalidate();
		histogramPanel.repaint();
	}
}
