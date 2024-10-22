package bookSystem.controller;

import bookSystem.pojo.Result;
import bookSystem.pojo.User;
import bookSystem.service.UserService;
import bookSystem.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result login(@RequestParam String email, @RequestParam String password){
        log.info("login{}",email);
        User u = userService.login(email,password);
        if(u != null){
            Map<String, Object> data = new HashMap<>();
            data.put("userId", u.getUserId());
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", u.getUserId());
            claims.put("email",u.getEmail());
            log.info("传递claims");
            String jwt = JwtUtils.creatJwt(claims);
            data.put("token",jwt);
            return Result.success(data);
        }
        return Result.error("用户名或密码错误");
    }
}
