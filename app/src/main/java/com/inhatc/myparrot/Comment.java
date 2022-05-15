package com.inhatc.myparrot;

public class Comment {
    String writing_uid;
    String time;
    String nickname;
    String comments;

    public String getWriting_uid() { return writing_uid; }
    public void setWriting_uid(String writing_uid) {
        this.writing_uid = writing_uid;
    }

    public String getTime() {return time;}
    public void setTime(String time) {
        this.time = time;
    }

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComments() {return comments;}
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Comment(){}

    public Comment(String writing_uid, String time, String nickname, String comments){
        this.writing_uid = writing_uid;
        this.time = time;
        this.nickname = nickname;
        this.comments = comments;
    }
}
