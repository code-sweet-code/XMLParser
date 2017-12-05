package COM.hilbertinc.logging;

import COM.hilbertinc.base.*;
import COM.hilbertinc.xml.*;
import java.io.*;
/**
 * This is a disposable class that I am using for unit testing the
 * framework
 */
public class UnitTest 
{
/**
 * UnitTest constructor comment.
 */
public UnitTest() {
	super();
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
private static void showErrParam() {
	System.out.println("Syntax: java COM.hilbertinc.xml.UnitTest <xmldocument> -o filename -t type");
	System.out.println("type: HTML|REFLECTIVE");
	return;
}
private static void runHTMLTest(String srcFileName, String outputFile) {
	try
	{
	System.out.println("Parsing...");
	
	HDOMParser parser = new HDOMParser();
	parser.parse(srcFileName);
	if (null == parser.getDocument())
		{
		System.out.println("Document was not successfully parsed");
		System.out.println("If there are no error messages, it is probably an inability to find the DTD");
		return;
		}
	
	System.out.println("Done.  Dumping contents of parsed document.");
	try
		{
//		HDumperVisitor dumper = new HDumperVisitor();
		HTMLVisitor htmlVisitor = new HTMLVisitor(outputFile);
		htmlVisitor.getWriter().write("<html><body>");
		htmlVisitor.getWriter().write("\n");
		parser.traverse(htmlVisitor);
		htmlVisitor.getWriter().write("</html></body>");
		htmlVisitor.getWriter().write("\n");
		htmlVisitor.flush();
		System.out.println("Done.");
		}
	catch(Exception exception)
		{
		throw new HException(exception, "Error dumping the contents of the document");
		}
	}
catch(HException exception)
	{
	System.err.println("Exception...");
	exception.print();
	}
}

public static void main(java.lang.String[] args)
	{
	UnitTest tester = new UnitTest();

	if (0 == args.length)
	{
		showErrParam();
	}
	String srcFileName = args[0];
	String destFileName = "";
	String type = "";
	for(int i=0; i<args.length; i++) {
		if(args[i].equals("-o") && (i+1) < args.length) {
			destFileName = args[i+1];
		}else if(args[i].equals("-t") && (i+1) < args.length) {
			type = args[i+1];
		}
	}
	if(srcFileName.equals("") || destFileName.equals("") || type.equals("")) {
		showErrParam();
	}

	if(type.equals("HTML")) {
		runHTMLTest(srcFileName, destFileName);
	}else if(type.equals("REFLECTIVE")){
		runReflectiveTest(srcFileName, destFileName);
	}
	
	return;
	}
private static void runReflectiveTest(String destFileName, String destFileName2) {
	// TODO Auto-generated method stub
	
}
}