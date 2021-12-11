package edu.njust.mapper.mysql;

import edu.njust.model.mysql.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //方法1：注解写sql语句
    @Select("select * from user")
    List<UserModel> select_();

    //方法2：映射到resources/mapper/_.xml文件
    List<UserModel> select();
    int insert(String username, String userpw);
    int delete(String username);
    int update(String username0, String username, String userpw);
}
