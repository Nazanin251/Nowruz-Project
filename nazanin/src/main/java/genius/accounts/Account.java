package genius.accounts;

import genius.util.PasswordHasher;

public abstract class Account {
    // Basic user information
    private String name;
    private int age;
    private String email;
    private String username;
    private String passwordHash;
    private AccountType accountType;

    public Account(String name, int age, String email, String username, String password, AccountType accountType) {
        // Constructor to initialize account details
        this.name = name;
        this.age = age;
        this.email = email;
        this.username = username;
        this.passwordHash = PasswordHasher.hashPassword(password);
        this.accountType = accountType;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public AccountType getAccountType() { return accountType; }

    public boolean verifyPassword(String password) {
        return PasswordHasher.verifyPassword(password, passwordHash);
    }
    // String representation of the account
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", accountType=" + accountType +
                '}';
    }
    // Compare accounts by username
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return username.equals(account.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
