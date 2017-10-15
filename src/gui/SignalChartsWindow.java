package gui;

import java.awt.BorderLayout;

import java.awt.Panel;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import common.Signal;
import common.SignalTools;
import utils.GraphManager;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;

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
	private double[][] graphValues;

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
		shell.setSize(1032, 532);
		if(signal.isImaginary())shell.setSize(1600, 1040);
			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(320, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);
		
		Composite composite2 = new Composite(shell, SWT.EMBEDDED);
		composite2.setBounds(940, 20, 600, 400);
		composite2.setLayout(new RowLayout( ));	
		java.awt.Frame frame2 = SWT_AWT.new_Frame(composite2);
		
		/////////// graph and histogram
		graphValues = signal.generateSignal();
		JPanel graphPanel = GraphManager.createGraphPanel(graphValues, signal.getName());
		frame.add(graphPanel);			
		
//		int[][] histogramValues = SignalTools.generateHistogram(graphValues, blockSize);
//		JPanel histogramPanel = GraphManager.createHistogramPanel(histogramValues, blockSize);
//		frame2.add(histogramPanel);
		
		//////////
		
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("Export");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("+",signal, graphValues);
			}
		});
		mntmNewItem_1.setText("+ add");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("-",signal, graphValues);
			}
		});
		mntmNewItem_2.setText("- substract");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//MW.updateGraphPanel("*",signal, graphValues);
			}
		});
		mntmNewItem_3.setText("* multiply");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//MW.updateGraphPanel("/",signal, graphValues);
			}
		});
		mntmNewItem_4.setText("/ divide");
		
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
		
		Label lblNewLabel_4 = formToolkit.createLabel(shell, "Block size for histogram:", SWT.NONE);
		lblNewLabel_4.setBounds(33, 243, 168, 20);
		
		Combo blockSizeComboBox = new Combo(shell, SWT.NONE);
		blockSizeComboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				blockSize = Integer.parseInt(blockSizeComboBox.getText());
			}
		});
		blockSizeComboBox.setItems(new String[] {"5", "10", "15", "20"});
		blockSizeComboBox.setBounds(207, 239, 52, 28);
		formToolkit.adapt(blockSizeComboBox);
		formToolkit.paintBordersFor(blockSizeComboBox);
		blockSizeComboBox.select(0);
		
		updateInfoLabels();
	}
	
	private void updateInfoLabels() {
		int f = signal.getF();
		double d = signal.getD();
		double average = SignalTools.getWartoscSrednia(graphValues, f, d);
		double averagePower = SignalTools.getWartoscSredniaMoc(graphValues, f, d);
		averageInfLbl.setText(Double.toString(average));
		averageAbsoluteInfLbl.setText(Double.toString(SignalTools.getWartoscSredniaBezwzgledna(graphValues, f, d)));
		averagePowerInfLbl.setText(Double.toString(averagePower));
		varianceInfLbl.setText(Double.toString(SignalTools.getWartoscWariancja(graphValues, f, d, average)));
		effectiveValueInfLbl.setText(Double.toString(SignalTools.getWartoscSkuteczna(averagePower)));		
	}
}
