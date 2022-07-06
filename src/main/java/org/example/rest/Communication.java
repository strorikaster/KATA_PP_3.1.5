package org.example.rest;

import org.example.rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    List<String> cookies = new ArrayList<>();
    HttpHeaders httpHeaders = new HttpHeaders();

    public HttpHeaders getHttpHeaders() {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        return  httpHeaders;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        //List<User> allUsers = responseEntity.getBody();
        cookies = responseEntity.getHeaders().get("Set-Cookie");
        //System.out.println(cookies);
        return responseEntity.getBody();
    }

    public void saveUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, getHttpHeaders());
        String request = restTemplate.postForEntity(URL, entity, String.class).getBody();
        new ResponseEntity<>(request, HttpStatus.OK);
        System.out.println(request);
    }

        public void updateUser(User user) {
            HttpEntity<User> entity = new HttpEntity<>(user,getHttpHeaders());
            String request = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
            System.out.println(request);
        }



    public void deleteUser(Long id) {
        HttpEntity<User> entity = new HttpEntity<>(getHttpHeaders());
        String request = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        System.out.println(request);
    }

}
