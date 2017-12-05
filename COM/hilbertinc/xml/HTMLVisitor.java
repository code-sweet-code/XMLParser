package COM.hilbertinc.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class HTMLVisitor implements HDOMVisitor {
	private Writer writer;
	
	public HTMLVisitor(String filepath) throws IOException {
		writer = new FileWriter(filepath);
	}
	
	public Writer getWriter() {
		return writer;
	}
	
	public void flush() throws IOException
	{
		getWriter().flush();
		return;
	}
	
	@Override
	public boolean continueTraversal() {
		return true;
	}

	@Override
	public void processDocumentEpilog(Document document) throws Exception {
		Writer xml = getWriter();
		xml.write("<h4>");
		xml.write("&lt/");
		xml.write("Document epilog");
		xml.write("&gt");
		xml.write("</h4>");
		xml.write("\n");
		return;
	}

	@Override
	public void processDocumentProlog(Document document) throws Exception {
		Writer xml = getWriter();
		xml.write("<h4>");
		xml.write("&lt/");
		xml.write("Document prolog");
		xml.write("&gt");
		xml.write("</h4>");
		xml.write("\n");
		return;
	}

	@Override
	public void processDocumentType(DocumentType dtd) throws Exception {
		Writer xml = getWriter();
		xml.write("<h1>");
		xml.write("&lt!DOCTYPE ");
		xml.write(dtd.getName());
		xml.write(" SYSTEM ... &gt");
		xml.write("</h1>");
		xml.write("\n");
		return;
	}

	@Override
	public void processElementEpilog(Element node) throws Exception {
		Writer xml = getWriter();
		xml.write("<h4>");
		xml.write("&lt/");
		xml.write(node.getNodeName());
		xml.write("&gt");
		xml.write("</h4>");
		xml.write("\n");
		return;
	}

	@Override
	public void processElementProlog(Element node) throws Exception {
		Writer xml = getWriter();
		xml.write("<h4>");
		xml.write("&lt");
		xml.write(node.getTagName());
		xml.write("&gt");
		xml.write("</h4>");
		xml.write("\n");
		return;	
	}

	@Override
	public void processText(Text text) throws Exception {
		Writer xml = getWriter();
		xml.write("<h5>");
		xml.write(HDOMUtil.trim(text));
		xml.write("</h5>");
		xml.write("\n");
		return;
	}

}
