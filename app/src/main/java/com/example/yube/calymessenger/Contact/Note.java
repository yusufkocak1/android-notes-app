package com.example.yube.calymessenger.Contact;

public class Note {


    private String head;
    private String content;
    private String date;
    private String type;

    private String email;

    public Note( String head, String content, String date, String type, String userEmail) {

        this.head = head;
        this.content = content;
        this.date = date;
        this.type = type;
        this.email = userEmail;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
