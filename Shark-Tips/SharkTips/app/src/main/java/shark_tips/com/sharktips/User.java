package shark_tips.com.sharktips;


import java.util.Date;
import java.util.Timer;

/**
 * Created by User on 03/04/2017.
 */

public class User {
    enum IsAdmin{
        notAdmin,admin,superAdmin;
    }

    private String name;
    private String lastName;
    private String mail;
    private int phoneNumber;
    private String country;
    private String password;
    private IsAdmin isAdmin;
    private long registerTime;
    private int isActive;

    public User(String name, String lastName, String mail, int phoneNumber,
                String country, String password) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.password = password;
        isAdmin = IsAdmin.notAdmin;
        registerTime = System.currentTimeMillis();
        isActive = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 2)
            throw new IllegalArgumentException();
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 2)
            throw new IllegalArgumentException();
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (!mail.contains("@") || !mail.contains("."))
            throw new IllegalArgumentException();
        this.mail = mail;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country.length() != 2)
            throw new IllegalArgumentException();
        String realCountry = Phone2Country.getPhone(country);
        this.country = realCountry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 6)
            throw new IllegalArgumentException();
        this.password = password;
    }

    public IsAdmin getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(IsAdmin isAdmin) {
        this.isAdmin = isAdmin;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public int daysSinceRegisterd(){
        return (int) ((System.currentTimeMillis() - registerTime) / 86400000);
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
