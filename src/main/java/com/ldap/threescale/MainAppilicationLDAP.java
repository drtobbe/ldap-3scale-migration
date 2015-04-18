package com.ldap.threescale;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ldap.threescale.client.LdifParser;
import com.ldap.threescale.signup.RestOperations3Scale;
import com.ldap.threescale.signup.SignUpManager;
import com.ldap.threescale.signup.SignupApplication;
import com.ldap.threescale.signup.impl.RestTemplate3Scale;
import com.ldap.threescale.signup.impl.SignUpManagerImpl;
import com.ldap.threescale.signup.impl.SignupApplicationImpl;

/**
 * @author drtobbe
 */
public class MainAppilicationLDAP {
    private final static Logger logger = LoggerFactory.getLogger(MainAppilicationLDAP.class);
    private SignUpManager manager;
    private SignupApplication application;
    private String provider_key = Constants.PROVIDER_KEY;

    private URI uri = new URI("https://" + Constants.DOMAIN + "-admin.3scale.net/admin/api");

    public MainAppilicationLDAP() throws Exception {
        RestOperations3Scale template = new RestTemplate3Scale();
        manager = new SignUpManagerImpl(uri, template);
        application = new SignupApplicationImpl(manager);
    }

    private void loadUsers(String companyName, String domainName, String email, String password, String consumerId) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("provider_key", provider_key);
        map.add("consumerId", consumerId);
        map.add("org_name", companyName);
        map.add("last_name", domainName);
        map.add("username", email);
        map.add("email", email);
        map.add("password", password);
        logger.info("map: " + map);

        MultiValueMap<String, String> props = new LinkedMultiValueMap<String, String>();
        props.add("provider_key", provider_key);
        props.add("name", companyName + "'s App");
        props.add("description", "Description of your default application");
        props.add("user_key", consumerId);
        logger.info("props: " + props);

        try {
            application.signUpUser(map, props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            MainAppilicationLDAP app = new MainAppilicationLDAP();
            LdifParser parser = new LdifParser();
            String filename = "csv/LDAPcontent.ldif";
            List<MultiValueMap<String, String>> list = parser.getLDAP(filename);
            int loop = 0;
            for (MultiValueMap<String, String> map : list) {
                try {
                    String companyName = map.getFirst("cn");
                    String domainName = map.getFirst("domain");
                    String email = map.getFirst("mail");
                    String consumerId = map.getFirst("consumerId");
                    String password = UUID.randomUUID().toString();
                    app.loadUsers(companyName, domainName, email, password, consumerId);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (++loop > 50) {
                    break;
                }
            }
            logger.info("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
