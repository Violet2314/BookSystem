package bookSystem.service.imp;

import bookSystem.mapper.UserMapper;
import bookSystem.pojo.User;
import bookSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String email,String password) {
        return userMapper.login(email,password);
    }
}
