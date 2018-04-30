package com.titan.Transformerslab.service.Mappers;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.xsd.coImport.domains.ObjectFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class COImportXmlMapper {
	
	ObjectFactory of =new ObjectFactory();
	public com.titan.Transformerslab.xsd.coImport.domains.TXML.Header createHeader(com.titan.Transformerslab.xsd.coExport.domains.TXML.Header inputHeader) {
		com.titan.Transformerslab.xsd.coImport.domains.TXML.Header header = 
				(new ObjectFactory()).createTXMLHeader();
		header.setSource("Host");
		header.setActionType("Update");
		header.setReferenceID(inputHeader.getReferenceID());
		header.setMessageType("Customer Order");
		header.setCompanyID("70");
		header.setMsgLocale(new ObjectFactory().createTXMLHeaderMsgLocale("English (United States)"));
		return header;
	}

	
	public com.titan.Transformerslab.xsd.coImport.domains.TXML.Message createMessage(com.titan.Transformerslab.xsd.coExport.domains.TXML.Message inputMsg) {
	
		com.titan.Transformerslab.xsd.coImport.domains.TXML.Message message = 
				(new ObjectFactory()).createTXMLMessage();
		message.setOrder(createOrder(inputMsg));
		return message;
	}
	
	public com.titan.Transformerslab.xsd.coImport.domains.TXML.Message.Order createOrder(com.titan.Transformerslab.xsd.coExport.domains.TXML.Message inputMsg) {
		com.titan.Transformerslab.xsd.coImport.domains.TXML.Message.Order order= 
				(of).createTXMLMessageOrder();
		com.titan.Transformerslab.xsd.coExport.domains.TXML.Message.Order inputOrder = inputMsg.getOrder().get(0);
		order.setOrderNumber(inputOrder.getOrderNumber());
		order.setExternalOrderNumber(inputOrder.getExternalOrderNumber());
		order.setOrderType(of.createTXMLMessageOrderOrderType(inputOrder.getOrderType()));
		order.setOrderTotal(of.createTXMLMessageOrderOrderTotal(inputOrder.getOrderTotal().toString()));
		order.setOrderCurrency(of.createTXMLMessageOrderOrderCurrency(inputOrder.getOrderCurrency()));
		order.setOrderLines(getOrderLines(inputOrder.getOrderLines()));
		return order;
	}
	
	public com.titan.Transformerslab.xsd.coImport.domains.TXML.Message.Order.OrderLines 
	getOrderLines(com.titan.Transformerslab.xsd.coExport.domains.TXML.Message.Order.OrderLines orderLines){
		
		com.titan.Transformerslab.xsd.coImport.domains.TXML.Message.Order.OrderLines coImportOrderLines = 
				(of).createTXMLMessageOrderOrderLines();
		
		try {
			BeanUtils.copyProperties(coImportOrderLines, orderLines);
		} catch (Exception e) {
			log.error(e.getMessage());
		} 
		return coImportOrderLines;
	}
}
