package edu.hm.cs.softengii.db.entities;

import javax.persistence.*;

/**
 * Created by cmon on 26.10.2015.
 */
@Entity
@Table(name = "User")
public class UserEntity {

    private int id;
    private String forename;
    private String surname;
    private String loginName;
    private String password;
    private boolean isAdmin;


    public UserEntity() {
    }

    public UserEntity(boolean isAdmin, String forename, String surname, String loginName, String password) {
        this.isAdmin = isAdmin;
        this.forename = forename;
        this.surname = surname;
        this.loginName = loginName;
        this.password = password;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "forename")
    public String getForename() {

        if (forename == null) {
            return "";
        }
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {

        if (surname == null) {
            return "";
        }
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "loginname")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "admin")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


}
