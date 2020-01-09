package fr.ocr.application.user;

import lombok.Value;




@Value
public class UserCrudDtoWeb  {

    private Integer idUser;
    private String username;
    private String password;
    private String email;
}
