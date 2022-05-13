package com.inhatc.myparrot;

import android.net.Uri;

public class Writing {
    String title;
    String content;
    String tab;
    String time;
    String nickname;
    String image1;
    String image2;
    String image3;
    String image4;
    String image5;
    int views;

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getTab(){
        return tab;
    }
    public void setTab(String tab){
        this.tab = tab;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getImage1() { return image1; }
    public void setImage1(String image1) { this.image1 = image1; }

    public String getImage2() { return image2; }
    public void setImage2(String image2) { this.image2 = image2; }

    public String getImage3() { return image3; }
    public void setImage3(String image3) { this.image3 = image3; }

    public String getImage4() { return image4; }
    public void setImage4(String image4) { this.image4 = image4; }

    public String getImage5() { return image5; }
    public void setImage5(String image5) { this.image5 = image5; }

    public int getViews(){
        return views;
    }
    public void setViews(int views){
        this.views = views;
    }

    public Writing(){}

    public Writing(String nickname, String title, String content, String tab, String time, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Writing(String nickname, String title, String content, String tab, String time, String image1, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Writing(String nickname, String title, String content, String tab, String time, String image1, String image2, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Writing(String nickname, String title, String content, String tab, String time, String image1, String image2, String image3, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public Writing(String nickname, String title, String content, String tab, String time, String image1, String image2, String image3, String image4, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public Writing(String nickname, String title, String content, String tab, String time, String image1, String image2, String image3, String image4, String image5, int views){
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.tab = tab;
        this.time = time;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }
}
