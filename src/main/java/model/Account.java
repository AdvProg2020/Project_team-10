package model;


public abstract class Account {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private long credit;
    private String imagePath;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber
            , String password, String imagePath) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imagePath = imagePath;
    }

    protected Account(String username) {
        this.username = username;
    }

    public Account() {
    }

    @Override
    public String toString() {
        return
                "username: " + username + "\n" +
                "firstName: " + firstName + "\n" +
                "lastName: " + lastName + "\n" +
                "email: " + email + "\n" +
                "phoneNumber: " + phoneNumber + "\n" +
                "password: " + password;
    }

    public void increaseCredit(long creditPrice){
        this.credit += creditPrice ;
    }

    public void subtractCredit(double price) {
        this.credit -= price;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getCredit() {
        return credit;
    }

    public String getImagePath() {
        return imagePath;
    }
}
