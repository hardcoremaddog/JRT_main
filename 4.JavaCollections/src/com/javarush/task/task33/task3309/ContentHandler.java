package com.javarush.task.task33.task3309;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class ContentHandler extends DefaultHandler {

	XMLStreamWriter xmlStreamWriter;
	String tagName;
	String comment;

	public ContentHandler(XMLStreamWriter xmlStreamWriter, String tagName, String comment) {
		this.xmlStreamWriter = xmlStreamWriter;
		this.tagName = tagName;
		this.comment = comment;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String data = new String(ch, start, length);

		try {
			if (data.matches(".*[<>&'\"].*")) {
				xmlStreamWriter.writeCData(data);
			} else {
				xmlStreamWriter.writeCharacters(data);
			}
		} catch (XMLStreamException e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		try {
			if (localName.equals(tagName)) {
				xmlStreamWriter.writeComment(comment);
			}

			xmlStreamWriter.writeStartElement(localName);
			for (int i = 0; i < attributes.getLength(); i++) {
				xmlStreamWriter.writeAttribute(
						attributes.getQName(i),
						attributes.getURI(i),
						attributes.getLocalName(i),
						attributes.getValue(i));
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		try {
			xmlStreamWriter.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startDocument() throws SAXException {
		try {
			xmlStreamWriter.writeDTD("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
}
