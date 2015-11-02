package edu.hm.cs.softengii.db.userAuth;


/**
 * Created by cmon on 26.10.2015.
 */
public class UserEntity {

    private String forename;
    private String surname;
    private String loginName;
    private String password;
    private String email;
    private boolean isAdmin;


    public UserEntity() {
    }

    public UserEntity(boolean isAdmin, String forename, String surname, String loginName, String email) {
        this.isAdmin = isAdmin;
        this.forename = forename;
        this.surname = surname;
        this.loginName = loginName;
        this.email = email;
    }

    public String getForename() {

        if (forename == null) {
            return "";
        }
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {

        if (surname == null) {
            return "";
        }
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
