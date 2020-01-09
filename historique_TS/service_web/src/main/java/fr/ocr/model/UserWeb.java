package fr.ocr.model;

import lombok.Data;


@Data
public class UserWeb {
    private Integer idUser;
    private String username;
    private String password;
    private String email;

}
