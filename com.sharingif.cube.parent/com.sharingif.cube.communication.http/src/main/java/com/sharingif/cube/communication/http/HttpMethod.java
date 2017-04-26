package com.sharingif.cube.communication.http;

/**
 * Java 5 enumeration of HTTP request methods. Intended for use
 * with {@link org.springframework.http.client.ClientHttpRequest}
 * and {@link org.springframework.web.client.RestTemplate}.
 *
 * @author Arjen Poutsma
 * @since 3.0
 */
public enum HttpMethod {

	GET, POST, HEAD, OPTIONS, PUT, PATCH, DELETE, TRACE

}
