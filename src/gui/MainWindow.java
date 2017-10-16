package gui;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import common.Signal;
import common.SignalTools;
import utils.GraphManager;
import utils.SerializationManager;

import org.eclipse.swt.widgets.Menu;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Monitor;

import java.awt.Component;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindow {

	protected Shell shell;
	protected Shell dialogShell;
	
	protected Frame frame;
	protected Composite composite;
	
	private Signal previousSignal;
	private double[][] previousSignalValues=null;
	private String operationsHistoryTitle=null;
	private boolean filechooser=false;
	private Text fileTxtBox;
	private Signal signalForExport;

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
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				//WindowsManager.closeAllWindows();
			}
		});
		shell.setText("SWT Application");	
		shell.setSize(805, 560);
		
		composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		frame = SWT_AWT.new_Frame(composite);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
//		JFileChooser fileChooser = new JFileChooser();
//        int returnVal = fileChooser.showOpenDialog(new JFrame());
//    if (returnVal == JFileChooser.APPROVE_OPTION) {
//        File file = fileChooser.getSelectedFile();
//        System.out.println("udalo sie.");
//// What to do with the file, e.g. display it in a TextArea
////        textarea.read( new FileReader( file.getAbsolutePath() ), null );
//    } else {
//        System.out.println("File access cancelled by user.");
//    }

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
		composite_1.setBounds(0, 0, 787, 488);
		
		fileTxtBox = new Text(composite_1, SWT.BORDER);
		fileTxtBox.setBounds(27, 438, 246, 26);
		
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
		importBtn.setBounds(303, 436, 90, 30);
		importBtn.setText("Import");

	}
	
	public void updateGraphPanel(String operation, Signal currentSignal) {
		double[][] currentSignalValues = currentSignal.getValues();
		if(currentSignal.isImaginary())shell.setSize(659, 1040);
		else shell.setSize(659, 520);
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
		
		signalForExport = currentSignal;
		signalForExport.setValues(operationResult);
		signalForExport.setName(operationsHistoryTitle);

		System.out.println("Testing main window updating after operation:");
		System.out.println(Arrays.deepToString(operationResult).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		
		JPanel graphPanel = GraphManager.createGraphPanel(operationResult, operationsHistoryTitle);

		frame.add(graphPanel);
		graphPanel.revalidate();
		graphPanel.repaint();
	}
	private void openFileChooser() {
		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(new File("/User/"));
		
		int result = jFileChooser.showOpenDialog(new JFrame());

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jFileChooser.getSelectedFile();
//			try {
//				//SerializationManager.importSignal(selectedFile.getPath());
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			}
	}
}
