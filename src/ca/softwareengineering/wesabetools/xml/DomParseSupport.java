package ca.softwareengineering.wesabetools.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.softwareengineering.wesabetools.model.TransactionStore;
import ca.softwareengineering.wesabetools.model.WesabeTransaction;

public class DomParseSupport {

	public static void loadTransactions(File f, TransactionStore store) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(f);
		doc.getDocumentElement().normalize();
		
		NodeList txaction_nl = doc.getElementsByTagName("txaction");
		for (int i = 0; i < txaction_nl.getLength(); i++) {
			Node n = txaction_nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element tx_element = (Element) n;
				
				// common top-level fields
				boolean isTransfer = hasChild("transfer", tx_element);
				String date = getChildValue("date", tx_element);
				String amount = getChildValue("amount", tx_element);

				// begin tag extraction
				NodeList nlTags = tx_element.getElementsByTagName("tag");
				List<String> transactionTags = new ArrayList<String>();
				for (int j = 0; j < nlTags.getLength(); j++) {
					Node n_tag = nlTags.item(j);
					if (n_tag.getNodeType() == Node.ELEMENT_NODE) {
						String tag_name = getChildValue("name", (Element) n_tag);
						transactionTags.add(tag_name);
					}
				}
				// end tag extraction
				
				WesabeTransaction tx = WesabeTransaction.build(date, amount, transactionTags, isTransfer);
				store.addTransaction(tx);
			}
		}
	}

	private static boolean hasChild(String tag, Element base) {
		return base.getElementsByTagName(tag).getLength() > 0;
	}

	private static String getChildValue(String tag, Element base) {
		NodeList nl = base.getElementsByTagName(tag);
		Node nValue = nl.item(0).getChildNodes().item(0);
		return nValue.getNodeValue();
	}

}
