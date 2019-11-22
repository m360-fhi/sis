package com.example.sergio.sistz.util;

/**
 * Created by Sergio on 3/20/2016.
 */
public class Item {
    private String subject;
    private boolean selSubject;

    public Item(String sp1){
        super();
    }

    public Item(String subject, boolean selSubject){
        super();
        this.subject = subject;
        this.selSubject = selSubject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean getselSubject() {
        return selSubject;
    }


    public void setSelSubject(boolean selSubject) {
        this.selSubject = selSubject;
    }
}
