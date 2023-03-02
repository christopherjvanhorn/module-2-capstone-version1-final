package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.*;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    // Ashley
    public String getTransferHistory( ) {
        // TODO implement getTransferHistory
        return null;
    }

    // Chris
    public List<TransferPendingDto> viewPendingRequests(AuthenticatedUser authenticatedUser) {
        // TODO implement viewPendingRequests
        setAuthToken(authenticatedUser.getToken());
        try {
            ResponseEntity<TransferPendingDto[]> response = restTemplate.exchange(baseUrl + "transfer/pending/" + authenticatedUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), TransferPendingDto[].class);
            return List.of(Objects.requireNonNull(response.getBody()));
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return new ArrayList<>();
    }

    public String transferRequestApproval(AuthenticatedUser authenticatedUser, int transferId, boolean approved){
        // TODO implement approve&denyTransferRequest
        setAuthToken(authenticatedUser.getToken());
        try {
            ResponseEntity<Transfer> response = restTemplate.exchange(baseUrl + "transfer/pending/" + authenticatedUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), Transfer.class);
            return "Transfer ID: " + transferId + " has been updated.";
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
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
        Transfer newTransfer = null;
        if (accountRequestingId > 0 && accountFromId > 0) {
            newTransfer = new Transfer(1, 1, accountFromId, accountRequestingId, amount );
        } else {
            return  "Could not find users with the given Id";
        }

        setAuthToken(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, makeAuthEntity().getHeaders());
        try {
            restTemplate.exchange(baseUrl + "/transfer",HttpMethod.POST, entity, Transfer.class).getBody();
            return "Request Created.";

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return "Request Could Not Be Created";
    }

    public String createTransferSend(AuthenticatedUser authenticatedUser, int userIdToRequestFrom ,BigDecimal amount) {
        Account accountRequesting = getAccountByUserId(authenticatedUser.getUser().getId());
        Account accountFrom = getAccountByUserId(userIdToRequestFrom);
        if (accountFrom == null) {
            return  "User not found.";
        }

        int accountRequestingId = accountRequesting.getAccountId();
        int accountFromId = accountFrom.getAccountId();
        Transfer newTransfer = null;
        if (accountRequestingId > 0 && accountFromId > 0) {
            newTransfer = new Transfer(2, 2, accountFromId, accountRequestingId, amount );
        } else {
            return  "Could not find users with the given Id";
        }

        setAuthToken(authenticatedUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, makeAuthEntity().getHeaders());
        try {
            restTemplate.exchange(baseUrl + "/transfer",HttpMethod.POST, entity, Transfer.class).getBody();
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
