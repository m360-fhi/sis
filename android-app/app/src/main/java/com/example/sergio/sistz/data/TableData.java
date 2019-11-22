package com.example.sergio.sistz.data;

import android.graphics.drawable.Drawable;

import java.util.List;

public class TableData {
    private int size;
    private List<TableDataItem> row;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<TableDataItem> getRow() {
        return row;
    }

    public void setRow(List<TableDataItem> row) {
        this.row = row;
    }

    public static class TableDataItem {
        private String valueTxt;
        private Drawable valueImg;

        private int weight;
        private int fontColor;
        private int fontSize;
        private int backGroundColor;
        private int type;
        private int aligment;

        public static final int TEXT = 0;
        public static final int IMG = 1;

        public int getAligment() {
            return aligment;
        }

        public void setAligment(int aligment) {
            this.aligment = aligment;
        }

        public String getValue() {
            return valueTxt;
        }

        public Drawable getValueImg() {
            return valueImg;
        }

        public void setValue(String value) {
            this.valueTxt = value;
        }

        public void setValueImg(Drawable valueImg) {
            this.valueImg = valueImg;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getFontColor() {
            return fontColor;
        }

        public void setFontColor(int fontColor) {
            this.fontColor = fontColor;
        }

        public int getFontSize() {
            return fontSize;
        }

        public void setFontSize(int fontSize) {
            this.fontSize = fontSize;
        }

        public int getBackGroundColor() {
            return backGroundColor;
        }

        public int getType() {
            return type;
        }

        public void setBackGroundColor(int backGroundColor) {
            this.backGroundColor = backGroundColor;
        }

        public TableDataItem(String value, int backGroundColor, int fontColor, int fontSize, int weight, int aligment) {
            this.type = TEXT;
            this.valueTxt = value;
            this.backGroundColor = backGroundColor;
            this.fontColor = fontColor;
            this.fontSize = fontSize;
            this.weight = weight;
            this.aligment = aligment;
        }

        public TableDataItem(Drawable value, int backGroundColor, int weight, int aligment) {
            this.type = IMG;
            this.valueImg = value;
            this.backGroundColor = backGroundColor;
            this.weight = weight;
            this.aligment = aligment;
        }
    }
}
