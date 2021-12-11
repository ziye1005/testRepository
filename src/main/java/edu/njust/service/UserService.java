package edu.njust.service;


import edu.njust.mapper.mysql.UserMapper;
import edu.njust.model.mysql.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    //调用mapper
    @Resource
    private UserMapper userMapper;
    //查
    public List<UserModel> select() {
        return userMapper.select();
    }
    //增
    public int insert(String username, String userpw) {
        return userMapper.insert(username, userpw);
    }
    //删
    public int delete(String username){
        return userMapper.delete(username);
    }
    //改
    public int update(String username0, String username, String userpw) {
        return userMapper.update(username0, username, userpw);
    }

}
