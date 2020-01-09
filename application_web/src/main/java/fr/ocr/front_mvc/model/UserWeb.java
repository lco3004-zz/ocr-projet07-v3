package fr.ocr.front_mvc.model;

import javax.validation.constraints.NotEmpty;



public class UserWeb {
    private Integer idUser;

    @NotEmpty(message = "Ne doit pas être vide")
    private String username;

    @NotEmpty(message = "Ne doit pas être vide")
    private String password;

    private String email;

    public UserWeb() {

    }

    public UserWeb(Integer idUser,
                   String username,
                   String password,
                   String email) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
