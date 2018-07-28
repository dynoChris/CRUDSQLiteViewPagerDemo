package com.example.vadym.thetrainingproject.database.model;

public class ThirdItem {

    public static final String TABLE_NAME = "third_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String text;
    private String timestamp;

    public ThirdItem() {

    }

    public ThirdItem(int id, String text, String timestamp) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

