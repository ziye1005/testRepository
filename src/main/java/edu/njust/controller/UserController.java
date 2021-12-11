package edu.njust.controller;

import edu.njust.model.mysql.UserModel;
import edu.njust.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/user")//一级URL
@CrossOrigin //允许跨域
public class UserController {
    //调用service
    @Autowired
    private UserService userService;
    //查
    @GetMapping(value = "/select")
    public List<UserModel> select() throws Exception {
        return userService.select();
    }
    //增
    @PostMapping(value = "/insert")
    public int insert(@RequestParam(value="username")String username,
                      @RequestParam(value="userpw")String userpw) throws Exception {
        return userService.insert(username, userpw);
    }
    //删
    @DeleteMapping(value = "/delete")
    public int delete(@RequestParam(value="username")String username) throws Exception{
        return userService.delete(username);
    }
    //改
    @PutMapping(value = "/update")
    public int update(@RequestParam(value="username0")String username0,
                      @RequestParam(value="username")String username,
                      @RequestParam(value="userpw")String userpw) throws Exception {
        return userService.update(username0, username, userpw);
    }

}
