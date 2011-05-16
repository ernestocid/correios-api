package correiosapi;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WebsiteScrapper {

	private String URL;
	
	public WebsiteScrapper(String URL) {
		this.URL = URL;
	}

	public List<HashMap<String, String>> getStatusList() {
		String xml = extractCleanXMLFromURL();
		List<HashMap<String, String>> status = getStatusListFrom(xml);
		return status;
	}

	private String extractCleanXMLFromURL() {
		String xml = null;
		CleanerProperties props = new CleanerProperties();
		props.setOmitComments(true);
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		
		try {
			TagNode node = new HtmlCleaner(props).clean(new URL(URL));
			xml = new PrettyXmlSerializer(props).getAsString(node);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xml;
	}

	private List<HashMap<String, String>> getStatusListFrom(String xml) {
		List<HashMap<String, String>> statusList = new ArrayList<HashMap<String, String>>();
		NodeList nodeList = getNodeListFrom(xml);
		for (int i = 1; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				cleanNonElementNodes(node);
				NodeList childNodes = node.getChildNodes();
				HashMap<String, String> status = new HashMap<String, String>();

				String firstNode = childNodes.item(0).getTextContent();
				
				if(isDateFormat(firstNode)) {
					String date = childNodes.item(0).getTextContent().trim();
					String location = childNodes.item(1).getTextContent().trim();						
					String situation = childNodes.item(2).getTextContent().trim();
					
					status.put("date", date);
					status.put("location", location);
					status.put("situation", situation);
					
					statusList.add(status);
				} else {
					String description = childNodes.item(0).getTextContent().trim();
					statusList.get(i-2).put("description", description);
				}
				
			}
		}
		
		return statusList;
	}

	private NodeList getNodeListFrom(String xml) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		String expression = "/html/body/form/p/table/tbody/tr";
		try {
			XPathExpression xPathExpression = xPath.compile(expression);
			InputSource input = new InputSource();
			input.setCharacterStream(new StringReader(xml));
			
			NodeList nodeList = (NodeList) xPathExpression.evaluate(input, XPathConstants.NODESET);
			return nodeList;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isDateFormat(String node) {
		Pattern dateHourPattern = Pattern.compile("\\d{2}\\/\\d{2}\\/\\d{4} \\d{2}:\\d{2}");
		Matcher dateMatcher = dateHourPattern.matcher(node);
		return dateMatcher.matches();
	}

	private void cleanNonElementNodes(Node node) {
		for(int j = 0; j < node.getChildNodes().getLength(); j++) {
			if(node.getChildNodes().item(j).getNodeType() != Node.ELEMENT_NODE) {
				node.removeChild(node.getChildNodes().item(j));
			}
		}
	}

}
