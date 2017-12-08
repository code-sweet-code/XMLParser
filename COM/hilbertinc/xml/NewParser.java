package COM.hilbertinc.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.*;

import com.ibm.xml.parser.Parser;

public class NewParser {
	private Document document;
	
	public Document getDocument() {
		return document;
	}
	
	public void setDocument(Document dom) {
		document = dom;
		return;
	}
	
	public void traverse(Visitor visitor) {
		if(document != null && visitor != null) {
			visitor.visit(document.getDoctype());
			Iterator iter = new DocIterator(document);
			while(iter.hasNext()) {
				visitor.visit(iter.next());
			}
		}
	}

	
	public void parse(String fileName) throws FileNotFoundException {
		FileInputStream stream = new FileInputStream(fileName);
		Parser parser = new Parser(fileName);
		setDocument(parser.readStream(stream));
	}
}
