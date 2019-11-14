package fr.ocr.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserWeb implements Serializable  {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idUser;
    private String username;
    private String password;
    private String email;

}
