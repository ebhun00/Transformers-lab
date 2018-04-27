package com.titan.Transformerslab.utils;

import java.io.StringWriter;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;

public class SoapMessageUtils {

	public static void main(String[] args) {
		String xml = "<school><teacher><name>XXXXX</name><gender>XXXX</gender></teacher></school>";
		String add = "<student><name>XXXXXX</name><gender>XXXXX</gender></student>";
		StringBuilder both = new StringBuilder(xml).insert(xml.indexOf("<teacher>"), add);
		System.out.println(both);
	}

	public static String getXmlString(SOAPMessage soapResponse) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory.newInstance().newTransformer().transform(new DOMSource(soapResponse.getSOAPPart()),
					new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}

		return sw.toString();
	}

	public static String getOrderInputXml(String orderNumber) {
		String request = "<![CDATA[<tXML xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"getCustomerOrderDetailsinput.xsd\">\r\n"
				+ "                                                  <Header>\r\n"
				+ "                                                    <Source>Source</Source>\r\n"
				+ "                                                    <Action_Type>update</Action_Type>\r\n"
				+ "                                                    <Message_Type>getCustomerOrder</Message_Type>\r\n"
				+ "                                                    <Company_ID>70</Company_ID>\r\n"
				+ "                                                  </Header>\r\n"
				+ "                                                  <Message>\r\n"
				+ "                                                    <EntityType>Customer Order</EntityType>\r\n"
				+ "                                                    <GetCustomerOrderDetails>                                        \r\n"
				+ "                                                      <OrderNumber>%S</OrderNumber>\r\n"
				+ "                                                      <CustomerInfo/>\r\n"
				+ "                                                    </GetCustomerOrderDetails>\r\n"
				+ "                                                  </Message>\r\n"
				+ "                                                </tXML>\r\n"
				+ "                                                ]]>";

		String modified = StringUtils.replace(request, "%S", orderNumber);

		return modified;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * System.out.println(getOrderInputXml("64590")); }
	 */
}
