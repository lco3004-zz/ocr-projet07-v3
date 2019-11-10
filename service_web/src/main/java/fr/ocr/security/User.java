package fr.ocr.security;

import java.io.Serializable;

public class User implements Serializable {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idUser;
    private String userName;
    private String password;
    private String email;


    public User(Integer idUser, String userName, String password, String email) {
        this.idUser = idUser;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public User() {

    }


    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
