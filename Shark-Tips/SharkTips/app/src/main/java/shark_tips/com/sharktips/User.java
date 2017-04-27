package shark_tips.com.sharktips;


import java.util.Date;

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
    private long phoneNumber;
    private String country,countryCode;
    private String password;
    private boolean isAdmin,isPaid;
    private String speakLang,trdeLvl;
    private long registerTime;
    private int isActive;
    private long timeStamp;


    public User() {
    }

    public User(String name, String lastName, String mail, long timeStamp) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.timeStamp = timeStamp;
    }

    public User(String name, String lastName, String mail, long phoneNumber,
                String countryCode, String country, String password) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.countryCode = countryCode;
        this.country = country;
        this.password = password;
        isAdmin = false;
//        registerTime = System.currentTimeMillis();
//        isActive = 0;
    }

    public User(String name, String lastName, String mail, long phoneNumber, String country, String countryCode, String password, boolean isAdmin, boolean isPaid, String speakLang, String trdeLvl) {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.countryCode = countryCode;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isPaid = isPaid;
        this.speakLang = speakLang;
        this.trdeLvl = trdeLvl;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        if (countryCode == null)
            return;
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() < 2)
            return;
        this.name = name;
    }

    public boolean checkValidUserName(){
        if (name != null){
            return true;
        }
        return false;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() < 2)
            return;
        this.lastName = lastName;
    }

    public boolean checkValidUserLastName(){
        if (lastName != null){
            return true;
        }
        return false;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (!mail.contains("@") && !mail.contains("."))
            return;
        this.mail = mail;
    }

    public boolean checkValidMail(){
        if (mail != null){
            return true;
        }
        return false;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        if (phoneNumber < 6)
            return;
        this.phoneNumber = phoneNumber;
    }

    public boolean checkValidPhoneNumber(){
        if (phoneNumber > 6){
            return true;
        }
        return false;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country.length() < 2)
            return;
        this.country = country;
    }

    public boolean checkValidCountryCode(){
        if (country != null){
            return true;
        }
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 6)
            return;
        this.password = password;
    }

    public boolean checkValidPassword(){
        if (password != null){
            return true;
        }
        return false;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getSpeakLang() {
        return speakLang;
    }

    public void setSpeakLang(String speakLang) {
        this.speakLang = speakLang;
    }

    public String getTrdeLvl() {
        return trdeLvl;
    }

    public void setTrdeLvl(String trdeLvl) {
        this.trdeLvl = trdeLvl;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
