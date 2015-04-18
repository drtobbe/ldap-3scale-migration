package com.ldap.threescale.signup;

import org.springframework.util.MultiValueMap;

/**
 * @author drtobbe
 */
public interface SignupApplication {

    String signUpUser(MultiValueMap<String, String> map1, MultiValueMap<String, String> map2);

}
