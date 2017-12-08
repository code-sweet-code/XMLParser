package COM.hilbertinc.xml;

import java.util.Stack;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DocIterator implements Iterator {
	private Stack<Node> parentStack;
	private Node pointer;
	public DocIterator(Document doc) {
		parentStack = new Stack<Node>();
		pointer = doc.getDocumentElement();
	}
	
	public Node next() {
		Node curr = pointer;
		preOrder();
		return curr;
	}
	
	private void preOrder() {
		if(pointer == null) return;
		if(pointer.hasChildNodes()) {
			parentStack.push(pointer);
			pointer = pointer.getFirstChild();
		}else if(pointer.getNextSibling() != null){
			pointer = pointer.getNextSibling();
		}else {
			while(true) {
				if(!parentStack.isEmpty()) {
					pointer = parentStack.pop();
					if(pointer.getNextSibling() != null) {
						pointer = pointer.getNextSibling();
						break;
					}
				}else {
					pointer = null;
					break;
				}
			}
		}
	}

	@Override
	public boolean hasNext() {
		if(pointer != null) {
			return true;
		}
		return false;
	}
}
