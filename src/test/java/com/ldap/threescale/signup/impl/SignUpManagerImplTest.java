package com.ldap.threescale.signup.impl;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.agical.rmock.extension.junit.RMockTestCase;
import com.ldap.threescale.Constants;
import com.ldap.threescale.signup.RestOperations3Scale;
import com.ldap.threescale.signup.SignUpManager;
import com.ldap.threescale.signup.impl.SignUpManagerImpl;

/**
 * @author drtobbe
 */
public class SignUpManagerImplTest extends RMockTestCase {
    private final static Logger logger = LoggerFactory.getLogger(SignUpManagerImplTest.class);

    public void testSignUp() throws Exception {
        // Data: CONSUMER_ID,COMPANY_NAME,DOMAIN_NAME,E_MAIL
        String consumerId = "5f6da2fd-75aa-4114-83b3-9bd1255b78e5";
        String companyName = "TEST3";
        //String domainName = "www.testa.com";
        String email = "test3@testa.com";
        String password = "Test1234";
        String provider_key = Constants.PROVIDER_KEY;

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("provider_key", provider_key);
        map.add("consumerId", consumerId);
        map.add("org_name", companyName);
        map.add("username", email);
        map.add("email", email);
        map.add("password", password);

        String body = "<account><id>2445581203721</id></account>";
        ResponseEntity<String> model = new ResponseEntity<String>(body, HttpStatus.OK);

        // Mocks
        RestOperations3Scale template = (RestOperations3Scale) mock(RestOperations3Scale.class, "template");

        // Impl under test
        URI uri = new URI("https://" + Constants.DOMAIN + "-admin.3scale.net/admin/api");
        SignUpManager manager = new SignUpManagerImpl(uri, template);

        // Expectations
        URI signup = new URI(uri.toString() + "/signup.xml");
        template.postForEntity(signup, map, String.class);
        modify().returnValue(model);

        // Change state to verification, IMPORTANT!!!
        startVerification();

        // Do it!
        Long account = manager.signUpExpress(map);
        logger.info("account: " + account);
    }

    public void testGetApplication() throws Exception {
        String body = "<applications><application><id>2445581203721</id></application></applications>";
        ResponseEntity<String> model = new ResponseEntity<String>(body, HttpStatus.OK);

        // Mocks
        RestOperations3Scale template = (RestOperations3Scale) mock(RestOperations3Scale.class, "template");

        // Impl under test
        URI uri = new URI("https://" + Constants.DOMAIN + "-admin.3scale.net/admin/api");
        SignUpManager manager = new SignUpManagerImpl(uri, template);

        // Expectations
        String url = "/accounts/2445581218942/applications.xml?provider_key=" + Constants.PROVIDER_KEY;
        URI put = new URI(uri.toString() + url);
        template.getForEntity(put.toString(), String.class);
        modify().returnValue(model);

        // Change state to verification, IMPORTANT!!!
        startVerification();

        // Do it!
        Long application = manager.getApplicationInfo(2445581218942L, Constants.PROVIDER_KEY);
        logger.info("application: " + application);
    }

    public void testSetApplication() throws Exception {
        String body = "<applications><application><id>2445581203721</id></application></applications>";
        ResponseEntity<String> model = new ResponseEntity<String>(body, HttpStatus.OK);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("provider_key", Constants.PROVIDER_KEY);
        map.add("name", "EXAMPLE");
        map.add("user_key", "4b6a4d7b-97d3-4a96-bfe6-16fe6b4a3333");

        // Mocks
        RestOperations3Scale template = (RestOperations3Scale) mock(RestOperations3Scale.class, "template");

        // Impl under test
        URI uri = new URI("https://" + Constants.DOMAIN + "-admin.3scale.net/admin/api");
        SignUpManager manager = new SignUpManagerImpl(uri, template);

        Long account = 2445581218942L;
        Long application = 1409611700983L;
        // Expectations
        String url = "/accounts/" + account + "/applications/" + application + ".xml";
        URI put = new URI(uri.toString() + url);
        template.putForEntity(put, map, String.class);
        modify().returnValue(model);

        // Change state to verification, IMPORTANT!!!
        startVerification();

        // Do it!
        manager.setApplicationInfo(account, application, map);

    }

}
