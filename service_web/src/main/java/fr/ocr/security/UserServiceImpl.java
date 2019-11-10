package fr.ocr.security;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

	@Override
	public User doesUserExist(String email) throws UserNotFoundException {
		List<User> users = (List<User>) userDAO.findByEmail(email);
		if(users.size() == 0) {
			//TODO
		} 
		return users.get(0);
	}

	@Override
	public User isValidUser(String email, String password) throws UnmatchingUserCredentialsException {
		List<User> users = (List<User>) userDAO.findByEmailAndPassword(email, password);
		if(users == null || users.size() == 0) {
			throw new UnmatchingUserCredentialsException("User with given credentials is not found in the database.");
		} 
		return users.get(0);
	}

	@Override
	public User getByEmail(String email) throws UserNotFoundException {
		return this.doesUserExist(email);
	}

}
