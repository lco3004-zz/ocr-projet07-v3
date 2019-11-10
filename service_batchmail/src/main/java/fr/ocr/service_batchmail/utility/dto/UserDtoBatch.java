package fr.ocr.service_batchmail.utility.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDtoBatch implements Serializable {
    static final long serialVersionUID = 5453281303625368221L;

    private String userName;
    private String email;

}
