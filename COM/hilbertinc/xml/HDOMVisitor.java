package COM.hilbertinc.xml;

import org.w3c.dom.*;
import com.ibm.xml.parser.*;

/**
 * This interface describes the basic interface into traversing
 * a Document Object Model (XML) tree.
 */
 
public interface HDOMVisitor
	{
	
/**
 * This returns false with the visitor is no longer interested in
 * traversing the tree
 *
 * @return boolean
 */
boolean continueTraversal();
/**
 * This is called when a Document node is encountered in the DOM
 * tree
 *
 * @param document org.w3c.dom.Document
 */
void processDocumentEpilog(Document document)
	throws Exception;
/**
 * This is called when a Document node is encountered in the DOM
 * tree
 *
 * @param document org.w3c.dom.Document
 */
void processDocumentProlog(Document document)
	throws Exception;
/**
 * Called to handle processing of the DTD node information
 */
void processDocumentType(DocumentType dtd)
	throws Exception;
/**
 * This is called post-order (after the children are
 * processed) in a recursive traversal of a DOM tree.  The return
 * value indicates if the traversal should continue.  A return
 * value of 'true' will continue to traverse the tree
 *
 * @param element org.w3c.dom.Element
 */
void processElementEpilog(Element node)
	throws Exception;
/**
 * This is called pre-order (before the children are
 * processed) in a recursive traversal of a DOM tree.  The return
 * value indicates if the traversal should continue.  A return
 * value of 'true' will continue to traverse the tree
 *
 * @param element org.w3c.dom.Element
 */
void processElementProlog(Element node)
	throws Exception;
/**
 * This is called to process Text nodes in the DOM tree
 * @param text org.w3c.dom.Text
 */
void processText(Text text)
	throws Exception;
}