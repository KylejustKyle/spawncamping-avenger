import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class LevelXMLHandler extends DefaultHandler {
	public void startDocument() {
		// document start parse logic here
	}
	public void endDocument() {
		// document end parse logic here
	}
	public void startElement(String nameSpaceURI, String localName, String qName, Attributes atts) {
		System.out.print("<" + qName + ">");
	}
	public void endElement(String namespaceURI, String localName, String qName) {
		System.out.print("</" + qName + ">");		
	}
	public void characters(char[] ch, int start, int length) {
		for ( int i = start ; i < (start + length) ; i++ ) {
			System.out.print(ch[i]);
		}
	}
}
