import java.util.ArrayList;
import java.util.Scanner;

public class ConnectMe {
    private static Scanner scanner = new Scanner(System.in);

    private static ArrayList<String> usernames = new ArrayList<>();
    private static ArrayList<String> passwords = new ArrayList<>();
    private static ArrayList<String> emails = new ArrayList<>();
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> bios = new ArrayList<>();
    private static ArrayList<String> dobs = new ArrayList<>();
    private static ArrayList<String> phones = new ArrayList<>();

    // Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        System.out.println("Welcome to ConnectMe!");
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    userSignUp();
                    break;
                case 2:
                    userLogin();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Exiting ConnectMe. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void userSignUp() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (usernames.contains(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        // Add to lists
        usernames.add(username);
        passwords.add(password);
        emails.add(email);
        names.add("");
        bios.add("");
        dobs.add("");
        phones.add("");

        int index = usernames.size() - 1;

        System.out.println("User registered successfully!");

        // Prompt profile setup
        System.out.println("Please complete your profile setup.");
        setupProfile(index);
    }

    private static void setupProfile(int index) {
        System.out.print("Enter your full name: ");
        String name = scanner.nextLine();
        names.set(index, name);

        System.out.print("Enter your date of birth (dd-mm-yyyy): ");
        String dob = scanner.nextLine();
        dobs.set(index, dob);

        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
        phones.set(index, phone);

        System.out.print("Write a short bio: ");
        String bio = scanner.nextLine();
        bios.set(index, bio);

        System.out.println("Profile setup complete!");
    }

    private static void userLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int index = usernames.indexOf(username);
        if (index != -1 && passwords.get(index).equals(password)) {
            System.out.println("Login successful!");
            userDashboard(index);
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void userDashboard(int index) {
        while (true) {
            System.out.println("\nUser Dashboard:");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewProfile(index);
                    break;
                case 2:
                    setupProfile(index);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewProfile(int index) {
        System.out.println("\n--- Profile ---");
        System.out.println("Username: " + usernames.get(index));
        System.out.println("Name: " + names.get(index));
        System.out.println("Email: " + emails.get(index));
        System.out.println("DOB: " + dobs.get(index));
        System.out.println("Phone: " + phones.get(index));
        System.out.println("Bio: " + bios.get(index));
    }

    private static void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful!");
            adminDashboard();
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }

    private static void adminDashboard() {
        while (true) {
            System.out.println("\nAdmin Dashboard:");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    System.out.println("Admin logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void viewAllUsers() {
        if (usernames.isEmpty()) {
            System.out.println("No users registered.");
            return;
        }
        System.out.println("\n--- Registered Users ---");
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println((i + 1) + ". Username: " + usernames.get(i) + ", Email: " + emails.get(i));
        }
    }

    private static void deleteUser() {
        System.out.print("Enter username to delete: ");
        String username = scanner.nextLine();
        int index = usernames.indexOf(username);
        if (index != -1) {
            usernames.remove(index);
            passwords.remove(index);
            emails.remove(index);
            names.remove(index);
            bios.remove(index);
            dobs.remove(index);
            phones.remove(index);
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("User not found!");
        }
    }
}
