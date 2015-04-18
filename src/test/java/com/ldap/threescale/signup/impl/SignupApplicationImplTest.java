package com.ldap.threescale.signup.impl;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.agical.rmock.extension.junit.RMockTestCase;
import com.ldap.threescale.Constants;
import com.ldap.threescale.signup.SignUpManager;
import com.ldap.threescale.signup.SignupApplication;
import com.ldap.threescale.signup.impl.SignupApplicationImpl;

/**
 * @author drtobbe
 */
public class SignupApplicationImplTest extends RMockTestCase {

    public void testSignUp() throws Exception {
        Long account = 2445581177144L;
        Long application = 1409611700983L;

        // Data: CONSUMER_ID,COMPANY_NAME,DOMAIN_NAME,E_MAIL
        String consumerId = "5f6da2fd-75aa-4114-83b3-9bd1255b78e5";
        String companyName = "TEST";
        //String domainName = "www.testa.com";
        String email = "test@testa.com";
        String password = "Test1234";
        String provider_key = Constants.PROVIDER_KEY;

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("provider_key", provider_key);
        map.add("consumerId", consumerId);
        map.add("org_name", companyName);
        map.add("username", email);
        map.add("email", email);
        map.add("password", password);

        MultiValueMap<String, String> props = new LinkedMultiValueMap<String, String>();
        props.add("provider_key", Constants.PROVIDER_KEY);
        props.add("name", "EXAMPLE");
        props.add("user_key", "4b6a4d7b-97d3-4a96-bfe6-16fe6b4a1111");

        // Mocks
        SignUpManager manager = (SignUpManager) mock(SignUpManager.class, "manager");

        // Impl under test
        SignupApplication app = new SignupApplicationImpl(manager);

        // Expectations
        manager.signUpExpress(map);
        modify().returnValue(account);
        manager.getApplicationInfo(account, Constants.PROVIDER_KEY);
        modify().returnValue(application);
        manager.setApplicationInfo(account, application, props);

        // Change state to verification, IMPORTANT!!!
        startVerification();

        // Do it!
        app.signUpUser(map, props);
    }

}
