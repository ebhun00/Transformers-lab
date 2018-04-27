package com.titan.Transformerslab.service;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Getter
@Setter
@ConfigurationProperties("eom_service")
public class EomTokenService {

	private String tokenUrl;

	private String tokenNsUri;
	
	private String user;
	
	private String pwd;
	
	private String eomToken;
	
	@Scheduled(fixedDelay=5400000)
	public String getToken() {
		String soapAction = "";
		return callSoapWebService(tokenUrl, soapAction);
	}
	
	private  String callSoapWebService(String soapEndpointUrl, String soapAction) {
		String token = null;
		try {

			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

			 token = soapResponse.getSOAPBody().getElementsByTagName("return").item(0).getFirstChild().getNodeValue();
			log.info("token :   "+ token);
			System.out.println("token :   "+ token);
			soapConnection.close();
			
		} catch (Exception e) {
			System.err.println(
					"\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
			e.printStackTrace();
		}
		this.eomToken = token;
		return token;
	}
	private  void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String myNamespace = "sec";

		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration(myNamespace, tokenNsUri);

		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("getAuthToken", myNamespace);
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("arg0");
		soapBodyElem1.addTextNode(user);
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("arg1");
		soapBodyElem2.addTextNode(pwd);
	}
	
	private  SOAPMessage createSOAPRequest(String soapAction) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();

		createSoapEnvelope(soapMessage);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);

		soapMessage.saveChanges();

		soapMessage.writeTo(System.out);

		return soapMessage;
	}

}
