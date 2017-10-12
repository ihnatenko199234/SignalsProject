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
	
	int selectedIndex;
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
		shell.setSize(450, 281);
		shell.setText("Choose signal and set parameters");
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(0, 57, 444, 191);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setToolTipText(" amount of samples used to mimic continuity of the function");
		lblNewLabel.setBounds(55, 13, 30, 20);
		lblNewLabel.setText("f");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setToolTipText("amplitude");
		lblNewLabel_1.setBounds(55, 51, 30, 20);
		lblNewLabel_1.setText("A");
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setToolTipText("start time");
		lblNewLabel_2.setBounds(251, 13, 30, 20);
		lblNewLabel_2.setText("t1");
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setToolTipText("duration");
		lblNewLabel_3.setBounds(251, 51, 30, 20);
		lblNewLabel_3.setText("d");
		
		Label lblT = new Label(composite, SWT.NONE);
		lblT.setToolTipText("period");
		lblT.setBounds(55, 93, 30, 20);
		lblT.setText("T");
		
		fTextBox = new Text(composite, SWT.BORDER);
		fTextBox.setBounds(91, 10, 78, 26);
		
		ATextBox = new Text(composite, SWT.BORDER);
		ATextBox.setBounds(91, 48, 78, 26);
		
		t1TextBox = new Text(composite, SWT.BORDER);
		t1TextBox.setBounds(287, 10, 78, 26);
		
		dTextBox = new Text(composite, SWT.BORDER);
		dTextBox.setBounds(287, 48, 78, 26);
		
		TTextBox = new Text(composite, SWT.BORDER);
		TTextBox.setBounds(91, 90, 78, 26);
		
		fTextBox.setText(String.valueOf(f));
		ATextBox.setText(String.valueOf(A));
		t1TextBox.setText(String.valueOf(t1));
		dTextBox.setText(String.valueOf(d));
		TTextBox.setText(String.valueOf(T));
		
		updateVisabilityOfTextBoxes();
		
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int f = Integer.parseInt(fTextBox.getText());
				double A = Double.parseDouble(ATextBox.getText());
				int t1 = Integer.parseInt(t1TextBox.getText());
				double d = Double.parseDouble(dTextBox.getText());
				double T = Double.parseDouble(TTextBox.getText());
				
				Signal signal = SignalFactory.getSignal(signalOptionComboBox.getText(), f, A, t1, d, T);
				shell.dispose();
				WindowsManager.createSignalChartsWindow(signal);
			}
		});
		btnOk.setBounds(324, 138, 90, 30);
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
		btnCancel.setBounds(213, 138, 90, 30);
		formToolkit.adapt(btnCancel, true, true);
		
		signalOptionComboBox = new Combo(shell, SWT.NONE);
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
		signalOptionComboBox.setBounds(23, 23, 401, 28);
		signalOptionComboBox.select(0);

	}
	
	private void updateVisabilityOfTextBoxes() {
		if(selectedIndex == 0 || selectedIndex == 1 || selectedIndex == 8 || selectedIndex == 9 || selectedIndex == 10) {
			TTextBox.setEnabled(false);
		}else TTextBox.setEnabled(true);
	}
}
