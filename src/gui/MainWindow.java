package gui;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.ComplexSignal;
import common.Signal;
import common.SignalTools;
import utils.GraphManager;
import utils.SerializationManager;

import org.eclipse.swt.widgets.Menu;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Monitor;

import java.awt.Frame;
import java.io.IOException;

import javax.swing.JPanel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindow {

	protected Shell shell;
	protected Shell dialogShell;
	
	protected Frame frame;
	protected Composite composite;
	
	private double[][] previousSignalValues=null;
	double[][] operationResult = null;
	private String operationsHistoryTitle=null;
	private Text fileTxtBox;
	private Signal signalForExport;
	private JPanel graphPanel;

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
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				//WindowsManager.closeAllWindows();
			}
		});
		shell.setText("SWT Application");	
		shell.setSize(805, 599);
		
		composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//openFileChooser();
				
			}
		});
		mntmNewItem.setText("Import signal");
		
		MenuItem mntmExportSignal = new MenuItem(menu, SWT.NONE);
		mntmExportSignal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					SerializationManager.exportSignal(signalForExport);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmExportSignal.setText("Export signal");
		
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
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite_1.setBounds(0, 0, 787, 527);
		
		fileTxtBox = new Text(composite_1, SWT.BORDER);
		fileTxtBox.setBounds(111, 438, 246, 26);
		
		Button importBtn = new Button(composite_1, SWT.NONE);
		importBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String fileName = fileTxtBox.getText();
				try {
					Signal newSignal = SerializationManager.importSignal(fileName);
					WindowsManager.createSignalChartsWindow(newSignal);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		importBtn.setBounds(387, 436, 90, 30);
		importBtn.setText("Import");
		
		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				clearPanel();
			}
		});
		btnNewButton.setBounds(661, 79, 90, 30);
		btnNewButton.setText("Clear");

	}
	private void clearPanel() {
		frame.remove(graphPanel);
		operationsHistoryTitle =null;
		previousSignalValues = null;
		operationResult = null;
		frame.revalidate();
		frame.repaint();
	}
	public void updateGraphPanel(String operation, Signal currentSignal) {
		
		if(currentSignal.isImaginary())shell.setSize(659, 1040);
		else shell.setSize(805, 599);
		frame.removeAll();
		double[][] currentSignalValues = currentSignal.getValues();
		if(operationsHistoryTitle==null)operationsHistoryTitle=currentSignal.getName();
		else operationsHistoryTitle+=" "+operation+" "+currentSignal.getName();
		
		operationResult = null;
		
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
		
		signalForExport = new ComplexSignal();
		signalForExport.setF(currentSignal.getF());
		signalForExport.setT1(currentSignal.getT1());
		signalForExport.setD(currentSignal.getD());
		signalForExport.setValuesType(currentSignal.getValuesType());
		signalForExport.setValues(operationResult);
		signalForExport.setName(operationsHistoryTitle);
		
		graphPanel = GraphManager.createGraphPanel(operationResult, operationsHistoryTitle);

		frame.add(graphPanel);
		graphPanel.revalidate();
		graphPanel.repaint();
	}
}
