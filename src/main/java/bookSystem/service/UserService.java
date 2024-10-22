package bookSystem.service;

import bookSystem.pojo.User;

public interface UserService {
    User login(String email,String password);
}
