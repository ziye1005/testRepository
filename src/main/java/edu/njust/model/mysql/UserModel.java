package edu.njust.model.mysql;

public class UserModel {
    private int userid;
    private String username;
    private String userpw;

    //   getter
    public int getUserId() {
        return userid;
    }
    public String getUserName() {
        return username;
    }
    public String getUserPw() {
        return userpw;
    }
    //   setter
    public void setUserId(int userid) {
        this.userid = userid;
    }
    public void setUserName(String username) {
        this.username = username;
    }
    public void setUserPw(String userpw) {
        this.userpw = userpw;
    }
}
