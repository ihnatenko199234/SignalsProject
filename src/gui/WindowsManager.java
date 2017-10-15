package gui;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;

import common.Signal;

public class WindowsManager {
	
	static MainWindow MW;
	static ArrayList<SignalChartsWindow> SCW;
	
	public void createMainWindow() {
		try {
			MW = new MainWindow();
			MW.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SCW = new ArrayList();
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
			//SCW.add(signalChartsWindow);
			signalChartsWindow.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void closeAllWindows() {
		for(SignalChartsWindow w: SCW) {
			w.shell.dispose();
		}
	}
}
