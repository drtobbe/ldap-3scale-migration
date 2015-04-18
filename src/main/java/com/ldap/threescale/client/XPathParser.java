package com.ldap.threescale.client;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author drtobbe
 */
public class XPathParser {
    private final static Logger logger = LoggerFactory.getLogger(XPathParser.class);

    public String getXPath(String xml, String expr) throws Exception {
        InputSource source = new InputSource(new StringReader(xml));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(source);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        String value = xpath.evaluate(expr, document);
        logger.info(expr + ": " + value);
        return value;
    }

}
