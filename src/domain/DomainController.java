package domain;

import exceptions.UserAlreadyExistsExcpetion;
import exceptions.UserDoesntExistException;
import repository.UserMapper;

public class DomainController {
	
	private UserRepo userRepo;
	
	public DomainController () {
		userRepo = new UserRepo(new UserMapper());
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
