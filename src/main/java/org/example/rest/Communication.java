package org.example.rest;

import org.example.rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";

    List<String> sessionId;
    String headers = "";

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();
        sessionId = responseEntity.getHeaders().get("Set-Cookie")/*.get(0).split(";")[0]*/;
        headers =  sessionId.stream().collect(Collectors.joining(";"));
        System.out.println(sessionId);
        return allUsers;
    }

//    public User getOneUser(Long id) {
//        User user = restTemplate.getForObject(URL + "/" + id, User.class);
//        return user;
//    }

    //String headers =  sessionId.stream().collect(Collectors.joining(";"));

    public void saveUser(User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", headers);
        //Long id = user.getId();
        //if(id == 0) {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, user, String.class);
        System.out.println("New user added to database");
        System.out.println(responseEntity.getBody());
//       } else {
    }

        public void updateUser(User user) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Cookie", headers);
           restTemplate.put(URL, user);
           ///System.out.println("User with id " + id + " was updated");
       }



    public void deleteUser(Long id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", sessionId.stream().collect(Collectors.joining(";")));
        restTemplate.delete(URL + "/" +id);
        System.out.println("User with id " + id + " was deleted");
    }

}
