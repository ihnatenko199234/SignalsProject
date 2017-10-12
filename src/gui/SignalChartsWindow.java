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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SignalChartsWindow {

	protected Shell shell;
	private Signal signal;
	private MainWindow MW;

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
	public SignalChartsWindow(MainWindow MW, Signal signal) {
		this.MW = MW;
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
		if(signal.isComposite())shell.setSize(1600, 1040);
			else shell.setSize(1600, 520);
		shell.setText(title);
		
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 600, 400);
		composite.setLayout(new RowLayout( ));	
		java.awt.Frame frame = SWT_AWT.new_Frame(composite);

		JPanel graphPanel = GraphManager.createGraphPanel(signal);
		frame.add(graphPanel);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("Export");
		
		MenuItem mntmNewItem_1 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("add",signal, graphPanel);
			}
		});
		mntmNewItem_1.setText("+ add");
		
		MenuItem mntmNewItem_2 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("substract",signal, graphPanel);
			}
		});
		mntmNewItem_2.setText("- substract");
		
		MenuItem mntmNewItem_3 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("multiply",signal, graphPanel);
			}
		});
		mntmNewItem_3.setText("* multiply");
		
		MenuItem mntmNewItem_4 = new MenuItem(menu, SWT.NONE);
		mntmNewItem_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MW.updateGraphPanel("divide",signal, graphPanel);
			}
		});
		mntmNewItem_4.setText("/ divide");
	

	   
	}
}
