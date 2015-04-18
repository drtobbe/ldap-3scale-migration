package com.ldap.threescale.client;

import java.io.FileReader;

import junit.framework.TestCase;

import org.springframework.util.FileCopyUtils;

import com.ldap.threescale.client.XPathParser;

/**
 * @author drtobbe
 */
public class XPathParserTest extends TestCase {

    public void testAccount() throws Exception {
        XPathParser parser = new XPathParser();
        String fileid = "2445581203721";
        String xml = FileCopyUtils.copyToString(new FileReader("src/test/resources/account-" + fileid + ".xml"));
        String id = parser.getXPath(xml, "/account/id");
        assertEquals(fileid, id);
    }

    public void testApplication() throws Exception {
        XPathParser parser = new XPathParser();
        String fileid = "1409611573384";
        String xml = FileCopyUtils.copyToString(new FileReader("src/test/resources/applications-" + fileid + ".xml"));
        String id = parser.getXPath(xml, "applications/application/id");
        assertEquals(fileid, id);
    }

}
