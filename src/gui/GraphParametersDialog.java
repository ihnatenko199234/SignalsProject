package gui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.forms.widgets.FormToolkit;

import common.Signal;
import common.SignalFactory;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class GraphParametersDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text fTextBox;
	private Text ATextBox;
	private Text t1TextBox;
	private Text dTextBox;
	private Text TTextBox;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Combo signalOptionComboBox;
	
	int f = 200;
	double A = 1;
	int t1 = 0;
	double d = 2;
	double T = 1;
	double ts = 4;
	double ns = 2;
	double p = 0.01;
	
	
	int selectedIndex;
	private Text tsTextBox;
	private Text nsTextBox;
	private Text pTextBox;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GraphParametersDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		Monitor primary =  Display.getCurrent().getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(-11, 0, 465, 64);
		formToolkit.adapt(composite);
		formToolkit.paintBordersFor(composite);
		

		
		signalOptionComboBox = new Combo(composite, SWT.NONE);
		signalOptionComboBox.setLocation(37, 26);
		signalOptionComboBox.setSize(401, 28);
		signalOptionComboBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedIndex = signalOptionComboBox.getSelectionIndex();
				updateVisabilityOfTextBoxes();
			}
		});
		signalOptionComboBox.setItems(new String[] {
				"(S1) szum o rozk\u0142adzie jednostajnym", 
				"(S2) szum gaussowski", 
				"(S3) sygna\u0142 sinusoidalny", 
				"(S4) sygna\u0142 sinusoidalny wyprostowany jednopo\u0142\u00F3wkowo", 
				"(S5) sygna\u0142 sinusoidalny wyprostowany dwupo\u0142\u00F3wkowo", 
				"(S6) sygna\u0142 prostok\u0105tny", 
				"(S7) sygna\u0142 prostok\u0105tny symetryczny", 
				"(S8) sygna\u0142 tr\u00F3jk\u0105tny", 
				"(S9) skok jednostkowy", 
				"(S10) impuls jednostkowy", 
				"(S11) szum impulsowy"});
		signalOptionComboBox.select(0);
		
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setModified(true);
		shell.setSize(450, 325);
		shell.setText("Choose signal and set parameters");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 57, 454, 288);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setToolTipText(" amount of samples used to mimic continuity of the function");
		lblNewLabel.setBounds(55, 38, 30, 20);
		lblNewLabel.setText("f");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setToolTipText("amplitude");
		lblNewLabel_1.setBounds(55, 76, 30, 20);
		lblNewLabel_1.setText("A");
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setToolTipText("start time");
		lblNewLabel_2.setBounds(251, 38, 30, 20);
		lblNewLabel_2.setText("t1");
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setToolTipText("duration");
		lblNewLabel_3.setBounds(251, 76, 30, 20);
		lblNewLabel_3.setText("d");
		
		Label lblT = new Label(composite, SWT.NONE);
		lblT.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblT.setToolTipText("period");
		lblT.setBounds(55, 118, 30, 20);
		lblT.setText("T");
		
		Label lblTs = new Label(composite, SWT.NONE);
		lblTs.setToolTipText("t[s] wype\u0142nienie");
		lblTs.setText("ts");
		lblTs.setBounds(55, 160, 30, 20);
		formToolkit.adapt(lblTs, true, true);
		
		Label lblPs = new Label(composite, SWT.NONE);
		lblPs.setToolTipText("numer pr\u00F3bki, dla kt\u00F3rej nast\u0119puje skok amplitudy");
		lblPs.setText("ns");
		lblPs.setBounds(251, 160, 30, 20);
		formToolkit.adapt(lblPs, true, true);
		
		fTextBox = new Text(composite, SWT.BORDER);
		fTextBox.setBounds(91, 35, 78, 26);
		
		ATextBox = new Text(composite, SWT.BORDER);
		ATextBox.setBounds(91, 73, 78, 26);
		
		t1TextBox = new Text(composite, SWT.BORDER);
		t1TextBox.setBounds(287, 35, 78, 26);
		
		dTextBox = new Text(composite, SWT.BORDER);
		dTextBox.setBounds(287, 73, 78, 26);
		
		TTextBox = new Text(composite, SWT.BORDER);
		TTextBox.setBounds(91, 115, 78, 26);
		
		tsTextBox = new Text(composite, SWT.BORDER);
		tsTextBox.setBounds(91, 154, 78, 26);
		
		nsTextBox = new Text(composite, SWT.BORDER);
		nsTextBox.setBounds(287, 154, 78, 26);
		
		pTextBox = new Text(composite, SWT.BORDER);
		pTextBox.setBounds(287, 112, 78, 26);
		
		fTextBox.setText(String.valueOf(f));
		ATextBox.setText(String.valueOf(A));
		t1TextBox.setText(String.valueOf(t1));
		dTextBox.setText(String.valueOf(d));
		TTextBox.setText(String.valueOf(T));
		tsTextBox.setText("1");
		nsTextBox.setText("1");
		pTextBox.setText(String.valueOf(p));
		
		
		updateVisabilityOfTextBoxes();
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				f = Integer.parseInt(fTextBox.getText());
				A = Double.parseDouble(ATextBox.getText());
				t1 = Integer.parseInt(t1TextBox.getText());
				d = Double.parseDouble(dTextBox.getText());
				T = Double.parseDouble(TTextBox.getText());
				ts = Double.parseDouble(tsTextBox.getText());
				ns = Double.parseDouble(nsTextBox.getText());
				p = Double.parseDouble(pTextBox.getText());
				
				Signal signal = SignalFactory.getSignal(signalOptionComboBox.getText(), f, A, t1, d, T, ts, ns, p);
				shell.dispose();
				WindowsManager.createSignalChartsWindow(signal);
			}
		});
		btnOk.setBounds(231, 212, 90, 30);
		formToolkit.adapt(btnOk, true, true);
		btnOk.setText("OK");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				shell.dispose();
			}
		});
		btnCancel.setText("Cancel");
		btnCancel.setBounds(120, 212, 90, 30);
		formToolkit.adapt(btnCancel, true, true);
		
		
		
		Label lblP = new Label(composite, SWT.NONE);
		lblP.setToolTipText("prawdopodobie\u0144stwem wyst\u0105pienia warto\u015Bci A");
		lblP.setText("p");
		lblP.setBounds(251, 118, 30, 20);
		formToolkit.adapt(lblP, true, true);

	}
	
	private void updateVisabilityOfTextBoxes() {
		if(selectedIndex == 0 || selectedIndex == 1 || selectedIndex == 8 || selectedIndex == 9 || selectedIndex == 10) {
			TTextBox.setEnabled(false);
		}else TTextBox.setEnabled(true);
		
		if(selectedIndex == 8) {
			tsTextBox.setEnabled(true);
		}else tsTextBox.setEnabled(false);
		
		if(selectedIndex == 9) {
			nsTextBox.setEnabled(true);
		}else nsTextBox.setEnabled(false);
		
		if(selectedIndex == 10) {
			pTextBox.setEnabled(true);
		}else pTextBox.setEnabled(false);
		

	}
}
