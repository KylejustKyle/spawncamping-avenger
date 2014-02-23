import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class LevelParser {
	public LevelParser(String levelFile) throws SAXException, IOException, ParserConfigurationException {
		XMLReader p = XMLReaderFactory.createXMLReader();
		p.setContentHandler(new LevelXMLHandler());
		p.parse(levelFile);
		
	}

}
