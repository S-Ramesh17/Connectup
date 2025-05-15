import java.util.Scanner;

class Connect {
    private final Scanner sc = new Scanner(System.in);

    // User data arrays
    private String[] emails = new String[1000];
    private String[] passwords = new String[1000];
    private String[] names = new String[1000];
    private String[] bios = new String[1000];
    private long[] phones = new long[1000];
    private int users = 0;

    // Post data arrays
    private String[] posts = new String[5000];
    private int[] postOwners = new int[5000]; // owner user index
    private int postCount = 0;

    // Admin credentials
    private final String adminEmail = "admin@connect.com";
    private final String adminPassword = "admin123";

    public void mainMenu() {
        System.out.println("WELCOME TO CONNECT - SOCIAL MEDIA PLATFORM");
        while (true) {
            printOptions("Select Role:", "User", "Admin", "Exit");
            switch (sc.nextLine().trim()) {
                case "1" -> userMenu();
                case "2" -> adminLogin();
                case "3" -> {
                    System.out.println("Thank you for using CONNECT. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }

    // Reusable method for printing options with numbers
    private void printOptions(String header, String... options) {
        System.out.println("\n" + header);
        for (int i = 0; i < options.length; i++) System.out.printf("%d. %s\n", i + 1, options[i]);
        System.out.print("Choice: ");
    }

    // User menu: sign up, login, back
    private void userMenu() {
        while (true) {
            printOptions("User Menu:", "Sign Up", "Log In", "Back");
            switch (sc.nextLine().trim()) {
                case "1" -> signUp();
                case "2" -> {
                    int userIdx = userLogin();
                    if (userIdx != -1) userDashboard(userIdx);
                }
                case "3" -> { return; }
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }

    private void signUp() {
        System.out.println("\n--- Sign Up ---");
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        if (findUserByEmail(email) != -1) {
            System.out.println("Email already registered. Please log in or use another email.");
            return;
        }
        String pwd = getPasswordInput();
        if (pwd == null) return;

        // Store email/password
        emails[users] = email;
        passwords[users] = pwd;

        // Profile setup
        System.out.println("Set up your profile:");
        System.out.print("Name: ");
        names[users] = sc.nextLine();

        phones[users] = getValidPhone();

        System.out.print("Bio: ");
        bios[users] = sc.nextLine();

        users++;
        System.out.println("Registration successful! Please log in.");
    }

    private String getPasswordInput() {
        System.out.print("Enter Password: ");
        String pwd1 = sc.nextLine();
        System.out.print("Confirm Password: ");
        String pwd2 = sc.nextLine();
        if (!pwd1.equals(pwd2)) {
            System.out.println("Passwords do not match. Try again.");
            return null;
        }
        return pwd1;
    }

    private long getValidPhone() {
        while (true) {
            System.out.print("Phone number: ");
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid phone number. Please enter digits only.");
            }
        }
    }

    private int userLogin() {
        System.out.println("\n--- User Log In ---");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String pwd = sc.nextLine();
        int idx = findUserByEmail(email);
        if (idx == -1 || !pwd.equals(passwords[idx])) {
            System.out.println("Invalid email or password.");
            return -1;
        }
        System.out.println("Login successful! Welcome " + names[idx]);
        return idx;
    }

    private void userDashboard(int userIdx) {
        while (true) {
            printOptions("User Dashboard:",
                "View Profile",
                "Edit Profile",
                "Create Post",
                "View My Posts",
                "Edit Post",
                "Delete Post",
                "Logout"
            );
            switch (sc.nextLine().trim()) {
                case "1" -> showProfile(userIdx);
                case "2" -> editProfile(userIdx);
                case "3" -> createPost(userIdx);
                case "4" -> viewPosts(userIdx);
                case "5" -> editPost(userIdx);
                case "6" -> deletePost(userIdx);
                case "7" -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void showProfile(int u) {
        System.out.println("\n--- Profile ---");
        System.out.printf("Name: %s\nEmail: %s\nPhone: %d\nBio: %s\n",
                names[u], emails[u], phones[u], bios[u]);
    }

    private void editProfile(int u) {
        while (true) {
            printOptions("Edit Profile:", "Name", "Phone", "Bio", "Back");
            switch (sc.nextLine().trim()) {
                case "1" -> {
                    System.out.print("Enter new name: ");
                    names[u] = sc.nextLine();
                    System.out.println("Name updated.");
                }
                case "2" -> {
                    phones[u] = getValidPhone();
                    System.out.println("Phone updated.");
                }
                case "3" -> {
                    System.out.print("Enter new bio: ");
                    bios[u] = sc.nextLine();
                    System.out.println("Bio updated.");
                }
                case "4" -> { return; }
                default -> System.out.println("Invalid input.");
            }
        }
    }

    private void createPost(int u) {
        System.out.print("\nEnter post content: ");
        String content = sc.nextLine();
        posts[postCount] = content;
        postOwners[postCount++] = u;
        System.out.println("Post created successfully.");
    }

    private void viewPosts(int u) {
        System.out.println("\n--- Your Posts ---");
        boolean found = false;
        for (int i = 0; i < postCount; i++) {
            if (postOwners[i] == u) {
                System.out.printf("%d. %s\n", i + 1, posts[i]);
                found = true;
            }
        }
        if (!found) System.out.println("You have no posts.");
    }

    private void editPost(int u) {
        viewPosts(u);
        System.out.print("Enter post number to edit (0 to cancel): ");
        int idx = getValidIndex(postCount);
        if (idx == 0) return;
        idx--;
        if (idx < 0 || idx >= postCount || postOwners[idx] != u) {
            System.out.println("Invalid post number.");
            return;
        }
        System.out.print("Enter new content: ");
        posts[idx] = sc.nextLine();
        System.out.println("Post updated.");
    }

    private void deletePost(int u) {
        viewPosts(u);
        System.out.print("Enter post number to delete (0 to cancel): ");
        int idx = getValidIndex(postCount);
        if (idx == 0) return;
        idx--;
        if (idx < 0 || idx >= postCount || postOwners[idx] != u) {
            System.out.println("Invalid post number.");
            return;
        }
        removePostAt(idx);
        System.out.println("Post deleted.");
    }

    private void removePostAt(int idx) {
        for (int i = idx; i < postCount - 1; i++) {
            posts[i] = posts[i + 1];
            postOwners[i] = postOwners[i + 1];
        }
        posts[--postCount] = null;
        postOwners[postCount] = -1;
    }

    private int getValidIndex(int max) {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine());
                if (val >= 0 && val <= max) return val;
                System.out.print("Number out of range, try again: ");
            } catch (Exception e) {
                System.out.print("Invalid number, try again: ");
            }
        }
    }

    private void adminLogin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String pwd = sc.nextLine();
        if (email.equals(adminEmail) && pwd.equals(adminPassword)) {
System.out.println("Admin login successful!");
adminDashboard();
} else {
System.out.println("Invalid admin credentials.");
}
}
private void adminDashboard() {
    while (true) {
        printOptions("Admin Dashboard:",
            "View All Users",
            "Delete User",
            "View All Posts",
            "Delete Post",
            "Logout"
        );
        switch (sc.nextLine().trim()) {
            case "1" -> listAllUsers();
            case "2" -> deleteUser();
            case "3" -> listAllPosts();
            case "4" -> deletePostByAdmin();
            case "5" -> {
                System.out.println("Admin logged out.");
                return;
            }
            default -> System.out.println("Invalid input.");
        }
    }
}

private void listAllUsers() {
    System.out.println("\n--- All Users ---");
    if (users == 0) {
        System.out.println("No users registered.");
        return;
    }
    for (int i = 0; i < users; i++) {
        System.out.printf("%d. %s (%s) - %d\n", i + 1, names[i], emails[i], phones[i]);
    }
}

private void deleteUser() {
    listAllUsers();
    System.out.print("Enter user number to delete (0 to cancel): ");
    int idx = getValidIndex(users);
    if (idx == 0) return;
    idx--;
    if (idx < 0 || idx >= users) {
        System.out.println("Invalid user number.");
        return;
    }

    // Remove user and their posts
    for (int i = idx; i < users - 1; i++) {
        emails[i] = emails[i + 1];
        passwords[i] = passwords[i + 1];
        names[i] = names[i + 1];
        bios[i] = bios[i + 1];
        phones[i] = phones[i + 1];
    }
    users--;

    // Remove posts by user
    for (int i = postCount - 1; i >= 0; i--) {
        if (postOwners[i] == idx) {
            removePostAt(i);
        } else if (postOwners[i] > idx) {
            postOwners[i]--; // Adjust postOwners index due to user array shift
        }
    }

    System.out.println("User and their posts deleted.");
}

private void listAllPosts() {
    System.out.println("\n--- All Posts ---");
    if (postCount == 0) {
        System.out.println("No posts available.");
        return;
    }
    for (int i = 0; i < postCount; i++) {
        System.out.printf("%d. %s (By %s)\n", i + 1, posts[i], names[postOwners[i]]);
    }
}

private void deletePostByAdmin() {
    listAllPosts();
    System.out.print("Enter post number to delete (0 to cancel): ");
    int idx = getValidIndex(postCount);
    if (idx == 0) return;
    idx--;
    if (idx < 0 || idx >= postCount) {
        System.out.println("Invalid post number.");
        return;
    }
    removePostAt(idx);
    System.out.println("Post deleted.");
}

private int findUserByEmail(String email) {
    for (int i = 0; i < users; i++) {
        if (emails[i].equalsIgnoreCase(email)) return i;
    }
    return -1;
}
}

public class Main {
public static void main(String[] args) {
Connect app = new Connect();
app.mainMenu();
}
}
