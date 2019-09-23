package com.example.uaspwpb;

public class Memo {
    private String id;
    private String title;
    private String date;
    private String subTitle;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Memo(){}
    public  Memo(String title,String date,String subTitle,String id){
        this.title = title;
        this.date = date;
        this.subTitle = subTitle;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
