package com.ldap.threescale.signup.impl;

import org.springframework.util.MultiValueMap;

import com.ldap.threescale.signup.SignUpManager;
import com.ldap.threescale.signup.SignupApplication;

/**
 * @author drtobbe
 */
public class SignupApplicationImpl implements SignupApplication {
    private SignUpManager manager;

    public SignupApplicationImpl(SignUpManager manager) {
        this.manager = manager;
    }

    @Override
    public String signUpUser(MultiValueMap<String, String> map1, MultiValueMap<String, String> map2) {
        Long account = manager.signUpExpress(map1);
        if (account == 0) {
            return "-";
        }
        Long application = manager.getApplicationInfo(account, map1.getFirst("provider_key"));
        return manager.setApplicationInfo(account, application, map2);
    }

}
