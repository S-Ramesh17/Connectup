import java.util.ArrayList;
import java.util.Scanner;

public class ConnectMe {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private ArrayList<String> usernames = new ArrayList<>();
    private ArrayList<String> passwords = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> bios = new ArrayList<>();
    private ArrayList<String> dobs = new ArrayList<>();
    private ArrayList<String> phones = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ConnectMe app = new ConnectMe();
        app.run();
    }

    public void run() {
        while (true) {
            System.out.println("\n===== ConnectMe Social Media =====");
            System.out.println("1. User Sign Up");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

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
                    System.out.println("Thank you for using ConnectMe. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void userSignUp() {
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

        usernames.add(username);
        passwords.add(password);
        emails.add(email);
        names.add("");
        bios.add("");
        dobs.add("");
        phones.add("");

        System.out.println("User registered successfully!");
    }

    private void userLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int index = usernames.indexOf(username);
        if (index != -1 && passwords.get(index).equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            userDashboard(index);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void userDashboard(int index) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Profile");
            System.out.println("2. Edit Profile");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

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
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewProfile(int index) {
        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + names.get(index));
        System.out.println("Username: " + usernames.get(index));
        System.out.println("Email: " + emails.get(index));
        System.out.println("DOB: " + dobs.get(index));
        System.out.println("Phone: " + phones.get(index));
        System.out.println("Bio: " + bios.get(index));
    }

    private void setupProfile(int index) {
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter a short bio: ");
        String bio = scanner.nextLine();

        names.set(index, name);
        dobs.set(index, dob);
        phones.set(index, phone);
        bios.set(index, bio);

        System.out.println("Profile updated successfully.");
    }

    private void adminLogin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful.");
            adminDashboard();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void adminDashboard() {
        while (true) {
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View All Users");
            System.out.println("2. Delete User");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

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
                    System.out.println("Logging out from admin...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewAllUsers() {
        if (usernames.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\n--- All Users ---");
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println((i + 1) + ". Username: " + usernames.get(i) + ", Email: " + emails.get(i));
        }
    }

    private void deleteUser() {
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
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }
}
