package com.example.sergio.sistz.data;

public class TeacherStudentItem {
    private String id;
    private String full_name;
    private String active;

    public TeacherStudentItem() {
        this("", "", "");
    }
    public TeacherStudentItem(String id, String name, String active) {
        this.id = id;
        this.full_name = name;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
