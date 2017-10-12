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
import utils.GraphManager;

public class SignalChartsWindow {

	protected Shell shell;
	private Signal signal;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			SignalChartsWindow window = new SignalChartsWindow();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open(Signal signal) {
		this.signal = signal;
		Display display = Display.getDefault();
		createContents(signal.getName());
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		    
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
		shell.setSize(1800, 900);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);

		JPanel graphPanel = GraphManager.createGraphPanel(signal);
		frame.add(graphPanel);
	

	   
	}

}
