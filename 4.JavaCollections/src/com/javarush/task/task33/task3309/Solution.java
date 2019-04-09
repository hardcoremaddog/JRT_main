package com.javarush.task.task33.task3309;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;

/*
Комментарий внутри xml
*/
public class Solution {

	public static String toXmlWithComment(Object obj, String tagName, String comment) throws XMLStreamException, JAXBException {

		StringWriter result = new StringWriter();
		XMLStreamWriter xmlStreamWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(result);

		ContentHandler contentHandler = new ContentHandler(xmlStreamWriter, tagName, comment);

		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();


		marshaller.marshal(obj, contentHandler);

		return result.toString();
	}



	public static void main (String[]args) throws IOException, JAXBException, XMLStreamException {
		System.out.println(toXmlWithComment(new First(), "first", "it's a comment"));
	}
}



