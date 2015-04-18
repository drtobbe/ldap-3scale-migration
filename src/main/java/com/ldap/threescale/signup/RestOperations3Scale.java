package com.ldap.threescale.signup;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

/**
 * @author drtobbe
 */
public interface RestOperations3Scale extends RestOperations {

    public <T> ResponseEntity<T> putForEntity(URI url, Object request, Class<T> responseType);

}
