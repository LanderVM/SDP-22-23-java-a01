package domain;

import exceptions.UserAlreadyExistsExcpetion;
import exceptions.UserDoesntExistException;

public class DomainController {
	
	private UserRepo userRepo;
	
	public DomainController (UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public boolean checkUser (String accountName,String password) throws UserDoesntExistException {
		
		User user = userRepo.requestUser(accountName);
		
		if (user==null) {
			throw new UserDoesntExistException();
		}
		if (user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void makeUser (String accountName,String password,boolean isAdmin) throws UserAlreadyExistsExcpetion {
		
		User user = userRepo.requestUser(accountName);
		
		if (user!=null) {
			throw new UserAlreadyExistsExcpetion();
		}
		
		userRepo.makeUser(accountName, password, isAdmin);
	}
}
