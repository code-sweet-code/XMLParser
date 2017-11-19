package COM.hilbertinc.base;

import java.util.*;
import java.io.*;

/**
 * This is the base exception class for all application-specific
 * problems within Hilbert applications
 * 
 */
public class HException 
	extends Exception
{
	private Throwable originalException = null;
	private Vector    messageStack      = new Vector();
	private boolean   recoverable       = false;
	private int       errorCode         = 0;
	private String    className = "";
	private String    method = "";
/**
 * TException constructor comment.
 */
public HException()
	{
	super();
	}
/**
 * Construct an exception with message text
 *
 * @param s java.lang.String
 */
public HException(String message)
	{
	super(message);
	addMessage(message);
	}
/**
 * Construct an exception with message text and an original exception
 *
 * @param message java.lang.String
 * @param exception java.lang.Throwable
 */
public HException(Throwable exception, String message)
	{
	super(message);
	setOriginalException(exception);

	String omessage = exception.getMessage();
	if ((omessage != null) && (!omessage.equals("")))
		addMessage(omessage);
	
	addMessage(message);
	}
/**
 * Add another message to the stack
 *
 * @param message java.lang.String
 */
public void addMessage(String message)
	{
	getMessageStack().addElement(message);
	return;
	}
/**
 * Access the name of the class that threw this exception
 *
 * @return java.lang.String
 */
public String getClassName() 
	{
	return className;
	}
/**
 * Access the Hilbert error code that corresponds to this exception
 *
 * @return int
 */
public int getErrorCode()
	{
	return errorCode;
	}
/**
 * This will return the contents of the message stack as an array of
 * strings
 *
 * @return java.lang.String[]
 */
public String[] getMessages()
	{
	String[] array = new String[getMessageStack().size()];
	getMessageStack().copyInto(array);
	return array;
	}
/**
 * This enables exception handlers to append additional diagnostic messages
 * to the exception.  If there is a series of catch/rethrow as the error
 * percolates up the call chain, each catch block can put in diagnostics.
 *
 * @return java.util.Vector
 */
protected Vector getMessageStack()
	{
	return messageStack;
	}
/**
 * Access the method within the class that threw this exception
 *
 * @return java.lang.String
 */
public String getMethod() 
	{
	return method;
	}
/**
 * This contains the java.lang.Throwable subclass that caused the Hilbert
 * exception to be thrown.  This may be null in many cases, but it gives
 * exception handlers the opportunity to rethrow the original exception or
 * to provide additional diagnostics
 *
 * @return java.lang.Throwable
 */
public Throwable getOriginalException()
	{
	return originalException;
	}
/**
 * This indicates if the problem is transient and possibly will correct
 * itself without intervention.  Examples include a service at the other
 * end of a socket that is not available.  Non-recoverable errors would
 * include a missing class such as a JDBC driver or a missing configuration
 * file.
 *
 * @return boolean
 */
public boolean isRecoverable()
	{
	return recoverable;
	}
/**
 * Print the exception information to standard error
 */
public void print() 
	{
	print(new PrintWriter(System.err));
	return;
	}
/**
 * This will print all of the information about the exception to the
 * writer that was passed to us
 *
 * @param writer java.io.PrintWriter
 */
public void print(PrintWriter writer) 
	{
	writer.print("Recoverable: ");
	if (isRecoverable())
		writer.println("Yes");
	else
		writer.println("No");

	if (0 != getErrorCode())
		{
		writer.print("Error code: ");
		writer.println(getErrorCode());
		}

	if ("".equals(getClassName()))
		{
		// We only have a method name
		
		if (!"".equals(getMethod()))
			{
			writer.print("Exception thrown from method: ");
			writer.println(getMethod());
			}		
		}
	else
		{
		// We have a class name...
		
		writer.print("Exception thrown from: ");
		writer.print(getClassName());

		if (!"".equals(getMethod()))
			{
			// ... and a method name
			writer.print("::");
			writer.print(getMethod());
			}
		writer.println();
		} // className

	writer.println("Message stack: ");
	printMessages(writer);
	writer.println();

	writer.println("Stack trace: ");
	printStackTrace(writer);
	writer.println();

	Throwable original = getOriginalException();
	if (null != original)
		{
		writer.println("Original exception causing this exception:");
		Class throwableClass = original.getClass();
		writer.print("Class: ");
		writer.println(throwableClass.getName());
		writer.println("Stack trace: ");
		original.printStackTrace(writer);
		}
	writer.flush();
	return;
	}
/**
 * This is an overloaded convenience method that will dump the messages to the
 * standard error device
 */
public void printMessages()
	{
	printMessages(new PrintWriter(System.err));
	return;
	}
/**
 * Dump the contents of the messages to the writer
 *
 * @param writer java.io.PrintWriter
 */
public void printMessages(PrintWriter writer)
	{
	Enumeration e = getMessageStack().elements();
	while (e.hasMoreElements())
		{
		String text = (String)e.nextElement();
		writer.println(text);
		}
	return;
	}
/**
 * Access the name of the class that threw this exception
 *
 * @param name java.lang.String
 */
public void setClassName(String name) 
	{
	className = name;
	return;
	}
/**
 * Access the Hilbert error code that corresponds to this exception
 *
 * @param code int
 */
public void setErrorCode(int code)
	{
	errorCode = code;
	return;
	}
/**
 * Access the method within the class that threw this exception
 *
 * @param methodName java.lang.String
 */
public void setMethod(String name) 
	{
	method = name;
	return;
	}
/**
 * This contains the java.lang.Throwable subclass that caused the Hilbert
 * exception to be thrown.  This may be null in many cases, but it gives
 * exception handlers the opportunity to rethrow the original exception or
 * to provide additional diagnostics
 *
 * @param exception java.lang.Throwable
 */
public void setOriginalException(Throwable exception)
	{
	originalException = exception;
	return;
	}
/**
 * This indicates if the problem is transient and possibly will correct
 * itself without intervention.  Examples include a service at the other
 * end of a socket that is not available.  Non-recoverable errors would
 * include a missing class such as a JDBC driver or a missing configuration
 * file.
 *
 * @param canRecover boolean
 */
public void setRecoverable(boolean canRecover)
	{
	recoverable = canRecover;
	return;
	}
}