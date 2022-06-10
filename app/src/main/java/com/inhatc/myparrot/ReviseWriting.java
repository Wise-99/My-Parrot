package com.inhatc.myparrot;

public class ReviseWriting {
    String title;
    String content;
    String tab;
    String image1;
    String image2;
    String image3;
    String image4;
    String image5;

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

    public ReviseWriting(){}

    public ReviseWriting(String title, String content, String tab){
        this.title = title;
        this.content = content;
        this.tab = tab;
    }
}
