package com.ldap.threescale.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.directory.shared.ldap.entry.EntryAttribute;
import org.apache.directory.shared.ldap.entry.StringValue;
import org.apache.directory.shared.ldap.ldif.LdifEntry;
import org.apache.directory.shared.ldap.ldif.LdifReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author drtobbe
 */
public class LdifParser {
    Logger logger = LoggerFactory.getLogger(LdifParser.class);

    public List<MultiValueMap<String, String>> getLDAP(String filename) throws Exception {
        List<MultiValueMap<String, String>> list = new ArrayList<MultiValueMap<String, String>>();
        LdifReader reader = new LdifReader();
        List<LdifEntry> entries = reader.parseLdifFile(filename);
        //iterate the entries
        for (LdifEntry entry : entries) {
            MultiValueMap<String, String> props = new LinkedMultiValueMap<String, String>();
            EntryAttribute cn = entry.get("cn");
            EntryAttribute mail = entry.get("mail");
            EntryAttribute clientId = entry.get("clientId");
            EntryAttribute domain = entry.get("associatedDomainName");
            //
            if ((cn != null && (cn.get() instanceof StringValue))
                    && (mail != null && (mail.get() instanceof StringValue))
                    && (clientId != null && (clientId.get() instanceof StringValue))
                    && (domain != null && (domain.get() instanceof StringValue))) {
                System.out.print(cn);
                props.add("cn", cn.getString());

                System.out.print(mail);
                props.add("mail", mail.getString());

                System.out.print(domain);
                props.put("domain", Arrays.asList(domain.getString().split(",")));

                System.out.println(clientId);
                props.add("consumerId", clientId.getString());

                list.add(props);
            }
        }
        reader.close();
        logger.info("list: " + list.size());
        return list;
    }
}
