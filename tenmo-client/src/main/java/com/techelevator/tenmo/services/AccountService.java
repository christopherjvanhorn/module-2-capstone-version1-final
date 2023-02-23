package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {
    private final String BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    //Zoe
    public BigDecimal getBalance(int accountId) {
        //TODO implement getBalance
        Account account = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(BASE_URL + accountId, HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account.getBalance();
    }
    //Ashley
    public String getTransferHistory(AuthenticatedUser authenticatedUser){
        //TODO implement getTransferHistory
        return null;
    }
    //Chris
    public String viewPendingRequests(AuthenticatedUser authenticatedUser){
        //TODO implement viewPendingRequests
        return null;
    }

    //Anthony
    public boolean sendBucks(AuthenticatedUser authenticatedUser, String username) {
        //TODO implement sendBucks
        return false;
    }
    //Anthony
    public boolean requestBucks(AuthenticatedUser authenticatedUser, String username) {
        //TODO implement requestBucks
        return false;
    }

    private HttpEntity<Account> makeAuctionEntity(Account auction) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(auction, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }


}
