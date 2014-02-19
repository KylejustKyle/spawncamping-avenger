import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class LevelParser {
	public LevelParser(String levelFile) throws SAXException, IOException, ParserConfigurationException {
		File fXmlFile = new File(levelFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
		doc.getDocumentElement().normalize();
	 
		NodeList nList = doc.getElementsByTagName("events");
		Node x = nList.item(0);
		if(x == null) {
			System.out.println("XXX");
		}
		
		System.out.println("SSSS");
		System.out.println();
	}
}
