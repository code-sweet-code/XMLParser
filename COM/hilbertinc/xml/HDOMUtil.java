package COM.hilbertinc.xml;

import java.util.*;
import org.w3c.dom.*;
/**
 * This is a collection of utility methods on the Document Object Model
 * document
 */
public class HDOMUtil
	{
	
/**
 * Construct a utility class
 */
public HDOMUtil()
	{
	super();
	}
/**
 * Return the first Element instance among the child nodes of
 * the element passed.  If there are none, this will return a
 * null reference
 *
 * @return org.w3c.dom.Element
 * @param element org.w3c.dom.Element
 */
public static Element firstChildElement(Element element)
	{
	if (element.hasChildNodes())
		{
		NodeList children = element.getChildNodes();
		int count = children.getLength();
		for (int i = 0; i < count; ++i)
			{
			Node node = (Node)children.item(i);			
			if (node instanceof Element)
				return (Element)node;
			} // for
		} // if
	return null;
	}
/**
 * This is a utility method that will returns a boolean
 * indicating of the node is an Element and the name
 * matches the tag passed
 *
 * @return boolean
 * @param node org.w3c.dom.Node
 * @param tag java.lang.String
 */
private static boolean isMatchingNode(Node node, String tag)
	{
	if (node instanceof Element)
		if (node.getNodeName().equals(tag))
			return true;
	return false;
	}
/**
 * This will return the next sibling element (skipping Text elements
 * along the way)
 *
 * @return org.w3c.dom.Element
 * @param element org.w3c.dom.Element
 */
public static Element nextElement(Element element)
	{
	Node sibling = element.getNextSibling();
	while (null != sibling)
		{
		if (sibling instanceof Element)
			return (Element)sibling; // Found one.  We're outta here.
		sibling = sibling.getNextSibling();
		}	
	return null;
	}
/**
 * This returns the text of the first Text node that is a child of the
 * element passed.
 *
 * @return java.lang.String
 * @param element org.w3c.dom.Element
 */
public static String textFor(Element element)
	{
	// Look at the children of this tag element.  There should be
	// one or more Text nodes.

	if (!element.hasChildNodes())
		return "";
	
	NodeList list = element.getChildNodes();
	int count = list.getLength();
	for (int i = 0; i < count; ++i)
		{
		Node node = list.item(i);
		if (node instanceof Text)
			{
			String value = trim((Text)node);
			if (!("".equals(value)))
				return value; // Return the trim()'d text of the first child text node
			}
		} // for i...
	return "";
	}
/**
 * This will look at the children of the element that have a
 * tag name passed.  It will then look at the child text nodes
 * and return a trim'ed version of the first Text node as the
 * text of the tag. For example given the XML source:
 *
 *    <person>
 *       Bill E. Bob
 *    </person>
 *
 *
 *    "Bill E. Bob" = textForFirstTag(element, "person");
 *
 * @return java.lang.String
 * @param element org.w3c.dom.Element
 * @param tag java.lang.String
 */
public static String textForFirstTag(Element element, String tag)
	{
	if (!element.hasChildNodes())
		return ""; // Should never happen if we are passed a valid Element node

	NodeList tagList = element.getChildNodes();
	int tagCount = tagList.getLength();
	if (0 == tagCount) // No tags
		return "";

	for (int j = 0; j < tagCount; ++j)
		{
		Node tagNode = tagList.item(j);
		if (isMatchingNode(tagNode, tag))
			{
						
			// Look at the children of this tag element.  There should be
			// one or more Text nodes.

			NodeList list = tagNode.getChildNodes();
			int count = list.getLength();
			for (int i = 0; i < count; ++i)
				{
				Node node = list.item(i);
				if (node instanceof Text)
					{
					String value = trim((Text)node);
					if (!("".equals(value)))
						return value; // Return the trim()'d text of the first child text node
					}
				} // for i...
			} // if match
		} // for j...
	return "";
	}
/**
 * This will look at the children of the element that have a
 * tag name passed.  It will then look at the child text nodes
 * and return a trim'ed version of each Text node as the
 * text of the tag. For example given the XML source:
 *
 *    <person>
 *       Bill E. Bob
 *    </person>
 *    <person> Ralph
 *    Kramden
 *    </person>
 *
 *    String[] list = textForTag(element, "person");
 *    list[0] = "Bill E. Bob"    
 *    list[1] = "Ralph\nKramden"
 *
 * @return java.lang.String
 * @param element org.w3c.dom.Element
 * @param tag java.lang.String
 */
public static String[] textForTag(Element element, String tag)
	{
	if (!element.hasChildNodes())
		return new String[0]; // Should never happen if we are passed a valid Element node

	NodeList tagList = element.getChildNodes();
	int tagCount = tagList.getLength();
	if (0 == tagCount) // No tags by that name
		return new String[0];

	Vector strings = new Vector(); // Same up all the text strings
		
	for (int j = 0; j < tagCount; ++j)
		{
		Node tagNode = tagList.item(j);
		
		if (isMatchingNode(tagNode, tag))
			{				
			// Look at the children of this tag element.  There should be
			// one or more Text nodes.

			NodeList list = tagNode.getChildNodes();
			int count = list.getLength();
			for (int i = 0; i < count; ++i)
				{
				Node node = list.item(i);
				if (node instanceof Text)
					{
					String value = trim((Text)node);
					if (!("".equals(value)))
						strings.addElement(value);
					}
				} // for i...
			} // if match
		} // for j...

	String[] stringArray = new String[strings.size()];
	strings.copyInto(stringArray);
	return stringArray;
	}
/**
 * This will trim trailing and leading white space from a Text
 * node in a DOM structure.
 *
 * @return java.lang.String
 * @param textNode org.w3c.dom.Text
 */
public static String trim(Text textNode)
	{
	String text = textNode.getNodeValue();
	if (null == text)
		return "";
		
	char[] array = text.toCharArray();
	int length = array.length;
	if (0 == length)
		return "";
	
	// Strip leading white space

	int start = 0;
	boolean isWhite = Character.isWhitespace(array[start]);
	while(isWhite)
		{
		++start;
		if (start >= length)
			return ""; // It was all white
		isWhite = Character.isWhitespace(array[start]);
		}		
	
	// Strip trailing white space
	
	int end = length - 1;
	isWhite = Character.isWhitespace(array[end]);
	while(isWhite)
		{
		--end;
		if (end <= 0)
			return ""; // It was all white
		isWhite = Character.isWhitespace(array[end]);
		}		

	// If we get here, we had some non-whitespace
		
	return new String(array,start,end-start+1);
	}
}