package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.List;


public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
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
    public boolean sendBucks(AuthenticatedUser authenticatedUser, Integer userIdToSendTo, BigDecimal amount) {
        //TODO implement sendBucks
        HttpEntity<AuthenticatedUser> entity = new HttpEntity<>(authenticatedUser);
        try {
            restTemplate.put(baseUrl + "/transfer/send/" + authenticatedUser.getUser().getId()
                            + "/" + userIdToSendTo + "/" + amount, authenticatedUser,HttpMethod.PUT, entity);
            return  true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;


    }
    //Anthony
    public boolean requestBucks(AuthenticatedUser authenticatedUser, Integer userIdToRequestFrom ,BigDecimal amount) {
        //TODO implement requestBucks
        HttpEntity<AuthenticatedUser> entity = new HttpEntity<>(authenticatedUser);
        try {
            restTemplate.put(baseUrl + "/transfer/request/" + authenticatedUser.getUser().getId()
                            + "/" + userIdToRequestFrom + "/" + amount, authenticatedUser,HttpMethod.PUT, entity);
            return true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return false;
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

    private HttpEntity<Account> makeAccountEntity(Account account) {
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

}
