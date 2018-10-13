package com.example.yube.calyNotes.Contact;

public class Note {

    private int id;
    private String head;
    private String content;
    private String date;
    private String type;

    int userid;

    public Note(int id, String head, String content, String date, String type, int userid) {
        this.id = id;
        this.head = head;
        this.content = content;
        this.date = date;
        this.type = type;
        this.userid = userid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
