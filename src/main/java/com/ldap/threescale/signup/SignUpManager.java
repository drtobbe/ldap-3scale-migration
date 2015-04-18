package com.ldap.threescale.signup;

import org.springframework.util.MultiValueMap;

/**
 * @author drtobbe
 */
public interface SignUpManager {

    public Long signUpExpress(MultiValueMap<String, String> map);

    public Long getApplicationInfo(Long account, String provider_key);

    public String setApplicationInfo(Long account, Long application, MultiValueMap<String, String> props);

}
