package com.ldap.threescale.signup.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.ldap.threescale.signup.RestOperations3Scale;

/**
 * @author drtobbe
 */
public class RestTemplate3Scale extends RestTemplate implements RestOperations3Scale {

    public <T> ResponseEntity<T> putForEntity(URI url, Object request, Class<T> responseType)
            throws RestClientException {
        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
        return execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
    }

}
