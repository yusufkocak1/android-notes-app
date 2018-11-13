package com.yusuf.yube.mynotes.Contact;

public class Note {

    private String id;
    public String content;
    private String date;
    private String type;

    private String email;

    public Note(){

    }
    public Note(String content, String date, String type, String userEmail) {

        this.content = content;
        this.date = date;
        this.type = type;
        this.email = userEmail;
    }
    public Note(String id, String content, String date, String type, String userEmail) {

        this.id=id;
        this.content = content;
        this.date = date;
        this.type = type;
        this.email = userEmail;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
