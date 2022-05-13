package com.inhatc.myparrot;

public class userInfo {
    String email;
    String passwd;
    String nickname;
    String phNumber;

    public userInfo(){} // 생성자 메서드

    //getter, setter 설정
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getPasswd(){
        return passwd;
    }
    public void setPasswd(String passwd){
        this.passwd = passwd;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getPhNumber(){
        return phNumber;
    }
    public void setPhNumber(String phNumber){
        this.phNumber = phNumber;
    }

    public userInfo(String email, String passwd, String phNumber, String nickname){
        this.email = email;
        this.passwd = passwd;
        this.phNumber = phNumber;
        this.nickname = nickname;
    }
}
