package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.model.UserRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printPendingRequests(List<Transfer> transferList) {

        System.out.println("-------------------------------------------\n" +
                "Pending Transfers\n" +
                "ID          To                     Amount\n" +
                "-------------------------------------------");
        for (Transfer transfer : transferList) {
            User toUser = new User();
            System.out.printf("%-11d %-22s $%4.2d\n" +
                    "---------\n" +
                    "Please enter transfer ID to approve/reject (0 to cancel): ",
                    transfer.getId(), toUser.getUsername(), transfer.getTransferType());
        }

    }

    public Integer printSendRequest(){
        System.out.println("Enter ID of user you are sending to (0 to cancel):");
        String input = scanner.nextLine();
        Integer userId = Integer.parseInt(input);
        return userId;
    }



    public void printUsersSendList(List<User> users) {

        System.out.printf("-------------------------------------------\n" +
                          "Users\n" +
                          "ID          Name\n" +
                          "-------------------------------------------\n");
        for (User user : users) {
            System.out.println(user.getId()+ "        " + user.getUsername());
        }
    }
}
