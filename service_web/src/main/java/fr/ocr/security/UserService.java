package fr.ocr.security;


public interface UserService {
	

	User doesUserExist(String email) ;
	
	User getByEmail(String email);
	
	User isValidUser(String email, String password) ;
}
