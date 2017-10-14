package gui;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.Signal;
import common.SignalTools;
import utils.GraphManager;

import org.eclipse.swt.widgets.Menu;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Monitor;

import java.awt.Frame;
import java.util.Arrays;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {

	protected Shell shell;
	protected Shell dialogShell;
	
	protected Frame frame;
	protected Composite composite;
	
	private Signal previousSignal;
	private double[][] previousSignalValues=null;
	private String operationsHistoryTitle=null;

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			MainWindow window = new MainWindow();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		

	    Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, 0);
	    
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
	protected void createContents() {
		shell = new Shell();
		shell.setText("SWT Application");	
		shell.setSize(800, 520);
		
		composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("Import signal");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//				dialogShell = new Shell();
//				dialogShell.setSize(472, 293);
//				dialogShell.setText("Choose signal and set parameters");
				WindowsManager.createGraphParametersDialog(shell);
				
			}
		});
		mntmNewItem_1.setText("Generate new signal");

	}
	
	public void updateGraphPanel(String operation, Signal currentSignal, double[][] currentSignalValues) {
		if(currentSignal.isComposite())shell.setSize(800, 1040);
		else shell.setSize(800, 520);
		frame.removeAll();
		
		if(operationsHistoryTitle==null)operationsHistoryTitle=currentSignal.getName();
		else operationsHistoryTitle+=" "+operation+" "+currentSignal.getName();
		System.out.println(operationsHistoryTitle);
		
		double[][] operationResult = null;
		
		if(previousSignalValues==null) {
			operationResult = currentSignalValues;			
		}else {
			switch(operation) {
			case"+":
				operationResult = SignalTools.addSignals(previousSignalValues, currentSignalValues);			
				break;
			case"-":
				operationResult = SignalTools.substractSignals(previousSignalValues, currentSignalValues);
				break;
			case"*":
				operationResult = SignalTools.multiplySignals(previousSignalValues, currentSignalValues);
				break;
			case"/":
				operationResult = SignalTools.divideSignals(previousSignalValues, currentSignalValues);
				break;
			}
		}
		previousSignalValues = operationResult;
		

		System.out.println("Testing main window updating after operation:");
		System.out.println(Arrays.deepToString(operationResult).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		JPanel graphPanel = GraphManager.createGraphPanel(operationResult, operationsHistoryTitle);

		frame.add(graphPanel);
		//frame.repaint();
	}
}
