package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountService {
    private final String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

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
    public String getTransferHistory(AuthenticatedUser authenticatedUser) {
    // TODO implement getTransferHistory

    // Ashley
    public String getTransferHistory(AuthenticatedUser authenticatedUser) {
        // TODO implement getTransferHistory
        return null;
    }

    // Chris
    public String viewPendingRequests(AuthenticatedUser authenticatedUser) {
    // TODO implement viewPendingRequests

    // Chris
    public String viewPendingRequests(AuthenticatedUser authenticatedUser) {
        // TODO implement viewPendingRequests
        return null;
    }

    // Anthony
    // Anthony
    public boolean sendBucks(AuthenticatedUser authenticatedUser, String username) {
        // TODO implement sendBucks
        return false;
    }

    // Anthony

    // Anthony
    public boolean requestBucks(AuthenticatedUser authenticatedUser, String username) {
        // TODO implement requestBucks
        // TODO implement requestBucks
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

    public List<Transfer> getPendingRequests(int currentUserId) {
        return restTemplate.getForObject(baseUrl + "pending", List.class, currentUserId);
    }
}
