package bookSystem.mapper;

import bookSystem.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE email = #{email} and password = #{password}")
    User login(String email,String password);

}
