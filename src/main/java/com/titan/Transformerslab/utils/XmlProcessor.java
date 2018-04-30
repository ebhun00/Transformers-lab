package com.titan.Transformerslab.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.titan.Transformerslab.domain.RoutePlannerStoreShiftInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlProcessor {

	public static String xmlFile2String(String fileName) {
		String st = null;
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			InputSource is = new InputSource(fileName);
			Document document = docBuilderFactory.newDocumentBuilder().parse(is);
			StringWriter sw = new StringWriter();
			Transformer serializer = TransformerFactory.newInstance().newTransformer();
			serializer.transform(new DOMSource(document), new StreamResult(sw));
			st = sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	public static void xmlEnricher(String fileName) {
		String filename = "C:\\customInfo\\development\\exp\\Transformers-lab\\src\\main\\resources\\eom-data\\eom.xml";
		String add = "<student><name>XXXXXX</name><gender>XXXXX</gender></student>";
		String xmlString = xmlFile2String(filename);
		StringBuilder both = new StringBuilder(xmlString).insert(xmlString.indexOf("<teacher>"), add);
		System.out.println(both);
	}

	public static void main(String[] args) {
		String filename = "C:\\customInfo\\development\\exp\\Transformers-lab\\src\\main\\resources\\RP-data\\DocRouteDetail-RP.xml";

		String json = XmlProcessor.testXMLToJSON(xmlFile2String(filename));
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
			RoutePlannerStoreShiftInfo routeInfo = objectMapper.readValue(json, RoutePlannerStoreShiftInfo.class);
			System.out.println(routeInfo.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	 static String testXMLToJSON(String xml) {
		String jsonResult = null;
		try {
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.registerModule(module);
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			Object map = xmlMapper.readValue(xml, GenericObject.class);
			String json = mapper.writeValueAsString(map);
			System.out.println("conversion completed");
			log.info(json);
			jsonResult = json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonResult;
	}

	@JsonDeserialize(using = CustomDeserializer.class)
	public class GenericObject {
	}

	@SuppressWarnings("deprecation")
	public static class CustomDeserializer extends UntypedObjectDeserializer {
		private static final long serialVersionUID = -4628994110702279382L;

		protected Object mapObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
			JsonToken t = jp.getCurrentToken();
			if (t == JsonToken.START_OBJECT) {
				t = jp.nextToken();
			}
			// minor optimization; let's handle 1 and 2 entry cases separately
			if (t == JsonToken.END_OBJECT) { // and empty one too
												// empty map might work; but caller may want to modify... so better just
												// give small modifiable
				return new LinkedHashMap<String, Object>(2);
			}
			String field1 = jp.getCurrentName();
			jp.nextToken();
			Object value1 = deserialize(jp, ctxt);
			if (jp.nextToken() == JsonToken.END_OBJECT) { // single entry; but we want modifiable
				LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>(2);
				value1 = handleMaultipleValue(result, field1, value1);
				result.put(field1, value1);
				return result;
			}
			String field2 = jp.getCurrentName();
			jp.nextToken();
			Object value2 = deserialize(jp, ctxt);
			if (jp.nextToken() == JsonToken.END_OBJECT) {
				LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>(4);
				value1 = handleMaultipleValue(result, field1, value1);
				result.put(field1, value1);
				value2 = handleMaultipleValue(result, field2, value2);
				result.put(field2, value2);
				return result;
			}
			// And then the general case; default map size is 16
			LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
			value1 = handleMaultipleValue(result, field1, value1);
			result.put(field1, value1);
			value2 = handleMaultipleValue(result, field2, value2);
			result.put(field2, value2);
			do {
				String fieldName = jp.getCurrentName();
				jp.nextToken();
				Object value = deserialize(jp, ctxt);
				value = handleMaultipleValue(result, fieldName, value);
				result.put(fieldName, value);
			} while (jp.nextToken() != JsonToken.END_OBJECT);
			return result;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private Object handleMaultipleValue(Map<String, Object> map, String key, Object value) {
			if (!map.containsKey(key)) {
				return value;
			}

			Object originalValue = map.get(key);
			if (originalValue instanceof List) {
				((List) originalValue).add(value);
				return originalValue;
			} else {
				ArrayList newValue = new ArrayList();
				newValue.add(originalValue);
				newValue.add(value);
				return newValue;
			}
		}

	}

}