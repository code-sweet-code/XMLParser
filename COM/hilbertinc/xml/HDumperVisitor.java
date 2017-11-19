package COM.hilbertinc.xml;

import java.io.*;
import org.w3c.dom.*; 
/**
 * This is a utility class that demonstrates a simple visitor class
 * that is called via the traverse(...) method of a DOMTree.
 */
public class HDumperVisitor 
	implements HDOMVisitor 
{
	private Writer writer = null;
	private int    indent = 0;
/**
 * HDumper constructor comment.
 */
public HDumperVisitor() {
	super();
}
/**
 * Indicates when we are finished traversing the tree
 * @return boolean
 */
public boolean continueTraversal() 
	{
	return true;
	}
/**
 * Dump the buffered contents of the writer
 */
public void flush()
	throws IOException
	{
	getWriter().flush();
	return;
	}
/**
 * This will return an indent string of the proper length
 *
 * @return java.lang.String
 */
protected String getIndent() 
	{
	StringBuffer buffer = new StringBuffer();
	for (int i = 0; i < indent; ++i)
		buffer.append(' ');
	return buffer.toString();
	}
/**
 * Access the writer to which with dump the XML data.  Note that this will
 * lazy-construct a writer to the standard output if one isn't explicitly
 * set by the caller.
 *
 * @return java.io.Writer
 */
public Writer getWriter() 
	{
	if (null == writer)
		writer = new PrintWriter(System.out);
	return writer;
	}
/**
 * Increment the indent level one less tab stop
 */
protected void indentLess() 
	{
	indent -= 3;
	}
/**
 * Increment the indent level one tag stop
 */
protected void indentMore() 
	{
	indent += 3;
	}
/**
 * This is called after a Document node is found in the DOM
 * document tree
 */
public void processDocumentEpilog(org.w3c.dom.Document document)
	throws Exception 
	{
	getWriter().write("Document epilog\n");
	return;
	}
/**
 * This is called after a Document node is found in the DOM
 * document tree
 */
public void processDocumentProlog(org.w3c.dom.Document document)
	throws Exception
	{
	getWriter().write("Document prolog\n");
	return;
	}
/**
 * This will process a DTD node in the tree
 */
public void processDocumentType(DocumentType dtd)
	throws Exception
	{
	Writer xml = getWriter();
	xml.write("<!DOCTYPE ");
	xml.write(dtd.getName());
	xml.write(" SYSTEM ... \n ");
	return;
	}
/**
 * This will post-process an element node
 */
public void processElementEpilog(Element node) 
	throws Exception 
	{
	Writer xml = getWriter();
	indentLess();
	xml.write(getIndent());
	xml.write("</");
	xml.write(node.getNodeName());
	xml.write(">\n");
	return;	
	}
/**
 * Pre-process an element in the DOM tree
 */
public void processElementProlog(Element node) 
	throws Exception 
	{
	Writer xml = getWriter();
	xml.write(getIndent());
	xml.write("<");
	xml.write(node.getTagName());
	xml.write(">\n");
	indentMore();
	return;	
	}
/**
 * Handle text nodes in the document tree
 */
public void processText(org.w3c.dom.Text text)
	throws Exception
	{
	Writer xml = getWriter();
	xml.write(getIndent());
	xml.write(HDOMUtil.trim(text));
	xml.write("\n");
	return;
	}
/**
 * Access the writer object to which we write the XML data
 *
 * @param xmlWriter java.io.Writer
 */
public void setWriter(Writer xmlWriter) 
	{
	writer = xmlWriter;
	return;
	}
}
//HDumperVisitor.java Code Listing	02/08/00	1

