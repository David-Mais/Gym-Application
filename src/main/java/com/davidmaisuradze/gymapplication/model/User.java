package com.davidmaisuradze.gymapplication.model;

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean isActive;

    public User(String firstName, String lastName, String userName, String password, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isActive = isActive;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() +
                lastName.hashCode() +
                userName.hashCode() +
                password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        User user = (User) obj;
        if (user.hashCode() != this.hashCode())  {
            return false;
        }
        if (!this.getUserName().equals(user.getUserName())) {
            return false;
        }
        return true;
    }
}
