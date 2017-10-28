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
	private int frequency;
	private int bits;
	
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
		shell.setSize(1419, 442);
//		if(signal.isImaginary())shell.setSize(1600, 1000);
//			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(160, 0, 600, 400);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(0, 0, 159, 400);
		
		
		Label lblFrequency = new Label(composite_1, SWT.NONE);
		lblFrequency.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblFrequency.setLocation(24, 35);
		lblFrequency.setSize(107, 20);
		lblFrequency.setText("Set frequency");
		
		Combo combo = new Combo(composite_1, SWT.NONE);
		
		combo.setItems(new String[] {"100", "300", "500", "700", "900", "1000"});
		combo.setBounds(24, 61, 107, 28);
		combo.select(0);
		
		Label lblSetBits = new Label(composite_1, SWT.NONE);
		lblSetBits.setText("Set bits");
		lblSetBits.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblSetBits.setBounds(24, 112, 107, 20);
		
		Combo combo_1 = new Combo(composite_1, SWT.NONE);
		combo_1.setItems(new String[] {"1", "3", "5", "7", "9"});
		combo_1.setBounds(24, 138, 107, 28);
		combo_1.select(0);
		combo_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					bits=Integer.valueOf(combo_1.getText());
					updateQuantedSignalPanel();
				}
			}
		});
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				bits = Integer.valueOf(combo_1.getText());
				updateQuantedSignalPanel();
			}
		});
		
		
		Composite composite_2 = new Composite(shell, SWT.EMBEDDED);
		composite_2.setBounds(766, 0, 600, 400);
		composite_2.setLayout(new RowLayout());
		frame1 = SWT_AWT.new_Frame(composite_2);
		
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				frequency = Integer.valueOf(combo.getText());
				updateSampledSignalPanel();
			}
		});
		combo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13) {
					frequency = Integer.valueOf(combo.getText());
					updateSampledSignalPanel();
				}
			}
		});			
		
		frequency = Integer.valueOf(combo.getText());
		bits = Integer.valueOf(combo_1.getText());
		
		updateSampledSignalPanel();
		updateQuantedSignalPanel();
	}
	
	private void updateSampledSignalPanel() {
		samples = SamplingQuantizationTools.probkujSygnal(signal, frequency);
		JPanel graphPanel = GraphManager.createGraphPanel(samples, "Sampling of " + signal.getName());
		frame.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
	private void updateQuantedSignalPanel() {
		double[][] quants = SamplingQuantizationTools.kwantyzacjaSygnalu(samples, bits);
		JPanel graphPanel = GraphManager.createMultiGraphPanel(samples, "Original", quants, "Quantized");
		//JPanel graphPanel = GraphManager.createGraphPanel(quants, "Quantization of "+signal.getName());
		frame1.add(graphPanel);	
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	
}
