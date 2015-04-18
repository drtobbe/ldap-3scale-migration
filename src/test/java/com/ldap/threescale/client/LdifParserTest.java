package com.ldap.threescale.client;

import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import com.ldap.threescale.client.LdifParser;

/**
 * @author drtobbe
 */
public class LdifParserTest extends TestCase {
    Logger logger = LoggerFactory.getLogger(LdifParserTest.class);

    public void testReadLdif() throws Exception {
        LdifParser parser = new LdifParser();
        String filename = "csv/LDAPcontent.ldif";
        List<MultiValueMap<String, String>> list = parser.getLDAP(filename);
        logger.info("list: " + list.get(0));
    }

}
