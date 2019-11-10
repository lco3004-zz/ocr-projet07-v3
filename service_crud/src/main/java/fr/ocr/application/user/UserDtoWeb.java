package fr.ocr.application.user;

import lombok.Value;

import java.io.Serializable;


@Value
public class UserDtoWeb implements Serializable {
    static final long serialVersionUID = 5453481303625368221L;

    private Integer idUser;
    private String userName;
    private String password;
    private String email;
}
