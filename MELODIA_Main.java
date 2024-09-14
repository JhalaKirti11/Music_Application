package com.jukebox.app.MusicApp;
import java.util.Scanner;

class MainApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize database connection (optional: ensure it’s working)
        ConnectionClassForDM.getConnection();
        boolean running = true;
        
        while (running) {
            System.out.println("\n--------*Welcome to MELODIA*---------");
            System.out.println("-------*Get The Melody Here*--------");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");

            int choice = getIntegerInput(scanner, "Choose an option: ");
            switch (choice) {
                case 1:     // Admin

                    System.out.print("Enter admin ID: ");
                    int adminID = scanner.nextInt();

                    Admin admin = new Admin((adminID));

                    // Call loginAdmin() to validate admin credentials before proceeding

                    if (admin.loginAdmin()) {
                        admin.manageSongs();
                    } else {
                        System.out.println("\nInvalid admin credentials.");
                    }
                    break;

                case 2:     // User
                    Registration registration = new Registration();
                    boolean isLoggedIn = false;
                    while (!isLoggedIn) {
                        System.out.println("=====================================");
                        System.out.println("1. Register");
                        System.out.println("2. Login");
                        System.out.println("3. Back");
                        System.out.println("=====================================");
                        int userChoice = getIntegerInput(scanner,"Choose an option: " );

                        switch (userChoice) {

                            case 1:
                                registration.registerUser();
                                break;

                            case 2:
                                isLoggedIn = registration.loginUser();
                                if (isLoggedIn) {
                                    User loggedInUser = registration.getLoggedInUser();
                                    Playlist menu = new Playlist(loggedInUser);
                                    menu.userMenu();
                                }
                                break;

                            case 3:
                                running = false;
                                break;
                                
                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    break;

                case 3:     //  Back:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
    }
    scanner.close();
}

    // Method to get valid integer input
    public static int getIntegerInput(Scanner scanner, String prompt) {
        int input;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                break;
            } else {
                System.out.println("Invalid input. Please enter a valid option.");
                scanner.next(); // Consume the invalid input
            }
        }
        return input;
    }    
}
