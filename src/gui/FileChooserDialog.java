package gui;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
 
public class FileChooserDialog extends JFrame {
	public FileChooserDialog() {
		this.setSize(new Dimension(632, 540));
		JFileChooser fileChooser = new JFileChooser();
		getContentPane().add(fileChooser, BorderLayout.CENTER);
	}
    private static final long serialVersionUID = 1L;
 
   
}