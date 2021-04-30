package com.github.leeonardoo.algestruturas.html;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HTMLParser extends DefaultHandler {

    private File htmlFile;

    public void setHtmlFile(File newFile) {
        htmlFile = newFile;
    }

    public void parseFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(htmlFile));
            String str;

            while ((str = reader.readLine()) != null) {
                contentBuilder.append(str);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String htmlText = contentBuilder.toString();
        System.out.println(htmlText);

        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser;

        try {
            parser = parserFactory.newSAXParser();

            parser.parse(htmlFile, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("Start document");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("End document");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        System.out.println("Found element start " + localName + " " + qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        System.out.println("Found element end " + localName + " " + qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }
}