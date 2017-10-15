package utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.thoughtworks.xstream.XStream;

import common.Signal;

public class SerializationManager {

	public static void exportSignal(Signal signal) throws IOException {
		XStream xstream = new XStream();
		xstream.processAnnotations(Signal.class);
		String dataXml = xstream.toXML(signal);
		//System.out.println(dataXml); 
		
		FileOutputStream fout=new FileOutputStream("test.xml");  
		ObjectOutputStream out=new ObjectOutputStream(fout);  
		  
		out.writeObject(dataXml);  
		out.flush();  
	}
	
	public static void importSignal(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in=new ObjectInputStream(new FileInputStream(fileName));  
		String dataXml=(String)in.readObject();  
		XStream xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		XStream.allowTypes(Signal.class);
		xstream.processAnnotations(Signal.class);
		Signal signal = (Signal)xstream.fromXML(dataXml);
	}
}
