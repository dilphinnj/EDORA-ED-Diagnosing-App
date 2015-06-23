import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.*;
//import javax.swing.JTextPane;
import jess.*;


public class testFile {
	
	

	public static void method() throws FileNotFoundException
	{
	    // Create a Jess engine
	    Rete rete = new Rete();
	    
	    System.out.println(new java.io.File("").getAbsolutePath());

	    // Open the file test.clp
	    FileReader fr = new FileReader("EDalgorithm.clp");

	    // Create a parser for the file, telling it where to take input
	    // from and which engine to send the results to
	    Jesp j = new Jesp(fr, rete);
	    try
	    {
	        // parse and execute one construct, without printing a prompt
	        j.parse(false);
	    }
	    catch (Exception re)
	    {
	        // All Jess errors are reported as 'ReteException's.
	        re.printStackTrace(rete.getErrStream());
	    }
	}

	
	public static void main(String args[]) throws Exception
	{
		
		method();
	
	}
	
}

