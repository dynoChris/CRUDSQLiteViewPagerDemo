package com.example.vadym.thetrainingproject.database.model;

public class SecondItem {

    public static final String TABLE_NAME = "second_table";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private String text;
    private String timestamp;

    public SecondItem() {

    }
    public SecondItem(int id, String text, String timestamp) {
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
