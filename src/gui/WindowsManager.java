package gui;

import org.eclipse.swt.widgets.Shell;

import common.Signal;

public class WindowsManager {
	
	static MainWindow MW;
	
	public void createMainWindow() {
		try {
			MW = new MainWindow();
			MW.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createGraphParametersDialog(Shell shell) {
		try {
			GraphParametersDialog dialog = new GraphParametersDialog(shell,0);
			dialog.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createSignalChartsWindow(Signal signal) {
		try {
			SignalChartsWindow signalChartsWindow = new SignalChartsWindow(MW,signal);
			signalChartsWindow.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
