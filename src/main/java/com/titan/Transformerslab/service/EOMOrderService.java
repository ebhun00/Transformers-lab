package com.titan.Transformerslab.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

import com.titan.Transformerslab.domain.AppConfigs;
import com.titan.Transformerslab.domain.OrderRouteInfo;
import com.titan.Transformerslab.domain.RPDataWrapper;
import com.titan.Transformerslab.domain.RPOrderDomain;
import com.titan.Transformerslab.domain.ResourceKey;
import com.titan.Transformerslab.domain.ShiftOrderDetails;
import com.titan.Transformerslab.repository.AppConfigsRepositoryImpl;
import com.titan.Transformerslab.repository.EOMRepository;
import com.titan.Transformerslab.service.Mappers.COImportXmlMapper;
import com.titan.Transformerslab.utils.DateUtils;
import com.titan.Transformerslab.xsd.coExport.domains.TXML;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EOMOrderService {

	@Autowired
	private EOMRepository eomRepository;
	@Autowired
	private OrderService orderService;

	@Autowired
	private COImportXmlMapper coImportXmlMapper;

	Map<String, ShiftOrderDetails> eomshiftOrders;

	/*
	 * @Autowired private Jaxb2Marshaller jaxbMarshallerCoImport;
	 */

	private RPDataWrapper wrapper;

	@Autowired
	private AppConfigsRepositoryImpl appConfigsRepository;

	@Autowired
	ApplicationContext context;

	@ServiceActivator(inputChannel = "rp-outbound-channel")
	public void generateOrdersForShift(RPDataWrapper wrapper) {
		this.wrapper = wrapper;
		ResourceKey resourceKey = this.wrapper.getResourceKey();
		int stopIdForUnrouted = 1;
		AppConfigs appConfigs = appConfigsRepository.findByKey("xmlApproach");
		List<String> stores = (List<String>) Arrays.asList(appConfigsRepository.findByKey("stores").getValue().split(","));

		if (stores.contains(resourceKey.getStoreNumber())) {
			try {
				eomshiftOrders = eomRepository.getShiftOrders(resourceKey.getStoreNumber(), resourceKey.getShift(),
						resourceKey.getDate());

				if (Boolean.getBoolean(appConfigs.getValue()))
					for (Map.Entry<String, ShiftOrderDetails> entry : eomshiftOrders.entrySet()) {
						String eomOrderXml = orderService.getOrderDetails(entry.getKey());
						MessageChannel channel = (MessageChannel) context.getBean("xml_objectInboundChannel");
						channel.send(MessageBuilder.withPayload(eomOrderXml).build());
					}
				else {
					for (Map.Entry<String, ShiftOrderDetails> entry : eomshiftOrders.entrySet()) {
						if (wrapper.getRpOrderInfoMap().containsKey(entry.getKey())) {
							eomRepository.updateOrderWithShiftDetails(wrapper.getRpOrderInfoMap().get(entry.getKey()));
						} else {
							eomRepository.updateOrderWithShiftDetails(orderUpdateDataForUnRoutedOrder(entry.getKey(),
									entry.getValue(), wrapper, stopIdForUnrouted++));
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}else {
			log.info("Store not supposed to process");
		}

	}

	public RPOrderDomain orderUpdateDataForUnRoutedOrder(String orderId, ShiftOrderDetails shiftOrder,
			RPDataWrapper rpOrderInfoMap, int stopIdForUnrouted) {
		ResourceKey resourceKey = rpOrderInfoMap.getResourceKey();
		Map<String, RPOrderDomain> rpRouteDetailsByOrder = rpOrderInfoMap.getRpOrderInfoMap();
		if (rpRouteDetailsByOrder.containsKey(orderId)) {
			return rpRouteDetailsByOrder.get(orderId);
		} else {
			OrderRouteInfo orderRouteInfo = new OrderRouteInfo(orderId, DateUtils.getCurrentDateIn_MMDDYYYY(),
					DateUtils.getCurrentDateIn_MMDDYYYY(), "00" + stopIdForUnrouted, "", "", "");
			return new RPOrderDomain("UR1" + resourceKey.getShift(), "1111111", resourceKey.getStoreNumber(),
					DateUtils.getCurrentDateIn_MMDDYYYY(), orderRouteInfo);
		}
	}

	@ServiceActivator(outputChannel = "xml_inboundChannel")
	private String sendMsgToChannel(String eomOrderXml) {
		return eomOrderXml;
	}

	@ServiceActivator(inputChannel = "outboundChannel")
	public void receiveMesssage(TXML txml) throws Exception {
		com.titan.Transformerslab.xsd.coImport.domains.TXML coImport = new com.titan.Transformerslab.xsd.coImport.domains.TXML();
		coImport.setHeader(coImportXmlMapper.createHeader(txml.getHeader()));
		coImport.setMessage(coImportXmlMapper.createMessage(txml.getMessage()));

		JAXBContext jaxbContext = JAXBContext.newInstance("com.titan.Transformerslab.xsd.coImport.domains");
		Marshaller marshaller = jaxbContext.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(coImport, System.out);
	}

}
