package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
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
            ResponseEntity<Account> response = restTemplate.exchange(baseUrl + "/transfer/" + userId, HttpMethod.GET, makeAuthEntity(),
                    Account.class);
            account = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    //Zoe
    public String getBalance(AuthenticatedUser authenticatedUser) {
        //TODO implement getBalance
        return null;
    }
    //Ashley
    public String getTransferHistory(AuthenticatedUser authenticatedUser){
        setAuthToken(authenticatedUser.getToken());
        String transfers = null;
        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(baseUrl + "transfer/history/users/" + authenticatedUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), String.class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
            transfers = "ERROR";
            return transfers;
        }
        if(transfers == null) {
            transfers = "----- No transfer history to display -----";
            BasicLogger.log("No transfer history");
        }

        return transfers;
    }

    //Chris
    public String viewPendingRequests(AuthenticatedUser authenticatedUser){
        //TODO implement viewPendingRequests
        return null;
    }

    //Anthony
    public String sendBucks(AuthenticatedUser authenticatedUser, Integer userIdToSendTo, BigDecimal amount) {
        setAuthToken(authenticatedUser.getToken());
        Account sender = getAccountByUserId(authenticatedUser.getUser().getId());
        if ((sender.getBalance()).compareTo(amount) < 0)  {
           return  "You do not have enough funds to complete this transaction";
        }
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) {
            return  "Amount must be greater than 0";
        }
        try {
            restTemplate.exchange(baseUrl + "/transfer/send/" + authenticatedUser.getUser().getId()
                            + "/" + userIdToSendTo + "/" + amount, HttpMethod.POST, makeAuthEntity(), Boolean.class);
            createTransferSend(authenticatedUser, userIdToSendTo, amount);

            return  "Amount Sent Successfully.";
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return  "Amount Sent Unsuccessfully";
    }
    //Anthony
    public String createTransferRequest(AuthenticatedUser authenticatedUser, int userIdToRequestFrom ,BigDecimal amount) {
        Account accountRequesting = getAccountByUserId(authenticatedUser.getUser().getId());
        Account accountFrom = getAccountByUserId(userIdToRequestFrom);
        if (accountFrom == null) {
            return  "User not found.";
        }

        int accountRequestingId = accountRequesting.getAccountId();
        int accountFromId = accountFrom.getAccountId();
        TransferRequestDto newTransfer = null;
        if (accountRequestingId > 0 && accountFromId > 0) {
            newTransfer = new TransferRequestDto(1, 1, accountFromId, accountRequestingId, amount );
        } else {
            return  "Could not find users with the given Id";
        }

        setAuthToken(authenticatedUser.getToken());
        HttpEntity<TransferRequestDto> entity = new HttpEntity<>(newTransfer, makeAuthEntity().getHeaders());
        try {
            restTemplate.exchange(baseUrl + "/transfer",HttpMethod.POST, entity, TransferRequestDto.class).getBody();
            return "Request Created.";

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return "Request Could Not Be Created";
    }

    public List<Transfer> getPendingRequests(int currentUserId) {   
        return restTemplate.getForObject(baseUrl + "transfer/pending", List.class, currentUserId);
    }

    public String createTransferSend(AuthenticatedUser authenticatedUser, int userIdToRequestFrom ,BigDecimal amount) {
        Account accountRequesting = getAccountByUserId(authenticatedUser.getUser().getId());
        Account accountFrom = getAccountByUserId(userIdToRequestFrom);
        if (accountFrom == null) {
            return  "User not found.";
        }

        int accountRequestingId = accountRequesting.getAccountId();
        int accountFromId = accountFrom.getAccountId();
        TransferRequestDto newTransfer = null;
        if (accountRequestingId > 0 && accountFromId > 0) {
            newTransfer = new TransferRequestDto(2, 2, accountFromId, accountRequestingId, amount );
        } else {
            return  "Could not find users with the given Id";
        }

        setAuthToken(authenticatedUser.getToken());
        HttpEntity<TransferRequestDto> entity = new HttpEntity<>(newTransfer, makeAuthEntity().getHeaders());
        try {
            restTemplate.exchange(baseUrl + "/transfer",HttpMethod.POST, entity, TransferRequestDto.class).getBody();
            return "Request Created.";

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return "Request Could Not Be Created";
    }
    
    public List<User> getUsers() {
        List<User> users = List.of(restTemplate.getForObject(baseUrl + "/transfer/users", User[].class));
        if (users != null) {
            return users;
        }
        System.out.println( "Could not find users");
        return null;
    }

       
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders(); 
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
