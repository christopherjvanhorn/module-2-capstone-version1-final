package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.UserRequest;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class AccountService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;


    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Zoe
    public Account getAccountByUserId(int userId) {
        // TODO implement getBalance
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + userId, HttpMethod.GET, makeAuthEntity(),
                    Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    // Ashley
    public String getTransferHistory( ) {
        // TODO implement getTransferHistory
        return null;
    }

    // Chris
    public String viewPendingRequests() {
        // TODO implement viewPendingRequests
        return null;
    }

    //Anthony
    public boolean sendBucks(AuthenticatedUser authenticatedUser, String username) {
        //TODO implement sendBucks
        User user =  authenticatedUser.getUser();
        user.setId(authenticatedUser.getUser().getId());
        user.setUsername(authenticatedUser.getUser().getUsername());


        try {
            restTemplate.put(baseUrl + "send", authenticatedUser,HttpMethod.PUT);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
    }

    // Anthony
    public boolean requestBucks() {
        // TODO implement requestBucks
        return false;
    }

    private HttpEntity<Account> makeAuctionEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    public List<Transfer> getPendingRequests(int currentUserId) {   
        return restTemplate.getForObject(baseUrl + "pending", List.class, currentUserId);
    }
    
    public List<User> getUsers() {

        List<User> users = List.of(restTemplate.getForObject(baseUrl + "/transfer/users", User[].class));
        if (users != null) {
            return users;
        }
        System.out.println( "Could not find users");
        return null;
    }
}
