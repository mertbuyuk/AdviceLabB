package com.mb.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {

	private String message;
    private String reason;
    @JsonIgnore
    private HttpStatus status;
    private Object responseBody;
    @JsonIgnore
    private Map<String, List<String>> headers;

    public Response header(String key, String value){
        if (headers == null){
            headers = new HashMap<>();
        }

        if (!headers.containsKey(key))
            headers.put(key, new ArrayList<>());

        headers.get(key).add(value);
        return this;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public Response setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public Response body(Object body){
        return setResponseBody(body);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public ResponseEntity<Response> build(){
        if (this.headers == null)
            return new ResponseEntity<>(this, this.status);
        else
            return new ResponseEntity<>(this, new LinkedMultiValueMap<>(this.headers), this.status);
    }

    public static Response ok(String message){
        Response response = new Response();

        response.setMessage(message);
        response.setReason(null);
        response.setStatus(HttpStatus.OK);

        return response;
    }

    public static Response badValue(String message, String reason){
        Response response = new Response();

        response.setMessage(message);
        response.setReason(reason);
        response.setStatus(HttpStatus.BAD_REQUEST);

        return response;
    }

    public static Response internalError(String message){
        Response response = new Response();

        response.setMessage(message);
        response.setReason("Unknown internal reason");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }

    public static Response notFound(String message){
        Response response = new Response();

        response.setMessage(message);
        response.setReason("Not found");
        response.setStatus(HttpStatus.NOT_FOUND);

        return response;
    }

    public static Response unauthorized(String message){
        Response response = new Response();

        response.setMessage(message);
        response.setReason("Unauthorized");
        response.setStatus(HttpStatus.UNAUTHORIZED);

        return response;
    }

}
