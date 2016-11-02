/*
 * Copyright (c) 2011 Jeremy Sells
 * See the file LICENSE.txt for copying permission.
 */

package info.jeremysells.projects.programming.wolw.common;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jeremy Sells
 */
public class JxmlReader {
    private Document xmldoc = null;
    private XPath xpath = XPathFactory.newInstance().newXPath();

    /**
     * Constructor
     * E.g. use URL url = getClass().getResource(fileLocation);
     * @param url
     */
    public JxmlReader(URL url) throws Exception
    {
        InputStream is = null;
        try
        {
	    is = url.openStream();
            xmldoc = parseInputStreamToDocument(is);
        }
        catch (Exception E)
        {
            throw new Exception("Error parsing XML document. Exact error was : \n" + E.toString());
        }
	finally
        {
            try
            { is.close(); }
            catch(Exception E){  }

	}
    }

    /**
     * Basically this method does the conversion from input-stream to a Document
     *
     * @param is
     * @return
     */
    private Document parseInputStreamToDocument(InputStream is) {
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	try {
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    return db.parse(is);
	} catch (Exception e) {
	    return null;
	}
    }


    /**
     * Runs a xpath expression expecting a single result
     * @param xpath
     * @return
     */
    public String runSingleStringXPATH(String xpathExpression) throws XPathExpressionException
    {
        return (String) xpath.evaluate(xpathExpression, xmldoc, XPathConstants.STRING);
    }

        /**
     * Runs a xpath expression expecting a single result
     * @param xpath
     * @return
     */
    public NodeList runMultipleReXPATH(String xpathExpression) throws XPathExpressionException
    {
        return (NodeList) xpath.evaluate(xpathExpression, xmldoc, XPathConstants.NODESET);
    }

}
