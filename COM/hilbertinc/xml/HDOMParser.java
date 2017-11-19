package COM.hilbertinc.xml;

import java.io.*;
import COM.hilbertinc.base.*;
import COM.hilbertinc.xml.*;
import com.ibm.xml.parser.*;
import org.w3c.dom.*;

/**
 * This will parse XML for database interaction (i.e. the DTD of db.dtd)
 * and generate SQL and Java classes for access the persistent storage
 */
public class HDOMParser 
{
	private Document document;
/**
 * HXmlParser constructor comment.
 */
public HDOMParser()
	{
	super();
	}
/**
 * Access the DOM document that is the result of the parsed XML
 * document.
 *
 * @return org.w3c.dom.Document
 */
public Document getDocument() 
	{
	return document;
	}
/**
 * This will parse the XML data from the input stream passed
 *
 * @param xmlstream InputStream
 */
public void parse(InputStream xmlstream)
	throws HException
	{
	Parser parser = new Parser("XML from stream");
	setDocument(parser.readStream(xmlstream));
	return;
	}
/**
 * This will parse the XML data from the input stream passed
 *
 * @param xmlstream InputStream
 */
public void parse(InputStream xmlstream, String name)
	throws HException
	{
	Parser parser = new Parser(name);
	setDocument(parser.readStream(xmlstream));
	return;
	}
/**
 * This is a convenience method that will build a FileInputStream from the
 * file name passed and call the parser on the stream
 *
 * @param fileName java.lang.String
 */
public void parse(String fileName)
	throws HException
	{
	try
		{
		FileInputStream stream = new FileInputStream(fileName);
		parse(stream, fileName);
		}
	catch(IOException exception)
		{
		HException hException = new HException(exception, "I/O error parsing file: "+fileName);
		hException.setClassName("HDOMParser");
		hException.setMethod("parse(String)");
		throw hException;
		}
	return;
	}
/**
 * This does the actual work of traversing the DOM tree.  This is called
 * by the public traverse(...) method with the root as the node.  We
 * terminate the traversal as soon as the visitor returns a 'false' or
 * the entire tree has been traversed
 *
 * @param visitor COM.hilbertinc.xml.HDOMVisitor
 * @param node org.w3c.dom.Element
 */
protected void processNode(HDOMVisitor visitor, Node node)
	throws Exception
	{

	// --- Document nodes ---
	
	if (node instanceof Document)
		{
		Document document = (Document)node;
		visitor.processDocumentProlog(document);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to

		if (document.hasChildNodes())
			{
			NodeList children = document.getChildNodes();
			int count = children.getLength();
			for (int i = 0; i < count; ++i)
				{
				Node child = (Node)children.item(i);
				processNode(visitor, child);
				if (!visitor.continueTraversal())
					return;  // Stop if we are asked to
				}
			} // if children
		visitor.processDocumentEpilog(document);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to
		}
	else

	// --- Element nodes ---

	if (node instanceof Element)
		{
		Element element = (Element)node;
		visitor.processElementProlog(element);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to

		if (element.hasChildNodes())
			{
			NodeList children = element.getChildNodes();
			int count = children.getLength();
			for (int i = 0; i < count; ++i)
				{
				Node child = (Node)children.item(i);
				processNode(visitor, child);
				if (!visitor.continueTraversal())
					return;  // Stop if we are asked to
				}
			} // if children
		visitor.processElementEpilog(element);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to
		}
	else

	// --- Text nodes ---

	if (node instanceof Text)
		{
		visitor.processText((Text)node);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to
		}
			
	else

	// --- DTD nodes ---

	if (node instanceof DocumentType)
		{
		visitor.processDocumentType((DocumentType)node);
		if (!visitor.continueTraversal())
			return;  // Stop if we are asked to
		}
		
	// --- Unknown node type ---
	
	else
		{
		Class nodeClass = node.getClass();
		System.out.println("Unsupported DOM node type in HDOMParser::processNode. Type is: "+nodeClass.getName());
		}
	return;
	}
/**
 * Access the DOM document that is the result of the parsed XML
 * document.
 *
 * @param dom org.w3c.dom.Document
 */
public void setDocument(Document dom) 
	{
	document = dom;
	return;
	}
/**
 * This will traverse the DOM tree and perform actions on a node within
 * the tree
 *
 * @param visitor HDOMVisitor
 */
public void traverse(HDOMVisitor visitor)
	throws Exception
	{
	if (null == getDocument())
		return; // Nothing to traverse

	if (null == visitor)
		return; // Nothing to do once we get to a node, so we might as well leave now

	NodeList children = getDocument().getChildNodes();
	int count = children.getLength();
	for (int i = 0; i < count; ++i)
		{
		Node child = (Node)children.item(i);
		processNode(visitor, child);
		}
	return;
	}	
}