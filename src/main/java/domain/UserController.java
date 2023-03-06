package domain;

import jakarta.persistence.EntityNotFoundException;
import persistence.UserJPADoa;

public class UserController {

    private final UserJPADoa userJPADoa;
    private User user;

    public UserController(UserJPADoa userJPADoa) {
        this.userJPADoa = userJPADoa;
    }

    public boolean checkUser(String accountName, String password) throws EntityNotFoundException {

        user = userJPADoa.get(accountName).
                orElseThrow(() -> {
                    throw new EntityNotFoundException("User does not exists");
                });

        return user.getPassword().equals(password);
    }

    public boolean userIsAdmin() {
        return user.isAdmin();
    }
}
