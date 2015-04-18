package com.ldap.threescale.signup.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import com.ldap.threescale.client.XPathParser;
import com.ldap.threescale.signup.RestOperations3Scale;
import com.ldap.threescale.signup.SignUpManager;

/**
 * @author drtobbe
 */
public class SignUpManagerImpl implements SignUpManager {
    Logger logger = LoggerFactory.getLogger(SignUpManagerImpl.class);

    private RestOperations3Scale restTemplate;
    XPathParser parser = new XPathParser();
    private URI uri;

    public SignUpManagerImpl(URI uri, RestOperations3Scale restTemplate) {
        this.uri = uri;
        this.restTemplate = restTemplate;
    }

    @Override
    public Long signUpExpress(MultiValueMap<String, String> map) {
        try {
            URI signup = new URI(uri.toString() + "/signup.xml");
            ResponseEntity<String> response = restTemplate.postForEntity(signup, map, String.class);
            logger.info(response.toString());
            String account_xml = response.getBody();
            String id = parser.getXPath(account_xml, "/account/id");
            return Long.parseLong(id);
        } catch (HttpClientErrorException e) {
            logger.warn(e.getMessage());
            return 0L;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String setApplicationInfo(Long account, Long application, MultiValueMap<String, String> map) {
        try {
            String url = "/accounts/" + account + "/applications/" + application + ".xml";
            URI put = new URI(uri.toString() + url);
            ResponseEntity<String> response = restTemplate.putForEntity(put, map, String.class);
            logger.info(response.toString());
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long getApplicationInfo(Long account, String provider_key) {
        try {
            String url = "/accounts/" + account + "/applications.xml?provider_key=" + provider_key;
            URI signup = new URI(uri.toString() + url);
            ResponseEntity<String> response = restTemplate.getForEntity(signup.toString(), String.class);
            String applications_xml = response.getBody();
            logger.info(response.toString());
            String id = parser.getXPath(applications_xml, "applications/application/id");
            return Long.parseLong(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
