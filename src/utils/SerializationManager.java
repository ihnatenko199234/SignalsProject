package utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;

import common.Signal;

public class SerializationManager {

	public static void exportSignal(Signal signal) throws IOException{
		XStream xstream = new XStream();
		xstream.processAnnotations(Signal.class);
		    
		String dataXml = xstream.toXML(signal);

		Date date = new Date();
		String timeAnnotation = String.valueOf(String.valueOf(date.getTime()));
		String tmp = signal.getName();
		tmp = tmp.replace("*", "razy");
		tmp = tmp.replace("/", "dziel");
		FileOutputStream fout=new FileOutputStream(tmp+"-"+timeAnnotation+".xml");  
		ObjectOutputStream out=new ObjectOutputStream(fout);  
		  
		out.writeObject(dataXml);  
		out.close();
		out.flush();  
		
	}
	
	public static Signal importSignal(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));  
		String dataXml=(String)in.readObject();  
		in.close();
		//System.out.println(dataXml);
		XStream xstream = new XStream();
		XStream.setupDefaultSecurity(xstream);
		xstream.addPermission(AnyTypePermission.ANY);
		String[] classes = new String[1];
		classes[0] = Signal.class.getName();
		xstream.allowTypes(classes);
		xstream.processAnnotations(Signal.class);
		Signal signal = (Signal)xstream.fromXML(dataXml);
		//System.out.println(signal.getF());
		return signal;
	}
}
