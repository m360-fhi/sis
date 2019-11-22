package com.example.sergio.sistz.data;

public class ExpandableItemMenu {
    private String text;
    private int image;

    public ExpandableItemMenu(int image, String text) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

