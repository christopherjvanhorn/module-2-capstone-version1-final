package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import java.math.BigDecimal;
import java.text.NumberFormat;

import java.util.List;

import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;
    private NumberFormat currency = NumberFormat.getCurrencyInstance();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance(currentUser.getUser().getId());
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance(int userId) {
        Account account = accountService.getAccountByUserId(userId);

        if (account != null) {
            System.out.println("Your current account balance is: " + currency.format(account.getBalance()));
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void viewTransferHistory() {
        /*
         * TODO Use transferService class to send request to
         * transferController that returns TransferDTO
         */
        String response = accountService.getTransferHistory(currentUser);
        consoleService.printTransferHistory(response);

    }

    private void viewPendingRequests() {
        /*
         * TODO Use transferService class to send request to
         * transferController that returns transferRequest
         * where status = Pending
         */
        consoleService.printPendingRequests(accountService.getPendingRequests(currentUser.getUser().getId()));

    }


	private void sendBucks() {
        consoleService.printUsersSendList(accountService.getUsers());
        Integer userIdToSendTo = consoleService.promptForInt("Enter ID of user you are sending to(0 to cancel): ");
        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");
        System.out.println(accountService.sendBucks(currentUser, userIdToSendTo, amount));
	}

	private void requestBucks() {
        consoleService.printUsersSendList(accountService.getUsers());
        int userIdToRequestFrom = consoleService.promptForInt("Enter ID of user you are requesting from (0 to cancel): ");
        BigDecimal amount = consoleService.promptForBigDecimal("Enter amount:");
        System.out.println(accountService.createTransferRequest(currentUser, userIdToRequestFrom, amount));
	}

}
