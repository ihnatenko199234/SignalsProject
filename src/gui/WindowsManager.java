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
//			FileChooserDialog fc = new FileChooserDialog();
//			fc.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SCW = new ArrayList<SignalChartsWindow>();
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
	
	public static void createSignalSampleWindow(Signal signal) {
		SampleQuantizeWindow signalSampleWindow = new SampleQuantizeWindow(signal);
		signalSampleWindow.open();
	}
	
	public static void closeAllWindows() {
		for(SignalChartsWindow w: SCW) {
			w.shell.dispose();
		}
	}

	public static void createFiltrationCorelationWindow(Signal signal) {
		FiltrationCorelationWindow filtrationCorelationWindow = new FiltrationCorelationWindow(signal);
		filtrationCorelationWindow.open();		
	}

	public static void createFourierTransformWindow(Signal signal, String type) {
		FourierTransformWindow fourierTransformWindow = new FourierTransformWindow(signal, type);
		fourierTransformWindow.open();
		
	}
}
