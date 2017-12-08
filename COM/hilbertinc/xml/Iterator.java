package COM.hilbertinc.xml;

import org.w3c.dom.Node;

public interface Iterator {
	public boolean hasNext();
	public Node next();
}