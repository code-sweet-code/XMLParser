package COM.hilbertinc.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.w3c.dom.*;

public class ReflectiveHTMLVisitor extends AbstractReflectiveVisitor {
	private Writer writer;
	public ReflectiveHTMLVisitor(String filename) throws IOException {
		writer = new FileWriter(filename);
	}
	
	public void visitElement(Element element) {
		//TODO
		System.out.println("element: "+element.getTagName());
	}
	
	public void visitText(Text text) {
		//TODO
		System.out.println("text: ");
	}
	
	public void visitAttr(Attr attr) {
		//TODO
	}
	
	public void visitDocument(Document document) {
		//TODO
		System.out.println("document: ");
	}
	
	public void visitNode(Node node) {
		//TODO
		System.out.println("Node: "+node.getNodeName());
	}
	
	public void visitDocumentType(DocumentType dt) {
		//TODO
		System.out.println("type: "+dt.getName());
	}
}
