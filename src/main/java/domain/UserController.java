package domain;

import exceptions.UserDoesntExistException;
import repository.UserMapper;

public class UserController {

    private User user;
    private UserRepo userRepo;

    public UserController() {
        userRepo = new UserRepo(new UserMapper());
    }

    public boolean checkUser(String accountName, String password) throws UserDoesntExistException {

        user = userRepo.requestUser(accountName);

        if (user == null)
            throw new UserDoesntExistException();
        return user.getPassword().equals(password);
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
    }
}
