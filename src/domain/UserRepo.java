package domain;

import repository.UserMapper;

public class UserRepo {
	
	private UserMapper userMapper;
	
	public UserRepo (UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public User requestUser (String accountName) {
		return userMapper.getUser(accountName);
	}
	
	public void makeUser(String accountName, String password,boolean isAdmin) {
		userMapper.makeUser(accountName, password, isAdmin);
	}
}
