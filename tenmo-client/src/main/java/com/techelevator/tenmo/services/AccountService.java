package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    //Zoe
    public String getBalance(AuthenticatedUser authenticatedUser) {
        //TODO implement getBalance
        return null;
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


    public List<Transfer> getPendingRequests(int currentUserId) {
        return restTemplate.getForObject(baseUrl+"pending", List.class, currentUserId);
    }
}
